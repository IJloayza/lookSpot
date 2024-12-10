package com.example.lookspot

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.ToggleButton
import androidx.recyclerview.widget.RecyclerView

class FavoritesAdapter(
    private val context: Context,
    private val favoriteSongs: MutableList<Song>,
    private val onRemoveSong: (Song) -> Unit
) : RecyclerView.Adapter<FavoritesAdapter.SongViewHolder>() {

    class SongViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageSong: ImageView = itemView.findViewById(R.id.imageSong)
        val titleSong: TextView = itemView.findViewById(R.id.titleSong)
        val titleArtist: TextView = itemView.findViewById(R.id.artistSong)
        val toggleHeart: ToggleButton = itemView.findViewById(R.id.toggle_heart)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_song, parent, false)
        return SongViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {


        val bitOptions: Options = Options().apply {
            inSampleSize = 4
        }

        val song = favoriteSongs[position]

        val bitmap: Bitmap = BitmapFactory.decodeResource(context.resources, song.imagePort, bitOptions)
        holder.imageSong.setImageBitmap(bitmap)
        holder.titleSong.text = song.title
        holder.titleArtist.text = song.artist
        holder.toggleHeart.isChecked = true

        holder.toggleHeart.setOnClickListener {
            val currentPosition = holder.adapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val songToRemove = favoriteSongs[currentPosition]

                // Eliminar la canción de la lista
                favoriteSongs.removeAt(currentPosition)

                // Notificar la eliminación al adaptador
                notifyItemRemoved(currentPosition)

                // Opcional: notificar los elementos restantes
                notifyItemRangeChanged(currentPosition, favoriteSongs.size)

                // Callback para manejar lógica adicional
                onRemoveSong(songToRemove)
            }
        }
    }

    override fun getItemCount(): Int {
        return favoriteSongs.size
    }


}
