package com.fardan.soulmatchalpha.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fardan.soulmatchalpha.R
import com.fardan.soulmatchalpha.model.TarotHistory

class TarotHistoryAdapter(private val tarotList: List<TarotHistory>) :
    RecyclerView.Adapter<TarotHistoryAdapter.TarotViewHolder>() {

    class TarotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTimestamp: TextView = itemView.findViewById(R.id.tvTimestamp)
        val tvResult: TextView = itemView.findViewById(R.id.tvResult)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TarotViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tarot_history, parent, false)
        return TarotViewHolder(view)
    }

    override fun onBindViewHolder(holder: TarotViewHolder, position: Int) {
        val tarot = tarotList[position]
        holder.tvTimestamp.text = tarot.timestamp
        holder.tvResult.text = tarot.result
    }

    override fun getItemCount(): Int = tarotList.size
}
