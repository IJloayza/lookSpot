package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    companion object {
        var didWelcome: Boolean = false
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        if (!didWelcome) {
            startActivity(
                Intent(this, Welcome::class.java)
            )
        }
        val favourites:Album = Album("Favourites")
        favourites.listSong.add(Song("Godzilla", R.drawable.img_musictobemurderedby, "Eminem"))
        favourites.listSong.add(Song("My eyes", R.drawable.img_utopia, "Travis Scott"))
        favourites.listSong.add(Song("Predator", R.drawable.img_predator, "Ice Cube"))
        favourites.listSong.add(Song("Annihilate", R.drawable.img_annihilate, "Metro Boomin"))
        favourites.listSong.add(Song("Zero", R.drawable.img_zero, "LYMK"))
        favourites.listSong.add(Song("I’ve been searching for you", R.drawable.img_centaur, "Jessie Mueller"))
        favourites.listSong.add(Song("I wonder", R.drawable.img_graduation, "Kanye West"))
        favourites.listSong.add(Song("Sicko mode", R.drawable.img_sickoworld, "Drake, Travis Scott"))
        favourites.listSong.add(Song("Superhero", R.drawable.img_heroesvillains, "Future"))

        AlbumManager.addAlbum(favourites)

        val logins: ViewGroup = findViewById(R.id.containerButtons)

        for (i in 0 until logins.childCount) {
            val child = logins.getChildAt(i)
            if (child is Button) {
                child.setOnClickListener{
                    startActivity(
                        Intent(this, PromptActivity::class.java)
                    )
                }
            }
        }



    }
}