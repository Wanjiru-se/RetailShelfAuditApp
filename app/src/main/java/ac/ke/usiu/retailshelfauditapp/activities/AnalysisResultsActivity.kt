package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityAnalysisResultsBinding

class AnalysisResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAnalysisResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAnalysisResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGenerateReport.setOnClickListener {
            startActivity(Intent(this, AuditReportActivity::class.java))
        }
    }
}