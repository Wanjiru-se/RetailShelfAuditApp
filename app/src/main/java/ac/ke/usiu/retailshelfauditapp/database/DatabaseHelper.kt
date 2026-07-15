package ac.ke.usiu.retailshelfauditapp.database

import ac.ke.usiu.retailshelfauditapp.model.Report
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "ShelfAudit.db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_REPORTS = "reports"
        private const val COLUMN_ID = "id"
        private const val COLUMN_REPORT_ID = "report_id"
        private const val COLUMN_DATE = "date"
        private const val COLUMN_COCA_COLA = "coca_cola_count"
        private const val COLUMN_FANTA = "fanta_count"
        private const val COLUMN_SPRITE = "sprite_count"
        private const val COLUMN_EMPTY_SPACES = "empty_spaces"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_REPORTS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_REPORT_ID TEXT,
                $COLUMN_DATE TEXT,
                $COLUMN_COCA_COLA INTEGER,
                $COLUMN_FANTA INTEGER,
                $COLUMN_SPRITE INTEGER,
                $COLUMN_EMPTY_SPACES INTEGER
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_REPORTS")
        onCreate(db)
    }

    fun insertReport(
        reportId: String,
        date: String,
        cocaColaCount: Int,
        fantaCount: Int,
        spriteCount: Int,
        emptySpaces: Int
    ): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_REPORT_ID, reportId)
            put(COLUMN_DATE, date)
            put(COLUMN_COCA_COLA, cocaColaCount)
            put(COLUMN_FANTA, fantaCount)
            put(COLUMN_SPRITE, spriteCount)
            put(COLUMN_EMPTY_SPACES, emptySpaces)
        }

        val result = db.insert(TABLE_REPORTS, null, values)
        db.close()

        return result != -1L
    }
    fun getAllReports(): ArrayList<Report> {
        val reportsList = ArrayList<Report>()
        val db = readableDatabase

        val cursor = db.rawQuery("SELECT * FROM reports ORDER BY id DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val report = Report(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                    reportId = cursor.getString(cursor.getColumnIndexOrThrow("report_id")),
                    date = cursor.getString(cursor.getColumnIndexOrThrow("date")),
                    cocaColaCount = cursor.getInt(cursor.getColumnIndexOrThrow("coca_cola_count")),
                    fantaCount = cursor.getInt(cursor.getColumnIndexOrThrow("fanta_count")),
                    spriteCount = cursor.getInt(cursor.getColumnIndexOrThrow("sprite_count")),
                    emptySpaces = cursor.getInt(cursor.getColumnIndexOrThrow("empty_spaces"))
                )

                reportsList.add(report)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return reportsList
    }
}