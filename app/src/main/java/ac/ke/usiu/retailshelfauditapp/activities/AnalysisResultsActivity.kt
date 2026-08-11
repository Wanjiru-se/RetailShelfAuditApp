package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityAnalysisResultsBinding
import java.io.File

class AnalysisResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalysisResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnalysisResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val colaCount = intent.getIntExtra("cola_count", 0)
        val fantaCount = intent.getIntExtra("fanta_count", 0)
        val spriteCount = intent.getIntExtra("sprite_count", 0)
        val totalCount = intent.getIntExtra("total_count", 0)

        val boxedImagePath = intent.getStringExtra("boxed_image_path")

        if (boxedImagePath != null) {
            val imageFile = File(boxedImagePath)

            if (imageFile.exists()) {
                val analyzedBitmap =
                    BitmapFactory.decodeFile(imageFile.absolutePath)

                binding.imgAnalyzedShelf.setImageBitmap(analyzedBitmap)
            }
        }

        val colaVisibility = if (totalCount > 0) {
            (colaCount.toFloat() / totalCount) * 100
        } else {
            0f
        }

        val fantaVisibility = if (totalCount > 0) {
            (fantaCount.toFloat() / totalCount) * 100
        } else {
            0f
        }

        val spriteVisibility = if (totalCount > 0) {
            (spriteCount.toFloat() / totalCount) * 100
        } else {
            0f
        }

        binding.txtTotalProducts.text = totalCount.toString()

        binding.txtColaCount.text = "Coca-Cola ×$colaCount"
        binding.txtFantaCount.text = "Fanta ×$fantaCount"
        binding.txtSpriteCount.text = "Sprite ×$spriteCount"

        binding.txtColaVisibility.text =
            "Visibility: %.1f%%".format(colaVisibility)

        binding.txtFantaVisibility.text =
            "Visibility: %.1f%%".format(fantaVisibility)

        binding.txtSpriteVisibility.text =
            "Visibility: %.1f%%".format(spriteVisibility)

        binding.btnHome.setOnClickListener {

            val homeIntent = Intent(
                this,
                HomeActivity::class.java
            )

            homeIntent.flags =
                Intent.FLAG_ACTIVITY_CLEAR_TOP or
                        Intent.FLAG_ACTIVITY_SINGLE_TOP

            startActivity(homeIntent)
            finish()
        }

        binding.btnGenerateReport.setOnClickListener {

            val reportIntent = Intent(
                this,
                AuditReportActivity::class.java
            )

            reportIntent.putExtra("cola_count", colaCount)
            reportIntent.putExtra("fanta_count", fantaCount)
            reportIntent.putExtra("sprite_count", spriteCount)
            reportIntent.putExtra("total_count", totalCount)

            startActivity(reportIntent)
        }
    }
}