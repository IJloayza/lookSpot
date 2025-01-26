package com.example.lookspot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView

class AlbumArrayAdapter(context: Context,
                        private val albums: List<Album>
) : ArrayAdapter<Album>(context, R.layout.item_search, albums), Filterable {

    private var filteredAlbums = albums.toList()

    override fun getCount(): Int = filteredAlbums.size

    override fun getItem(position: Int): Album = filteredAlbums[position]

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_search, parent, false)

        val textView = view.findViewById<TextView>(R.id.albumName)

        textView.text = getItem(position).title

        return view
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val query = constraint?.toString()?.lowercase() ?: ""
                val results = if (query.isEmpty()) {
                    albums
                } else {
                    albums.filter { it.title.lowercase().contains(query) }
                }

                return FilterResults().apply {
                    values = results
                    count = results.size
                }
            }
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredAlbums = results?.values as? List<Album> ?: albums
                notifyDataSetChanged()
            }
        }
    }
}