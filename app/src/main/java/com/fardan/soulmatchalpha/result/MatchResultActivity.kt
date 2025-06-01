package com.fardan.soulmatchalpha.result

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fardan.soulmatchalpha.MainActivity
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.SettingsActivity
import com.fardan.soulmatchalpha.model.MatchResult

class MatchResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_result)

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val percentageText: TextView = findViewById(R.id.percentageText)
        val titleText: TextView = findViewById(R.id.judulPersentase)
        val descriptionText: TextView = findViewById(R.id.descriptionText)
        val cobaLagi: Button = findViewById(R.id.btnCobaLagi)

        val matchResults = listOf(
            MatchResult("10%", "Musuh Dalam Dunia", "Kalian tidak cocok sama sekali! ..."),
            MatchResult("20%", "Teman Palsu", "Seperti teman yang pura-pura akrab ..."),
            MatchResult("30%", "Ada Tapi Tipis", "Kalian mirip mie instan bumbu ..."),
            MatchResult("40%", "Sahabat Tapi Gak Lebih", "Seperti kopi dan teh ..."),
            MatchResult("50%", "Jodoh 50:50", "Kalian kayak lempar koin ..."),
            MatchResult("60%", "Lumayan, Boleh Dicoba!", "Kalian seperti pasangan yang cocok ..."),
            MatchResult("70%", "Cocok Ga Ya?", "Cukup nyambung nih ..."),
            MatchResult("80%", "Bakal Seru Berdua!", "Kalian seperti nonton serial TV ..."),
            MatchResult("90%", "Hampir Banget Cocok!", "Wah, kayak pancake dengan topping lengkap ..."),
            MatchResult("100%", "Jodoh Sejati, Selamanya!", "Selamat! Kalian kayak nasi dan sambal ...")
        )

        // Ambil persentase dari Intent
        val percentage = intent.getIntExtra("percentage", -1)

        if (percentage == -1) {
            percentageText.text = "-"
            titleText.text = "Data tidak ditemukan"
            descriptionText.text = "Persentase tidak dikirim dari fitur sebelumnya."
        } else {
            val rounded = (percentage / 10) * 10
            val result = matchResults.find { it.percentage == "$rounded%" }

            if (result != null) {
                percentageText.text = "$percentage%"
                titleText.text = result.title
                descriptionText.text = result.description
            } else {
                percentageText.text = "$percentage%"
                titleText.text = "Hasil Tidak Tersedia"
                descriptionText.text = "Tidak ada deskripsi yang cocok untuk persentase ini."
            }
        }

        cobaLagi.setOnClickListener {
            finish()
        }

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
