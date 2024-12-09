package com.example.lookspot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PromptActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prompt)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Song.listSong.add(Song("Godzilla", R.drawable.img_musictobemurderedby, "Eminem"))
        Song.listSong.add(Song("Predator", R.drawable.img_predator, "Ice Cube"))
        Song.listSong.add(Song("My eyes", R.drawable.img_utopia, "Travis Scott"))
        Song.listSong.add(Song("Annihilate", R.drawable.img_annihilate, "Metro Boomin"))
        Song.listSong.add(Song("Zero", R.drawable.img_zero, "LYMK"))
        Song.listSong.add(Song("I’ve been searching for you", R.drawable.img_centaur, "Jessie Mueller"))
        Song.listSong.add(Song("I wonder", R.drawable.img_graduation, "Kanye West"))
        Song.listSong.add(Song("Sicko mode", R.drawable.img_sickoworld, "Drake, Travis Scott"))
        Song.listSong.add(Song("Superhero", R.drawable.img_heroesvillains, "Future"))

        val menu = supportFragmentManager.findFragmentById(R.id.fragMenu) as Menu
        menu.viewLifecycleOwnerLiveData.observe(this) { viewLifecycleOwner ->
            if (viewLifecycleOwner != null) {
                menu.setPageElementAsActive(Menu.Page.HOME)
            }
        }
    }
}