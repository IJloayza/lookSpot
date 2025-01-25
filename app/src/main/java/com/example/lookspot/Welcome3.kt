package com.example.lookspot

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isEmpty
import java.util.zip.Inflater

class Welcome3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_welcome3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val next: Button = findViewById(R.id.nextWelcome)

        next.setOnClickListener{ _ ->
            startActivity(
                Intent(this, Welcome4::class.java)
            )
        }

        val container: LinearLayout = findViewById(R.id.containerSong)

        for (song in Album.listOfAlbum[0].listSong.shuffled().take(2)) {
            val songInflater = LayoutInflater.from(this)
                .inflate(R.layout.item_song, container, false)

            val imageSong: ImageView = songInflater.findViewById(R.id.imageSong)
            val titleSong: TextView = songInflater.findViewById(R.id.titleSong)
            val titleArtist: TextView = songInflater.findViewById(R.id.artistSong)
            val toggleHeart: ToggleButton = songInflater.findViewById(R.id.toggle_heart)

            val bitOptions: Options = Options().apply {
                inSampleSize = 4
            }
            val bitmap: Bitmap = BitmapFactory.decodeResource(resources, song.imagePort, bitOptions)

            imageSong.setImageBitmap(bitmap)
            titleSong.text = song.title
            titleArtist.text = song.artist
            toggleHeart.isChecked = false

            container.addView(songInflater)
        }

    }
}