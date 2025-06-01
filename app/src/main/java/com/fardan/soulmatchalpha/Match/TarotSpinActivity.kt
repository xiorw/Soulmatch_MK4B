package com.fardan.soulmatchalpha.Match

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.fardan.soulmatchalpha.MainActivity
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.SettingsActivity
import com.fardan.soulmatchalpha.result.TarotResultActivity
import com.fardan.soulmatchalpha.model.TarotCard
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class TarotSpinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.tarot_spin)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.tarotspin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val bannerPerfectMatch = findViewById<ImageButton>(R.id.bannerImage)
        val btnBaca = findViewById<Button>(R.id.readCardButton)
        val tarotCard = findViewById<ImageView>(R.id.tarotCard)

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        bannerPerfectMatch.setOnClickListener {
            startActivity(Intent(this, PerfectMatchActivity::class.java))
        }

        btnBaca.setOnClickListener {
            val user = auth.currentUser
            if (user == null) {
                Toast.makeText(this, "Silakan login terlebih dahulu", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val today = dateFormat.format(Date())

            firestore.collection("soulmatch_history")
                .document(user.uid)
                .collection("tarot_spin")
                .get()
                .addOnSuccessListener { result ->
                    val alreadySpunToday = result.any { doc ->
                        val timestamp = doc.getTimestamp("timestamp")?.toDate()
                        timestamp != null && dateFormat.format(timestamp) == today
                    }

                    if (alreadySpunToday) {
                        Toast.makeText(this, "Kamu sudah membaca kartu hari ini. Coba lagi besok ya!", Toast.LENGTH_LONG).show()
                    } else {
                        // Lanjut spin kartu
                        val cardDataList = listOf(
                            TarotCard(R.drawable.tarot1, "The Heart: Cie lagi sama-sama excited, ya? Pertahanin terus komunikasinya. Good Luck!"),
                            TarotCard(R.drawable.tarot2, "The Sun: Kalau hati udah kesayat, jangan maksa lagi. Lebih baik pergi dan sembuhkan luka."),
                            TarotCard(R.drawable.tarot3, "The Cloud: Dia cuma dateng sekilas, kayak awan yang lewat begitu cepat, ninggalin kamu di sini nunggu tanpa kepastian."),
                            TarotCard(R.drawable.tarot4, "The Hourglass: Jam pasirnya hampir kosong, tapi dia masih belum juga datang. Masih mau nunggu? Mau nunggu sampe kapan?"),
                            TarotCard(R.drawable.tarot5, "The Water: Diem diem ada air mata yang turun, ya? Gapapa, cari aktivitas yang seru, selain nunggu dia, oke?"),
                            TarotCard(R.drawable.tarot6, "The Ballons: Kalian bagai balon yang terikat, bersatu dalam perjalanan ini, takkan pernah terpisah."),
                            TarotCard(R.drawable.tarot7, "The Flower: Cintamu dan dia membuat hati mekar seperti bunga di musim semi, penuh warna dan kehidupan."),
                            TarotCard(R.drawable.tarot8, "The Ring: Dengan cincin ini, kalian mengikat kisah cinta indah, menandakan komitmen seumur hidup."),
                            TarotCard(R.drawable.tarot9, "The Lightbulb: Cinta kalian kaya lentera malam, bersinar kalo chat-an pas malam doang. Shift malam kah?")
                        )

                        val randomIndex = cardDataList.indices.random()
                        val selectedCard = cardDataList[randomIndex]

                        val data = hashMapOf(
                            "timestamp" to Calendar.getInstance().time,
                            "result" to selectedCard.description
                        )

                        firestore.collection("soulmatch_history")
                            .document(user.uid)
                            .collection("tarot_spin")
                            .add(data)
                            .addOnSuccessListener {
                                Toast.makeText(this, "Hasil tarot berhasil disimpan", Toast.LENGTH_SHORT).show()
                                val intent = Intent(this, TarotResultActivity::class.java)
                                intent.putExtra("cardIndex", randomIndex)
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this, "Gagal menyimpan hasil: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Gagal memeriksa riwayat tarot: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
