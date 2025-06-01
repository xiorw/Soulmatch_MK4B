package com.fardan.soulmatchalpha

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fardan.soulmatchalpha.Match.PerfectMatchActivity
import com.fardan.soulmatchalpha.Match.DateMatchActivity
import com.fardan.soulmatchalpha.Match.StarMatchActivity
import com.fardan.soulmatchalpha.Match.TarotSpinActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi tombol
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val bannerPerfectMatch = findViewById<ImageButton>(R.id.bannerImage)
        val btnPerfectMatch = findViewById<ImageButton>(R.id.btnPerfectMatch)
        val btnDateMatch = findViewById<ImageButton>(R.id.btnDateMatch)
        val btnStarMatch = findViewById<ImageButton>(R.id.btnStarMatch)
        val btnTarotSpin = findViewById<ImageButton>(R.id.btnTarotSpin)

        // Navigasi ke halaman masing-masing

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        bannerPerfectMatch.setOnClickListener {
            startActivity(Intent(this, PerfectMatchActivity::class.java))
        }

        btnPerfectMatch.setOnClickListener {
            startActivity(Intent(this, PerfectMatchActivity::class.java))
        }

        btnDateMatch.setOnClickListener {
            startActivity(Intent(this, DateMatchActivity::class.java))
        }

        btnStarMatch.setOnClickListener {
            startActivity(Intent(this, StarMatchActivity::class.java))
        }

        btnTarotSpin.setOnClickListener {
            startActivity(Intent(this, TarotSpinActivity::class.java))
        }
    }
}

