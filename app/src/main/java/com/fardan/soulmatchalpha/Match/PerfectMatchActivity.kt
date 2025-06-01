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
import java.util.*
import kotlin.random.Random

class PerfectMatchActivity : AppCompatActivity() {

    private lateinit var yourName: EditText
    private lateinit var partnerName: EditText
    private lateinit var resultButton: Button

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.perfect_match)

        yourName = findViewById(R.id.yourName)
        partnerName = findViewById(R.id.partnerName)
        resultButton = findViewById(R.id.resultButton)

        val bannerDateMatch = findViewById<ImageButton>(R.id.bannerImage)
        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        bannerDateMatch.setOnClickListener {
            startActivity(Intent(this, DateMatchActivity::class.java))
        }

        firestore = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        resultButton.setOnClickListener {
            val name1 = yourName.text.toString().trim()
            val name2 = partnerName.text.toString().trim()

            if (name1.isEmpty()) {
                yourName.error = "Kolom wajib diisi"
                return@setOnClickListener
            }
            if (name2.isEmpty()) {
                partnerName.error = "Kolom wajib diisi"
                return@setOnClickListener
            }

            val allowedPercentages = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90, 100)
            val percentage = allowedPercentages.random()

            val currentUser = auth.currentUser

            if (currentUser != null) {
                val matchData = hashMapOf(
                    "your_name" to name1,
                    "partner_name" to name2,
                    "match_percentage" to percentage,
                    "timestamp" to com.google.firebase.Timestamp.now()
                )

                firestore.collection("soulmatch_history")
                    .document(currentUser.uid)
                    .collection("perfect_match")
                    .add(matchData)
                    .addOnSuccessListener {
                        val intent = Intent(this, MatchResultActivity::class.java)
                        intent.putExtra("feature", "Perfect Match")
                        intent.putExtra("Nama1", name1)
                        intent.putExtra("Nama2", name2)
                        intent.putExtra("percentage", percentage)
                        startActivity(intent)
                    }
                    .addOnFailureListener {
                        Toast.makeText(this, "Gagal menyimpan", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this, "User belum login", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
