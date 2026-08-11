package ac.ke.usiu.retailshelfauditapp.activities

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ac.ke.usiu.retailshelfauditapp.R
import ac.ke.usiu.retailshelfauditapp.database.DatabaseHelper
import com.google.android.material.card.MaterialCardView
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var databaseHelper: DatabaseHelper

    private lateinit var txtTodayAudits: TextView
    private lateinit var txtReportsCount: TextView
    private lateinit var txtLatestFacings: TextView

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

        databaseHelper = DatabaseHelper(this)

        txtTodayAudits = findViewById(R.id.txtTodayAudits)
        txtReportsCount = findViewById(R.id.txtReportsCount)
        txtLatestFacings = findViewById(R.id.txtLatestFacings)

        val btnCapture = findViewById<MaterialCardView>(R.id.btnCapture)
        val btnUpload = findViewById<MaterialCardView>(R.id.btnUpload)
        val btnReports = findViewById<MaterialCardView>(R.id.btnReports)
        val btnLogout = findViewById<TextView>(R.id.btnLogout)

        btnCapture.setOnClickListener {
            checkCameraPermission()
        }

        btnUpload.setOnClickListener {
            galleryLauncher.launch("image/*")
        }

        btnReports.setOnClickListener {
            startActivity(Intent(this, ReportsActivity::class.java))
        }

        btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()

            val intent = Intent(this, LoginActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
            finish()
        }

        updateDashboard()
    }

    override fun onResume() {
        super.onResume()
        updateDashboard()
    }

    private fun updateDashboard() {

        val reports = databaseHelper.getAllReports()

        val currentDate = SimpleDateFormat(
            "dd MMMM yyyy",
            Locale.getDefault()
        ).format(Date())

        val todaysAudits = reports.count {
            it.date == currentDate
        }

        val totalReports = reports.size

        val latestReport = reports.firstOrNull()

        val latestFacings = if (latestReport != null) {
            latestReport.cocaColaCount +
                    latestReport.fantaCount +
                    latestReport.spriteCount
        } else {
            0
        }

        txtTodayAudits.text = todaysAudits.toString()
        txtReportsCount.text = totalReports.toString()
        txtLatestFacings.text = latestFacings.toString()
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