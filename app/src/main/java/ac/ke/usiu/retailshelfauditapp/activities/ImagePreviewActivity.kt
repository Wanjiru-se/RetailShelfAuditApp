package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityImagePreviewBinding
import ac.ke.usiu.retailshelfauditapp.yolo.Detection
import ac.ke.usiu.retailshelfauditapp.yolo.RetailShelfDetector
import java.io.File
import java.io.FileOutputStream

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePreviewBinding
    private lateinit var detector: RetailShelfDetector

    private var imageToAnalyze: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        detector = RetailShelfDetector(this)

        val capturedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("captured_image", Bitmap::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("captured_image")
        }

        val selectedImageUri = intent.getStringExtra("selected_image_uri")

        when {
            capturedImage != null -> {
                imageToAnalyze = capturedImage
                binding.imgShelf.setImageBitmap(capturedImage)
            }

            selectedImageUri != null -> {
                val uri = Uri.parse(selectedImageUri)

                imageToAnalyze = loadBitmapFromUri(uri)
                binding.imgShelf.setImageURI(uri)
            }

            else -> {
                Toast.makeText(
                    this,
                    "No image was received",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.btnAnalyze.setOnClickListener {

            val bitmap = imageToAnalyze

            if (bitmap == null) {
                Toast.makeText(
                    this,
                    "No image is available for analysis",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            binding.btnAnalyze.isEnabled = false
            binding.btnAnalyze.text = "Analyzing..."

            Thread {
                try {
                    val detections = detector.detect(bitmap)

                    val colaCount = detections.count {
                        it.className.equals("cola", ignoreCase = true)
                    }

                    val fantaCount = detections.count {
                        it.className.equals("fanta", ignoreCase = true)
                    }

                    val spriteCount = detections.count {
                        it.className.equals("sprite", ignoreCase = true)
                    }

                    val boxedBitmap = drawDetections(
                        bitmap,
                        detections
                    )

                    val boxedImagePath = saveBoxedImage(boxedBitmap)

                    runOnUiThread {

                        val resultsIntent = Intent(
                            this,
                            AnalysisResultsActivity::class.java
                        )

                        resultsIntent.putExtra("cola_count", colaCount)
                        resultsIntent.putExtra("fanta_count", fantaCount)
                        resultsIntent.putExtra("sprite_count", spriteCount)

                        resultsIntent.putExtra(
                            "total_count",
                            detections.size
                        )

                        resultsIntent.putExtra(
                            "boxed_image_path",
                            boxedImagePath
                        )

                        startActivity(resultsIntent)

                        binding.btnAnalyze.isEnabled = true
                        binding.btnAnalyze.text = "Analyze"
                    }

                } catch (exception: Exception) {

                    runOnUiThread {

                        Toast.makeText(
                            this,
                            "Image analysis failed: ${exception.message}",
                            Toast.LENGTH_LONG
                        ).show()

                        binding.btnAnalyze.isEnabled = true
                        binding.btnAnalyze.text = "Analyze"
                    }
                }
            }.start()
        }

        binding.btnRetake.setOnClickListener {
            finish()
        }
    }

    private fun drawDetections(
        bitmap: Bitmap,
        detections: List<Detection>
    ): Bitmap {

        val mutableBitmap = bitmap.copy(
            Bitmap.Config.ARGB_8888,
            true
        )

        val canvas = Canvas(mutableBitmap)


        val boxPaint = Paint().apply {
            style = Paint.Style.STROKE
            strokeWidth = 12f
            color = Color.RED
        }

        val textPaint = Paint().apply {
            style = Paint.Style.FILL
            color = Color.RED
            textSize = 48f
            isFakeBoldText = true
        }

        for (detection in detections) {

            val left = detection.left * mutableBitmap.width
            val top = detection.top * mutableBitmap.height
            val right = detection.right * mutableBitmap.width
            val bottom = detection.bottom * mutableBitmap.height

            canvas.drawRect(
                left,
                top,
                right,
                bottom,
                boxPaint
            )

            canvas.drawText(
                detection.className,
                left,
                (top - 12f).coerceAtLeast(50f),
                textPaint
            )
        }

        return mutableBitmap
    }

    private fun saveBoxedImage(bitmap: Bitmap): String {

        val imageFile = File(
            cacheDir,
            "analyzed_shelf_${System.currentTimeMillis()}.jpg"
        )

        FileOutputStream(imageFile).use { outputStream ->

            bitmap.compress(
                Bitmap.CompressFormat.JPEG,
                90,
                outputStream
            )
        }

        return imageFile.absolutePath
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return try {

            contentResolver.openInputStream(uri)?.use { inputStream ->
                BitmapFactory.decodeStream(inputStream)
            }

        } catch (exception: Exception) {

            Toast.makeText(
                this,
                "Unable to open the selected image",
                Toast.LENGTH_SHORT
            ).show()

            null
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        if (::detector.isInitialized) {
            detector.close()
        }
    }
}