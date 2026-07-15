package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityImagePreviewBinding

class ImagePreviewActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImagePreviewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityImagePreviewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val capturedImage = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("captured_image", Bitmap::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra("captured_image")
        }

        val selectedImageUri = intent.getStringExtra("selected_image_uri")

        when {
            capturedImage != null -> {
                binding.imgShelf.setImageBitmap(capturedImage)
            }

            selectedImageUri != null -> {
                binding.imgShelf.setImageURI(android.net.Uri.parse(selectedImageUri))
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
            startActivity(Intent(this, AnalysisResultsActivity::class.java))
        }

        binding.btnRetake.setOnClickListener {
            finish()
        }
    }
}