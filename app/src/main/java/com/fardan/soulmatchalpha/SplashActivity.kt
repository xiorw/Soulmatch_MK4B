package com.fardan.soulmatchalpha

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.fardan.soulmatchalpha.auth.SignInActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        // Timer untuk menampilkan splash screen selama 3 detik
        Handler(Looper.getMainLooper()).postDelayed({
            // Berpindah ke SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish() // Menutup SplashActivity agar tidak bisa kembali dengan tombol back
        }, 3000) // Durasi 3 detik
    }
}
