package com.example.lookspot.extras.classes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lookspot.R
import com.example.lookspot.extras.models.Album

class AlbumAdapter(
    val albumList:List<Album>,
    val onRemove: (Album) -> Unit,
    val onUpdate: (Int, String) -> Unit
    ) : RecyclerView.Adapter<AlbumViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AlbumViewHolder(
            layoutInflater.inflate(R.layout.item_album, parent, false),
            this,
        )
    }

    override fun getItemCount(): Int {
        return albumList.size
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val item = albumList[position]
        holder.render(position, item, holder, onRemove, onUpdate)
    }
}