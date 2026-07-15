package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.database.DatabaseHelper
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityAuditReportBinding

class AuditReportActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAuditReportBinding
    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAuditReportBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        binding.btnSaveReport.setOnClickListener {
            val success = databaseHelper.insertReport(
                reportId = "RPT-001",
                date = "24 June 2026",
                cocaColaCount = 8,
                fantaCount = 5,
                spriteCount = 3,
                emptySpaces = 2
            )

            if (success) {
                Toast.makeText(this, "Report saved successfully", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, ReportsActivity::class.java))
            } else {
                Toast.makeText(this, "Failed to save report", Toast.LENGTH_SHORT).show()
            }
        }
    }
}