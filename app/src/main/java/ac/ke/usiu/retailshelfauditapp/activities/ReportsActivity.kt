package ac.ke.usiu.retailshelfauditapp.activities

import android.content.Intent
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

        // Takes the user directly back to the Home screen
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

        val reportsList = databaseHelper.getAllReports()

        reportAdapter = ReportAdapter(reportsList)

        binding.recyclerReports.layoutManager = LinearLayoutManager(this)
        binding.recyclerReports.adapter = reportAdapter
    }
}