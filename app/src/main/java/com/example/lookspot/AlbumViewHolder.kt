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

    fun render(album:Album, adapter: RecyclerView.Adapter<AlbumViewHolder>, holder: AlbumViewHolder){
        imageAlbumDefault.setImageResource(R.drawable.folder)
        titleAlbum.text = album.title
        numberSong.text = album.listSong.size.toString()
        albumLayout.setOnClickListener {
            val i = Intent(itemView.context, AlbumSongsActivity::class.java)
            i.putParcelableArrayListExtra("songs", album.listSong)
            i.putExtra("albumTitle", album.title)
            i.putExtra("albumId", album.id)
            itemView.context.startActivity(i)
        }
        albumLayout.setOnLongClickListener {
            AlbumManager.removeAlbum(album)
            // borra de la pantalla el item
            adapter.notifyItemRemoved(holder.adapterPosition)
            true
        }
    }
}