package com.fardan.soulmatchalpha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.fardan.soulmatchalpha.auth.SignInActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import android.widget.EditText
import com.google.firebase.auth.UserProfileChangeRequest


class ProfileActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var tvName: TextView
    private lateinit var tvEmail: TextView
    private lateinit var btnLogout: Button
    private lateinit var btnVerify: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        auth = Firebase.auth
        tvName = findViewById(R.id.tvName)
        tvEmail = findViewById(R.id.tvEmail)
        btnLogout = findViewById(R.id.btnLogout)
        btnVerify = findViewById(R.id.btnVerify)

        val user = auth.currentUser
        if (user != null) {
            tvName.text = user.displayName ?: "No Name"
            tvEmail.text = user.email ?: "No Email"

            // Periksa apakah email sudah diverifikasi
            if (user.isEmailVerified) {
                btnVerify.isEnabled = false
                btnVerify.text = "Email Verified"
            } else {
                btnVerify.isEnabled = true
                btnVerify.text = "Verify Email"
            }
        } else {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        btnVerify.setOnClickListener {
            user?.sendEmailVerification()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Verification email sent! Please check your inbox.", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                }
            }
        }


        val btnRefresh = findViewById<Button>(R.id.btnRefresh)
        btnRefresh.setOnClickListener {
            user?.reload()?.addOnCompleteListener {
                if (user.isEmailVerified) {
                    btnVerify.isEnabled = false
                    btnVerify.text = "Email Has Been Verified"
                    Toast.makeText(this, "Email is now verified!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Email not verified yet!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        btnLogout.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }

        val etNewName = findViewById<EditText>(R.id.etNewName)
        val btnUpdateName = findViewById<Button>(R.id.btnUpdateName)

        btnUpdateName.setOnClickListener {
            val newName = etNewName.text.toString().trim()
            if (newName.isEmpty()) {
                Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(newName)
                .build()

            user?.updateProfile(profileUpdates)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    tvName.text = newName
                    Toast.makeText(this, "Name updated successfully", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Failed to update name", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }
}

