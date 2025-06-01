package com.fardan.soulmatchalpha.Match

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fardan.soulmatchalpha.MainActivity
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.SettingsActivity
import com.fardan.soulmatchalpha.result.MatchResultActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class StarMatchActivity : AppCompatActivity() {

    private lateinit var yourZodiacInput: EditText
    private lateinit var partnerZodiacInput: EditText

    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // Daftar zodiac valid (lowercase semua untuk kemudahan validasi)
    private val validZodiacs = listOf(
        "aries", "taurus", "gemini", "cancer", "leo", "virgo",
        "libra", "scorpio", "sagittarius", "capricorn", "aquarius", "pisces"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.star_match)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.starmatch)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val bannerTarotSpin = findViewById<ImageButton>(R.id.bannerImage)
        val btnHasil = findViewById<Button>(R.id.resultButton)

        yourZodiacInput = findViewById(R.id.yourZodiac)
        partnerZodiacInput = findViewById(R.id.partnerZodiac)

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        bannerTarotSpin.setOnClickListener {
            startActivity(Intent(this, TarotSpinActivity::class.java))
        }

        btnHasil.setOnClickListener {
            val yourZodiacRaw = yourZodiacInput.text.toString().trim()
            val partnerZodiacRaw = partnerZodiacInput.text.toString().trim()

            if (yourZodiacRaw.isEmpty()) {
                yourZodiacInput.error = "Kolom wajib diisi"
                return@setOnClickListener
            }
            val yourZodiac = yourZodiacRaw.lowercase()
            if (!validZodiacs.contains(yourZodiac)) {
                yourZodiacInput.error = "Masukkan zodiak yang valid"
                return@setOnClickListener
            }

            if (partnerZodiacRaw.isEmpty()) {
                partnerZodiacInput.error = "Kolom wajib diisi"
                return@setOnClickListener
            }
            val partnerZodiac = partnerZodiacRaw.lowercase()
            if (!validZodiacs.contains(partnerZodiac)) {
                partnerZodiacInput.error = "Masukkan zodiak yang valid"
                return@setOnClickListener
            }

            val allowedPercentages = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
            val percentage = allowedPercentages.random()

            val user = auth.currentUser

            if (user == null) {
                Toast.makeText(this, "Anda belum login", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val data = hashMapOf(
                "your_zodiac" to yourZodiacRaw,  // simpan dengan format asli user (capitalize optional)
                "partner_zodiac" to partnerZodiacRaw,
                "match_percentage" to percentage,
                "timestamp" to com.google.firebase.Timestamp.now()
            )

            db.collection("soulmatch_history")
                .document(user.uid)
                .collection("star_match")
                .add(data)
                .addOnSuccessListener {
                    val intent = Intent(this, MatchResultActivity::class.java)
                    intent.putExtra("feature", "Star Match")
                    intent.putExtra("zodiac1", yourZodiacRaw)
                    intent.putExtra("zodiac2", partnerZodiacRaw)
                    intent.putExtra("percentage", percentage)
                    startActivity(intent)
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
