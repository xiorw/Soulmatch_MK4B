package com.fardan.soulmatchalpha

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fardan.soulmatchalpha.adapter.MatchHistoryAdapter
import com.fardan.soulmatchalpha.adapter.TarotHistoryAdapter
import com.fardan.soulmatchalpha.model.MatchHistory
import com.fardan.soulmatchalpha.model.TarotHistory
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class HistoryActivity : AppCompatActivity() {

    private lateinit var matchRecyclerView: RecyclerView
    private lateinit var tarotRecyclerView: RecyclerView
    private lateinit var matchAdapter: MatchHistoryAdapter
    private lateinit var tarotAdapter: TarotHistoryAdapter

    private val matchList = mutableListOf<MatchHistory>()
    private val tarotList = mutableListOf<TarotHistory>()

    private lateinit var firestore: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    private val fullDateFormat = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
    private val dayOnlyFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val backIcon = findViewById<ImageButton>(R.id.backIcon)
        val settingsIcon = findViewById<ImageButton>(R.id.settingsIcon)

        backIcon.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        settingsIcon.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val clearButton = findViewById<Button>(R.id.btnClearHistory)
        clearButton.setOnClickListener {
            clearMatchHistories()
        }

        matchRecyclerView = findViewById(R.id.matchRecyclerView)
        tarotRecyclerView = findViewById(R.id.tarotRecyclerView)

        matchAdapter = MatchHistoryAdapter(matchList)
        tarotAdapter = TarotHistoryAdapter(tarotList)

        matchRecyclerView.layoutManager = LinearLayoutManager(this)
        tarotRecyclerView.layoutManager = LinearLayoutManager(this)

        matchRecyclerView.adapter = matchAdapter
        tarotRecyclerView.adapter = tarotAdapter

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        fetchMatchHistory()
        fetchTarotHistory()
    }

    private fun fetchMatchHistory() {
        val user = auth.currentUser ?: return
        val matchTypes = listOf("perfect_match", "date_match", "star_match")

        matchTypes.forEach { type ->
            firestore.collection("soulmatch_history")
                .document(user.uid)
                .collection(type)
                .get()
                .addOnSuccessListener { snapshot ->
                    for (doc in snapshot) {
                        val timestamp = doc.getDate("timestamp") ?: continue
                        val formattedDate = fullDateFormat.format(timestamp)

                        when (type) {
                            "perfect_match" -> matchList.add(
                                MatchHistory(
                                    timestamp = formattedDate,
                                    type = "Perfect Match",
                                    match_percentage = doc.getLong("match_percentage")?.toInt() ?: 0,
                                    your_name = doc.getString("your_name") ?: "-",
                                    partner_name = doc.getString("partner_name") ?: "-"
                                )
                            )
                            "date_match" -> matchList.add(
                                MatchHistory(
                                    timestamp = formattedDate,
                                    type = "Date Match",
                                    match_percentage = doc.getLong("match_percentage")?.toInt() ?: 0,
                                    your_birthdate = doc.getString("your_birthdate") ?: "-",
                                    partner_birthdate = doc.getString("partner_birthdate") ?: "-"
                                )
                            )
                            "star_match" -> matchList.add(
                                MatchHistory(
                                    timestamp = formattedDate,
                                    type = "Star Match",
                                    match_percentage = doc.getLong("match_percentage")?.toInt() ?: 0,
                                    your_zodiac = doc.getString("your_zodiac") ?: "-",
                                    partner_zodiac = doc.getString("partner_zodiac") ?: "-"
                                )
                            )
                        }
                    }
                    matchAdapter.notifyDataSetChanged()
                }
        }
    }

    private fun fetchTarotHistory() {
        val user = auth.currentUser ?: return
        val today = dayOnlyFormat.format(Date())

        firestore.collection("soulmatch_history")
            .document(user.uid)
            .collection("tarot_spin")
            .get()
            .addOnSuccessListener { snapshot ->
                for (doc in snapshot) {
                    val timestamp = doc.getDate("timestamp") ?: continue
                    val result = doc.getString("result") ?: continue
                    val tarotDate = dayOnlyFormat.format(timestamp)

                    // Hanya tambahkan jika tanggalnya hari ini
                    if (tarotDate == today) {
                        tarotList.add(
                            TarotHistory(
                                timestamp = fullDateFormat.format(timestamp),
                                result = result
                            )
                        )
                    }
                }
                tarotAdapter.notifyDataSetChanged()
            }
    }

    private fun clearMatchHistories() {
        val user = auth.currentUser ?: return
        val matchTypes = listOf("perfect_match", "date_match", "star_match")

        matchTypes.forEach { type ->
            firestore.collection("soulmatch_history")
                .document(user.uid)
                .collection(type)
                .get()
                .addOnSuccessListener { snapshot ->
                    for (doc in snapshot) {
                        doc.reference.delete()
                    }
                    matchList.clear()
                    matchAdapter.notifyDataSetChanged()
                }
        }

        Toast.makeText(this, "Match history berhasil dihapus", Toast.LENGTH_SHORT).show()

        // Tidak menghapus tarotList (otomatis dikelola per hari)
    }
}
