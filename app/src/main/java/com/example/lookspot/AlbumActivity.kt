package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.widget.AutoCompleteTextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AlbumActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album)

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        val drawerNavBar : NavigationView = findViewById(R.id.nav_view)

        Menu.configureBottomNavBar(navigationBar, this)
        Menu.configureDrawerNavBar(drawerNavBar, this)

        initSearcher()
        initRecyclerView()

    }

    private fun initRecyclerView(){
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerAlbum)
        val manager = LinearLayoutManager(this)
        val decoration = DividerItemDecoration(this, manager.orientation)
        recyclerView.layoutManager = manager
        recyclerView.adapter = AlbumAdapter(AlbumManager.getAlbums())
        recyclerView.addItemDecoration(decoration)
    }

    private fun initSearcher() {
        val searcher = findViewById<SearchView>(R.id.searchAlbum)
        val searchAutoComplete = searcher.findViewById<AutoCompleteTextView>(androidx.appcompat.R.id.search_src_text)
        // Adapter amb la llista de albumes
        val adapter = AlbumArrayAdapter(this, AlbumManager.getAlbums())

        searchAutoComplete.setAdapter(adapter)
        searchAutoComplete.threshold = 1
        searchAutoComplete.setOnItemClickListener { _, _, position, _ ->
            val selectedAlbum = adapter.getItem(position)

            val intent = Intent(this, AlbumSongsActivity::class.java).apply {
                putExtra("albumId", selectedAlbum.id)
                putExtra("albumTitle", selectedAlbum.title)
                putParcelableArrayListExtra("songs", selectedAlbum.listSong)
            }

            startActivity(intent)
        }

        // Configurar el listener per canvis en el text
        searcher.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return true
            }
        })

        searcher.setOnCloseListener {
            false
        }
    }
}