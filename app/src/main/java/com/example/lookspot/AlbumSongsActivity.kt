package com.example.lookspot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class AlbumSongsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav_music)

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        Menu.configureBottomNavBar(navigationBar, this)
        //navigationBar.selectedItemId = R.id.favourite

        val songs = intent.getParcelableArrayListExtra<Song>("songs")
        if (songs != null) {
            recyclerView = findViewById(R.id.recyclerViewFavorites)
            recyclerView.layoutManager = LinearLayoutManager(this)

            adapter = SongsAdapter(this, songs) { song ->
                SongManager.removeSong(song)
            }

            recyclerView.adapter = adapter
        }
    }
}