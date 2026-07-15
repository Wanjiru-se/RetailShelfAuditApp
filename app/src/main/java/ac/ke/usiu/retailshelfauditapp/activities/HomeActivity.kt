package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.R
import com.google.android.material.card.MaterialCardView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val btnCapture = findViewById<MaterialCardView>(R.id.btnCapture)
        val btnUpload = findViewById<MaterialCardView>(R.id.btnUpload)
        val btnReports = findViewById<MaterialCardView>(R.id.btnReports)

        btnCapture.setOnClickListener {
            startActivity(Intent(this, ImagePreviewActivity::class.java))
        }

        btnUpload.setOnClickListener {
            startActivity(Intent(this, ImagePreviewActivity::class.java))
        }

        btnReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }
    }
}