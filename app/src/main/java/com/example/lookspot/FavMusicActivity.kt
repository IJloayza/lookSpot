package com.example.lookspot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavMusicActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavoritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fav_music)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val menu = supportFragmentManager.findFragmentById(R.id.fragMenu) as Menu
        menu.viewLifecycleOwnerLiveData.observe(this) { viewLifecycleOwner ->
            if (viewLifecycleOwner != null) {
                menu.setPageElementAsActive(Menu.Page.FAVORITES)
            }
        }

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val favoriteSongs = FavoritesManager.getFavorites().toMutableList()
        adapter = FavoritesAdapter(this, favoriteSongs) { song ->
            FavoritesManager.removeSong(song)
        }

        recyclerView.adapter = adapter

    }
}