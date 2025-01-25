package com.example.lookspot

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView

class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val albumLayout = itemView.findViewById<ConstraintLayout>(R.id.albumLayout)
    val imageAlbumDefault: ImageView = itemView.findViewById(R.id.imageAlbum)
    val titleAlbum: TextView = itemView.findViewById(R.id.titleAlbum)
    val numberSong: TextView = itemView.findViewById(R.id.numberOfSongs)

    fun render(album:Album){
        imageAlbumDefault.setImageResource(R.drawable.folder)
        titleAlbum.text = album.title
        numberSong.text = album.numberOfSongs.toString()
        albumLayout.setOnClickListener {
            val i = Intent(itemView.context, FavMusicActivity::class.java)
            i.putParcelableArrayListExtra("songs", album.listSong)
            itemView.context.startActivity(i)
        }
        albumLayout.setOnLongClickListener {
            AlbumManager.removeAlbum(album)
            true
        }
    }
}