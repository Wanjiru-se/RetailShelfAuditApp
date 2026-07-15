package ac.ke.usiu.retailshelfauditapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ac.ke.usiu.retailshelfauditapp.R
import com.google.android.material.card.MaterialCardView
import android.graphics.Bitmap
import android.net.Uri

class HomeActivity : AppCompatActivity() {

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {

                @Suppress("DEPRECATION")
                val capturedImage =
                    result.data?.extras?.get("data") as? Bitmap

                if (capturedImage != null) {
                    val previewIntent =
                        Intent(this, ImagePreviewActivity::class.java)

                    previewIntent.putExtra("captured_image", capturedImage)
                    startActivity(previewIntent)
                } else {
                    Toast.makeText(
                        this,
                        "The captured image could not be loaded",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { imageUri: Uri? ->
            if (imageUri != null) {
                val previewIntent =
                    Intent(this, ImagePreviewActivity::class.java)

                previewIntent.putExtra("selected_image_uri", imageUri.toString())
                startActivity(previewIntent)
            } else {
                Toast.makeText(
                    this,
                    "No image was selected",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private val cameraPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                openCamera()
            } else {
                Toast.makeText(
                    this,
                    "Camera permission is required to capture an image",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnCapture = findViewById<MaterialCardView>(R.id.btnCapture)
        val btnUpload = findViewById<MaterialCardView>(R.id.btnUpload)
        val btnReports = findViewById<MaterialCardView>(R.id.btnReports)

        btnCapture.setOnClickListener {
            checkCameraPermission()
        }

        btnUpload.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        btnReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }
    }

    private fun checkCameraPermission() {
        when {
            ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED -> {
                openCamera()
            }

            else -> {
                cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
            }
        }
    }

    private fun openCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        if (cameraIntent.resolveActivity(packageManager) != null) {
            cameraLauncher.launch(cameraIntent)
        } else {
            Toast.makeText(
                this,
                "No camera app was found on this device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}