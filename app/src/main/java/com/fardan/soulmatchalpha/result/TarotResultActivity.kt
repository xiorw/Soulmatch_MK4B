package com.fardan.soulmatchalpha.result

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.fardan.soulmatchalpha.MainActivity
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.SettingsActivity
import com.fardan.soulmatchalpha.model.TarotCard

class TarotResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.tarot_result)

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val btnSettings = findViewById<ImageButton>(R.id.settingsIcon)
        val cardImage: ImageView = findViewById(R.id.tarotCard)
        val cardDescription: TextView = findViewById(R.id.cardDescription)
        val btnKembali: Button = findViewById(R.id.btnKembali)

        // List kartu harus sama persis dengan di TarotSpinActivity
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

        // Ambil index kartu dari Intent
        val cardIndex = intent.getIntExtra("cardIndex", -1)

        // Ambil kartu yang akan ditampilkan
        val cardToShow = if (cardIndex in cardDataList.indices) {
            cardDataList[cardIndex]
        } else {
            // Jika index tidak valid, tampilkan kartu random
            cardDataList.random()
        }

        cardImage.setImageResource(cardToShow.imageResId)
        cardDescription.text = cardToShow.description

        btnKembali.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        backIcon.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }
}
