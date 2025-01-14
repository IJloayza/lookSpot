package com.example.lookspot

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BitmapFactory.Options
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView


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

        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        Menu.selectItemNavBar(navigationBar, this)
        navigationBar.selectedItemId = R.id.home


        val prompt: EditText = findViewById(R.id.input)
        val submit: ImageButton = findViewById(R.id.submitBtn)

        submit.setOnClickListener{ _ ->

            val intent = Intent(this, Loading::class.java)
            intent.putExtra("prompt", prompt.text.toString())
            startActivity(intent)
        }


        val promptRecover = intent.extras?.getString("recoveryPrompt")
        if (!promptRecover.isNullOrEmpty()) {
            prompt.setText(promptRecover)
        }


        // Cargando resultados mas o menos
        val question = intent.extras?.getString("prompt")
        if (question.isNullOrEmpty()) return

        val resultContainer : LinearLayout = findViewById(R.id.resultContainer)

        val questionInflater = LayoutInflater.from(this)
            .inflate(R.layout.prompt_question, resultContainer, false)

        val textV: TextView = questionInflater.findViewById(R.id.textContent)
        textV.text = question

        resultContainer.addView(questionInflater)

        for (song in Song.listSong.shuffled().take(4)) {
            val songInflater = LayoutInflater.from(this)
                .inflate(R.layout.item_song, resultContainer, false)

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
            toggleHeart.isChecked = FavoritesManager.isFavorite(song)

            toggleHeart.setOnClickListener { _ ->

                if (FavoritesManager.isFavorite(song))
                    FavoritesManager.removeSong(song)
                else
                    FavoritesManager.addSong(song)

            }

            resultContainer.addView(songInflater)
        }




    }

}
