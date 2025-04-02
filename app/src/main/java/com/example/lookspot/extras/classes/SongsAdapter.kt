package com.example.lookspot.extras.classes

import android.content.Context
import android.graphics.BitmapFactory.Options
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lookspot.R
import com.example.lookspot.extras.models.Song

class SongsAdapter(
    private val context: Context,
    private val songsList: MutableList<Song>,
    private val onRemoveSong: (Song) -> Unit
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSong: ImageView = itemView.findViewById(R.id.imageSong)
        val titleSong: TextView = itemView.findViewById(R.id.titleSong)
        val titleArtist: TextView = itemView.findViewById(R.id.artistSong)
        val toggleHeart: ImageButton = itemView.findViewById(R.id.toggle_heart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songsList[position]

        Glide.with(context)
            .load(song.image_url)
            .into(holder.imageSong)

        holder.titleSong.text = song.nombre
        holder.titleArtist.text = song.artista

        holder.toggleHeart.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val songToRemove = songsList[currentPosition]

                // Eliminar la canción de la lista
                songsList.removeAt(currentPosition)

                // Notificar la eliminación al adaptador
                notifyItemRemoved(currentPosition)

                // Opcional: notificar los elementos restantes
                notifyItemRangeChanged(currentPosition, songsList.size)

                // Callback para manejar lógica adicional
                onRemoveSong(songToRemove)
            }
        }
    }

    override fun getItemCount(): Int {
        return songsList.size
    }


}
