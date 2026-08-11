package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.database.DatabaseHelper
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityAuditReportBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AuditReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuditReportBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuditReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val colaCount = intent.getIntExtra("cola_count", 0)
        val fantaCount = intent.getIntExtra("fanta_count", 0)
        val spriteCount = intent.getIntExtra("sprite_count", 0)

        val totalFacings =
            colaCount +
                    fantaCount +
                    spriteCount

        val currentTime = System.currentTimeMillis()

        val reportId = "RPT-$currentTime"

        val currentDate = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale.getDefault()
        ).format(Date())

        binding.txtReportId.text = reportId
        binding.txtDate.text = currentDate
        binding.txtColaCount.text = "Coca-Cola: $colaCount"
        binding.txtFantaCount.text = "Fanta: $fantaCount"
        binding.txtSpriteCount.text = "Sprite: $spriteCount"
        binding.txtTotalFacings.text = "Total Facings: $totalFacings"

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

        binding.btnSaveReport.setOnClickListener {

            val success = databaseHelper.insertReport(
                reportId = reportId,
                date = currentDate,
                cocaColaCount = colaCount,
                fantaCount = fantaCount,
                spriteCount = spriteCount,
                emptySpaces = 0
            )

            if (success) {
                Toast.makeText(
                    this,
                    "Report saved successfully",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, ReportsActivity::class.java)
                )

                finish()
            } else {
                Toast.makeText(
                    this,
                    "Failed to save report",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}