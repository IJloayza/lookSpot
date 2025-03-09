package com.example.lookspot.extras.classes

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lookspot.R
import com.example.lookspot.extras.models.Album
import com.example.lookspot.screens.AlbumSongsActivity

class AlbumViewHolder(
    itemView: View,
    private val adapter: RecyclerView.Adapter<AlbumViewHolder>
    ) : RecyclerView.ViewHolder(itemView) {
    val albumLayout = itemView.findViewById<ConstraintLayout>(R.id.albumLayout)
    val imageAlbumDefault: ImageView = itemView.findViewById(R.id.imageAlbum)
    val titleAlbum: TextView = itemView.findViewById(R.id.titleAlbum)
    val numberSong: TextView = itemView.findViewById(R.id.numberOfSongs)
    val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)

    fun render(album: Album,  holder: AlbumViewHolder, onRemove: (Album) -> Unit){
        imageAlbumDefault.setImageResource(R.drawable.folder)
        titleAlbum.text = album.nombre
        numberSong.text = album.canciones.size.toString()
        albumLayout.setOnClickListener {
            val i = Intent(itemView.context, AlbumSongsActivity::class.java)
            i.putExtra("album", album.id)
            itemView.context.startActivity(i)
        }
        albumLayout.setOnLongClickListener {
            AlbumManager.removeAlbum(album)
            // borra de la pantalla el item
            adapter.notifyItemRemoved(holder.adapterPosition)
            true
        }
        deleteBtn.setOnClickListener {
            AlbumManager.removeAlbum(album)
            // borra de la pantalla el item
            adapter.notifyItemRemoved(holder.adapterPosition)
            onRemove(album)
        }
    }
}