package com.fardan.soulmatchalpha.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.model.MatchHistory

class MatchHistoryAdapter(private val matchList: List<MatchHistory>) :
    RecyclerView.Adapter<MatchHistoryAdapter.MatchViewHolder>() {

    class MatchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val tvMatchType: TextView = itemView.findViewById(R.id.tvMatchType)
        val tvMatchPercentage: TextView = itemView.findViewById(R.id.tvMatchPercentage)
        val tvYourLabel: TextView = itemView.findViewById(R.id.tvYourLabel)
        val tvYourValue: TextView = itemView.findViewById(R.id.tvYourValue)
        val tvPartnerLabel: TextView = itemView.findViewById(R.id.tvPartnerLabel)
        val tvPartnerValue: TextView = itemView.findViewById(R.id.tvPartnerValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_match_history, parent, false)
        return MatchViewHolder(view)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        val match = matchList[position]
        holder.tvTimestamp.text = match.timestamp
        holder.tvMatchType.text = match.type
        holder.tvMatchPercentage.text = "${match.match_percentage}%"

        when (match.type) {
            "Perfect Match" -> {
                holder.tvYourLabel.text = "Your Name"
                holder.tvYourValue.text = match.your_name
                holder.tvPartnerLabel.text = "Partner Name"
                holder.tvPartnerValue.text = match.partner_name
            }
            "Date Match" -> {
                holder.tvYourLabel.text = "Your Birthdate"
                holder.tvYourValue.text = match.your_birthdate
                holder.tvPartnerLabel.text = "Partner Birthdate"
                holder.tvPartnerValue.text = match.partner_birthdate
            }
            "Star Match" -> {
                holder.tvYourLabel.text = "Your Zodiac"
                holder.tvYourValue.text = match.your_zodiac
                holder.tvPartnerLabel.text = "Partner Zodiac"
                holder.tvPartnerValue.text = match.partner_zodiac
            }
        }
    }

    override fun getItemCount(): Int = matchList.size
}
