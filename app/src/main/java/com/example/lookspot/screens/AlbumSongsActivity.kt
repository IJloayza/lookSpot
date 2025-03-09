package com.example.lookspot.screens

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lookspot.extras.classes.AlbumManager
import com.example.lookspot.extras.classes.Menu
import com.example.lookspot.R
import com.example.lookspot.extras.classes.SongsAdapter
import com.example.lookspot.extras.models.Album
import com.example.lookspot.extras.models.SongViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AlbumSongsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongsAdapter
    private lateinit var navigationBar: BottomNavigationView
    private lateinit var drawerNavBar: NavigationView
    private lateinit var title: TextView
    private lateinit var returnBtn: ImageButton
    private val viewModel: SongViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_songs)

        initNavBarDrawBar()
        initTitle()
        initRecyclerView()
        initButtonBack()
    }

    private fun initButtonBack() {
        returnBtn = findViewById(R.id.backBtn)
        returnBtn.setOnClickListener{
            startActivity(Intent(this, AlbumActivity::class.java))
        }
    }

    private fun initRecyclerView() {
        val id = intent.getIntExtra("album",0)
        val album = AlbumManager.getAlbumById(id)

        title.text = album?.nombre

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        if (album != null) {
            adapter = SongsAdapter(this, album.canciones.toMutableList()) { song ->
                album.removeSong(song)
                viewModel.deleteSong(album.id, song.id)
            }
        }

        recyclerView.adapter = adapter
    }

    private fun initTitle() {
        title = findViewById(R.id.title)
    }

    private fun initNavBarDrawBar() {
        navigationBar = findViewById(R.id.nav_bar)
        drawerNavBar = findViewById(R.id.nav_view)
        Menu.configureBottomNavBar(navigationBar, this)
        Menu.configureDrawerNavBar(drawerNavBar, this)
    }
}