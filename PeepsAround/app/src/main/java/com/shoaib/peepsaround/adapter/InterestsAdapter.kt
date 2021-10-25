package com.shoaib.peepsaround.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.shoaib.peepsaround.R

class InterestsAdapter(val list: ArrayList<String>): RecyclerView.Adapter<InterestsAdapter.VH>() {
    class VH(v:View): RecyclerView.ViewHolder(v) {
        val data = v.findViewById<TextView>(R.id.test_id)

        fun bind( string: String)
        {
            data.text = string
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_interest_layout, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}