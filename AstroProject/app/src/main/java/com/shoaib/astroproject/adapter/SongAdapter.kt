package com.shoaib.astroproject.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.commit
import androidx.recyclerview.widget.RecyclerView
import com.shoaib.astroproject.R
import com.shoaib.astroproject.fragment.PlayMusicFragment
import com.shoaib.astroproject.model.SongItem


class SongAdapter(private val songList: ArrayList<SongItem>, val onItemClickListener: OnItemClickListener): RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        var imageSong = itemView.findViewById<ImageView>(R.id.image_song)
        var textSongName = itemView.findViewById<TextView>(R.id.text_songName)
        var textSongListening = itemView.findViewById<TextView>(R.id.text_songListen)
        var textSongLength = itemView.findViewById<TextView>(R.id.text_songLength)


        fun bindView(songItem: SongItem, clickListener: OnItemClickListener){
            imageSong.setImageResource(songItem.image)
            textSongName.text = songItem.songName
            textSongListening.text = songItem.songListening
            textSongLength.text = songItem.songLength

            itemView.setOnClickListener{
                clickListener.onItemClicked(songItem, layoutPosition)
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_song_layout,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(songList[position], onItemClickListener)

    }

    override fun getItemCount(): Int {
        return songList.size
    }
}

interface OnItemClickListener{
    fun onItemClicked(songItem: SongItem, position: Int)
}