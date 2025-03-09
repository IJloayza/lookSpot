package com.example.lookspot.screens

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lookspot.extras.classes.Menu
import com.example.lookspot.R
import com.example.lookspot.extras.classes.AlbumManager
import com.example.lookspot.extras.models.SongViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class PromptActivity : AppCompatActivity() {
    private val viewModel: SongViewModel by viewModels()
    private lateinit var prompt: EditText
    private lateinit var resultContainer: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_prompt)

        val bottomNavBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        val drawerNavBar : NavigationView = findViewById(R.id.nav_view)
        Menu.configureDrawerNavBar(drawerNavBar, this)
        Menu.configureBottomNavBar(bottomNavBar, this)
        initPromptText()
        initButtonSend()

        val promptRecover = intent.extras?.getString("recoveryPrompt")
        if (!promptRecover.isNullOrEmpty()) {
            prompt.setText(promptRecover)
        }

        // Cargando resultados mas o menos
        initLayoutSongs()
        observeSongs()
    }

    private fun initLayoutSongs() {
        val question = intent.extras?.getString("prompt")
        if (question.isNullOrEmpty()) return
        resultContainer = findViewById(R.id.resultContainer)

        val questionInflater = LayoutInflater.from(this)
            .inflate(R.layout.prompt_question, resultContainer, false)

        val textV: TextView = questionInflater.findViewById(R.id.textContent)
        textV.text = question

        resultContainer.addView(questionInflater)
    }

    private fun initButtonSend() {
        val submit: ImageButton = findViewById(R.id.submitBtn)
        submit.setOnClickListener{
            var promptText = prompt.text.toString()
            if(promptText != null){
                viewModel.listSongs(promptText)
                val intent = Intent(this, Loading::class.java)
                intent.putExtra("prompt", prompt.text.toString())
                startActivity(intent)
            }else{
                Toast.makeText(this, "S'ha de enviar un prompt", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun initPromptText() {
        prompt = findViewById(R.id.input)
    }

    private fun observeSongs() {
        viewModel.songs.observe(this) { songs ->
            if (songs != null && songs.isNotEmpty()) {
                // Limpiar el contenedor antes de agregar nuevas vistas
                resultContainer.removeAllViews()

                // Inflar cada canción en el LinearLayout
                for (song in songs) {
                    val songView = LayoutInflater.from(this).inflate(R.layout.item_song, resultContainer, false)
                    val songName = songView.findViewById<TextView>(R.id.titleSong)
                    val artistName = songView.findViewById<TextView>(R.id.artistSong)
                    val songImage = songView.findViewById<ImageView>(R.id.imageSong)
                    val buttonHeart: ToggleButton = songView.findViewById(R.id.toggle_heart)

                    buttonHeart.setOnClickListener{
                        //Si clican el boton se abre un AlertDialog que decide a cual album se va la cancion
                        val albums = AlbumManager.getAlbums()
                        val albumNames = albums.map { it.nombre }.toTypedArray()

                        AlertDialog.Builder(this)
                            .setTitle("Albums:")
                            .setItems(albumNames) { _, which ->
                                // Obtener el album seleccionado
                                val selectedAlbum = albums[which]

                                // Añadir al album
                                selectedAlbum.canciones += song
                                // Mostrar un mensaje de confirmación
                                Toast.makeText(this, "Cançó afegida a ${selectedAlbum.nombre}", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()

                    }

                    // Asignar los datos de la cancion
                    songName.text = song.nombre
                    artistName.text = song.artista

                    // Cargar imagen con Glide
                    Glide.with(this)
                        .load(song.image_url)
                        .into(songImage)

                    // Agregar la vista al contenedor
                    resultContainer.addView(songView)
                }
            } else {
                Toast.makeText(this, "No hay canciones disponibles", Toast.LENGTH_SHORT).show()
            }
        }
    }

}
