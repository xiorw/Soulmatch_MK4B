package com.fardan.soulmatchalpha.Match

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.fardan.soulmatchalpha.MainActivity
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.SettingsActivity
import com.fardan.soulmatchalpha.result.MatchResultActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class DateMatchActivity : AppCompatActivity() {

    private lateinit var yourDateInput: EditText
    private lateinit var partnerDateInput: EditText
    private lateinit var resultButton: Button
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.date_match)

        yourDateInput = findViewById(R.id.yourDate)
        partnerDateInput = findViewById(R.id.partnerDate)
        resultButton = findViewById(R.id.resultButton)
        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val bannerStarMatch = findViewById<ImageButton>(R.id.bannerImage)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        bannerStarMatch.setOnClickListener {
            startActivity(Intent(this, StarMatchActivity::class.java))
        }

        resultButton.setOnClickListener {
            processDateMatch()
        }
    }

    private fun processDateMatch() {
        val yourDate = yourDateInput.text.toString().trim()
        val partnerDate = partnerDateInput.text.toString().trim()

        if (yourDate.isEmpty()) {
            yourDateInput.error = "Kolom wajib diisi"
            return
        }
        if (partnerDate.isEmpty()) {
            partnerDateInput.error = "Kolom wajib diisi"
            return
        }

        // Validasi format tanggal
        if (!isValidDateFormat(yourDate)) {
            yourDateInput.error = "Format tanggal harus dd-MM-yyyy"
            return
        }
        if (!isValidDateFormat(partnerDate)) {
            partnerDateInput.error = "Format tanggal harus dd-MM-yyyy"
            return
        }

        val allowedPercentages = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
        val percentage = allowedPercentages.random()

        val userId = auth.currentUser?.uid ?: return

        val matchData = hashMapOf(
            "your_birthdate" to yourDate,
            "partner_birthdate" to partnerDate,
            "match_percentage" to percentage,
            "timestamp" to com.google.firebase.Timestamp.now()
        )

        db.collection("soulmatch_history")
            .document(userId)
            .collection("date_match")
            .add(matchData)
            .addOnSuccessListener {
                val intent = Intent(this, MatchResultActivity::class.java)
                intent.putExtra("feature", "Date Match")
                intent.putExtra("Date1", yourDate)
                intent.putExtra("Date2", partnerDate)
                intent.putExtra("percentage", percentage)
                startActivity(intent)
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    // Validasi format tanggal dd-MM-yyyy
    private fun isValidDateFormat(date: String): Boolean {
        return try {
            val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            sdf.isLenient = false
            sdf.parse(date)
            true
        } catch (e: Exception) {
            false
        }
    }
}
