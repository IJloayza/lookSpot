package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView

class FavMusicActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav_music)

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        Menu.selectItemNavBar(navigationBar, this)

        val songs = intent.getParcelableArrayListExtra<Song>("songs")
        if (songs != null) {
            recyclerView = findViewById(R.id.recyclerViewFavorites)
            recyclerView.layoutManager = LinearLayoutManager(this)

            adapter = FavoritesAdapter(this, songs) { song ->
                SongManager.removeSong(song)
            }

            recyclerView.adapter = adapter
        }
    }
}