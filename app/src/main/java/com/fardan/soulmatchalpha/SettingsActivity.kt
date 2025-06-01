package com.fardan.soulmatchalpha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.settings)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val btnTentangAplikasi= findViewById<Button>(R.id.aboutButton)
        val btnFAQ= findViewById<Button>(R.id.faqButton)
        val btnExit= findViewById<Button>(R.id.exitButton)
        val btnProfile= findViewById<Button>(R.id.profileButton)
        val btnHistory= findViewById<Button>(R.id.historyButton)

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        btnTentangAplikasi.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
        }

        btnFAQ.setOnClickListener {
            startActivity(Intent(this, FaqActivity::class.java))
        }

        btnExit.setOnClickListener {
            finishAffinity()
        }

        btnProfile.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        btnHistory.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

    }
}