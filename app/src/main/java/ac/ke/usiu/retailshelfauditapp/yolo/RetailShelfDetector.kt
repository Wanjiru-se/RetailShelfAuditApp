package ac.ke.usiu.retailshelfauditapp.yolo

import android.content.Context
import android.graphics.Bitmap
import java.nio.ByteBuffer
import java.nio.ByteOrder
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class RetailShelfDetector(context: Context) {

    private val inputSize = 640

    private val classNames = arrayOf("cola", "fanta", "sprite")
    private val confidenceThreshold = 0.25f
    private val interpreter: Interpreter

    init {
        interpreter = Interpreter(loadModelFile(context))
    }

    private fun loadModelFile(context: Context): MappedByteBuffer {
        val fileDescriptor = context.assets.openFd("best.tflite")

        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel

        return fileChannel.map(
            FileChannel.MapMode.READ_ONLY,
            fileDescriptor.startOffset,
            fileDescriptor.declaredLength
        )
    }

    private fun preprocessImage(bitmap: Bitmap): ByteBuffer {

        val resizedBitmap = Bitmap.createScaledBitmap(
            bitmap,
            inputSize,
            inputSize,
            true
        )

        val inputBuffer = ByteBuffer.allocateDirect(
            1 * 3 * inputSize * inputSize * 4
        )

        inputBuffer.order(ByteOrder.nativeOrder())

        val pixels = IntArray(inputSize * inputSize)

        resizedBitmap.getPixels(
            pixels,
            0,
            inputSize,
            0,
            0,
            inputSize,
            inputSize
        )

        // Red channel
        for (pixel in pixels) {
            val red = ((pixel shr 16) and 0xFF) / 255.0f
            inputBuffer.putFloat(red)
        }

        // Green channel
        for (pixel in pixels) {
            val green = ((pixel shr 8) and 0xFF) / 255.0f
            inputBuffer.putFloat(green)
        }

        // Blue channel
        for (pixel in pixels) {
            val blue = (pixel and 0xFF) / 255.0f
            inputBuffer.putFloat(blue)
        }

        inputBuffer.rewind()

        return inputBuffer
    }

    fun runInference(bitmap: Bitmap): Array<Array<FloatArray>> {

        val inputBuffer = preprocessImage(bitmap)

        val output = Array(1) {
            Array(7) {
                FloatArray(8400)
            }
        }

        interpreter.run(inputBuffer, output)

        return output
    }

    fun detect(bitmap: Bitmap): List<Detection> {

        val output = runInference(bitmap)
        val detections = mutableListOf<Detection>()

        for (index in 0 until 8400) {

            var bestClassIndex = 0
            var bestConfidence = output[0][4][index]

            for (classIndex in 1 until classNames.size) {
                val confidence = output[0][4 + classIndex][index]

                if (confidence > bestConfidence) {
                    bestConfidence = confidence
                    bestClassIndex = classIndex
                }
            }

            if (bestConfidence >= confidenceThreshold) {

                val centerX = output[0][0][index]
                val centerY = output[0][1][index]
                val width = output[0][2][index]
                val height = output[0][3][index]

                android.util.Log.d(
                    "YOLO_BOX",
                    "x=$centerX y=$centerY w=$width h=$height confidence=$bestConfidence"
                )

                val left = (centerX - width / 2f).coerceIn(0f, 1f)
                val top = (centerY - height / 2f).coerceIn(0f, 1f)
                val right = (centerX + width / 2f).coerceIn(0f, 1f)
                val bottom = (centerY + height / 2f).coerceIn(0f, 1f)

                detections.add(
                    Detection(
                        className = classNames[bestClassIndex],
                        confidence = bestConfidence,
                        left = left,
                        top = top,
                        right = right,
                        bottom = bottom
                    )
                )
            }
        }

        return applyNms(detections)
    }

    private fun applyNms(detections: List<Detection>): List<Detection> {

        val sortedDetections = detections.sortedByDescending {
            it.confidence
        }.toMutableList()

        val finalDetections = mutableListOf<Detection>()

        while (sortedDetections.isNotEmpty()) {

            val bestDetection = sortedDetections.removeAt(0)
            finalDetections.add(bestDetection)

            val iterator = sortedDetections.iterator()

            while (iterator.hasNext()) {
                val otherDetection = iterator.next()

                if (
                    bestDetection.className == otherDetection.className &&
                    calculateIou(bestDetection, otherDetection) >= 0.45f
                ) {
                    iterator.remove()
                }
            }
        }

        return finalDetections
    }

    private fun calculateIou(
        first: Detection,
        second: Detection
    ): Float {

        val intersectionLeft = maxOf(first.left, second.left)
        val intersectionTop = maxOf(first.top, second.top)
        val intersectionRight = minOf(first.right, second.right)
        val intersectionBottom = minOf(first.bottom, second.bottom)

        val intersectionWidth =
            maxOf(0f, intersectionRight - intersectionLeft)

        val intersectionHeight =
            maxOf(0f, intersectionBottom - intersectionTop)

        val intersectionArea =
            intersectionWidth * intersectionHeight

        val firstArea =
            (first.right - first.left) *
                    (first.bottom - first.top)

        val secondArea =
            (second.right - second.left) *
                    (second.bottom - second.top)

        val unionArea =
            firstArea + secondArea - intersectionArea

        return if (unionArea > 0f) {
            intersectionArea / unionArea
        } else {
            0f
        }
    }

    fun close() {
        interpreter.close()
    }
}