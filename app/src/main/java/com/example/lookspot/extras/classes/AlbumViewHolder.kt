package com.example.lookspot.extras.classes

import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.lookspot.R
import com.example.lookspot.extras.models.Album
import com.example.lookspot.screens.AlbumSongsActivity
import kotlin.collections.plus

class AlbumViewHolder(
    itemView: View,
    private val adapter: RecyclerView.Adapter<AlbumViewHolder>
    ) : RecyclerView.ViewHolder(itemView) {
    val albumLayout: ViewGroup = itemView.findViewById<ConstraintLayout>(R.id.albumLayout)
    val imageAlbumDefault: ImageView = itemView.findViewById(R.id.imageAlbum)
    val titleAlbum: TextView = itemView.findViewById(R.id.titleAlbum)
    val numberSong: TextView = itemView.findViewById(R.id.numberOfSongs)
    val deleteBtn: ImageButton = itemView.findViewById(R.id.deleteBtn)

    fun render(position: Int, album: Album,  holder: AlbumViewHolder, onRemove: (Album) -> Unit, onUpdate: (Int, String) -> Unit){
        imageAlbumDefault.setImageResource(R.drawable.folder)
        titleAlbum.text = album.nombre
        numberSong.text = album.canciones.size.toString()
        albumLayout.setOnClickListener {
            val i = Intent(itemView.context, AlbumSongsActivity::class.java)
            i.putExtra("album", album.id)
            itemView.context.startActivity(i)
        }
        albumLayout.setOnLongClickListener {
            val builder = AlertDialog.Builder(itemView.context);
            builder.setTitle("Change name for album: ${album.nombre}");

            val input = EditText(itemView.context);
            builder.setView(input);

            builder.setPositiveButton("Change") {a, b ->
            val newName = input.text.toString().trim();
            if (!newName.isEmpty()) {
                AlbumManager.updateAlbum(album, newName)
                adapter.notifyItemChanged(position)
                onUpdate(album.id, newName)
            }else{
                Toast.makeText(itemView.context, "The name can`t be an empty field", Toast.LENGTH_SHORT).show()
            }
        }

            builder.setNegativeButton("Cancelar", null);
            builder.show();
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