package ac.ke.usiu.retailshelfauditapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ac.ke.usiu.retailshelfauditapp.adapter.ReportAdapter
import ac.ke.usiu.retailshelfauditapp.database.DatabaseHelper
import ac.ke.usiu.retailshelfauditapp.databinding.ActivityReportsBinding

class ReportsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReportsBinding
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var reportAdapter: ReportAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityReportsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        databaseHelper = DatabaseHelper(this)

        val reportsList = databaseHelper.getAllReports()

        reportAdapter = ReportAdapter(reportsList)

        binding.recyclerReports.layoutManager = LinearLayoutManager(this)
        binding.recyclerReports.adapter = reportAdapter
    }
}