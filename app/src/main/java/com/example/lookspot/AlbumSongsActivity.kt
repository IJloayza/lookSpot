package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AlbumSongsActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SongsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_album_songs)

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        val drawerNavBar : NavigationView = findViewById(R.id.nav_view)
        Menu.configureBottomNavBar(navigationBar, this)
        Menu.configureDrawerNavBar(drawerNavBar, this)


        val title = findViewById<TextView>(R.id.title)

        //val songs = intent.getParcelableArrayListExtra<Song>("songs")
        val albumId = intent.getIntExtra("albumId", 0)

        val album = AlbumManager.getAlbumById(albumId) ?: return

        title.text = album.title

        recyclerView = findViewById(R.id.recyclerViewFavorites)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = SongsAdapter(this, album.listSong) { song ->
            album.removeSong(song)
        }

        recyclerView.adapter = adapter

        val returnBtn = findViewById<ImageButton>(R.id.backBtn)

        returnBtn.setOnClickListener{
            startActivity(Intent(this, AlbumActivity::class.java))
        }
    }
}