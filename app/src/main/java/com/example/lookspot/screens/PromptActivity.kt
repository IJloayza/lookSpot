package com.example.lookspot.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.lookspot.extras.classes.Menu
import com.example.lookspot.R
import com.example.lookspot.extras.classes.AlbumManager
import com.example.lookspot.extras.classes.EstadisticaProvider
import com.example.lookspot.extras.classes.UserManager
import com.example.lookspot.extras.models.EstadisticaViewModel
import com.example.lookspot.extras.models.SongViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView


class PromptActivity : AppCompatActivity() {
    private val viewModel: SongViewModel by viewModels()
    private val estadisticaViewModel: EstadisticaViewModel by viewModels { EstadisticaViewModel.Factory }
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

        val promptRecover = intent.extras?.getString("recoveryPrompt")
        if (!promptRecover.isNullOrEmpty()) {
            prompt.setText(promptRecover)
        }

        // Cargando resultados mas o menos
        initLayoutSongs()
        initButtonSend()
        observeSongs()
    }

    private fun initLayoutSongs() {
        resultContainer = findViewById(R.id.resultContainer)
    }

    private fun initButtonSend() {
        val submit: ImageButton = findViewById(R.id.submitBtn)
        submit.setOnClickListener{
            var promptText = prompt.text.toString()
            val questionInflater = LayoutInflater.from(this)
                .inflate(R.layout.prompt_question, resultContainer, false)

            val textV: TextView = questionInflater.findViewById(R.id.textContent)
            textV.text = promptText

            resultContainer.addView(questionInflater)
            viewModel.listSongs(promptText)

        }
    }

    private fun initPromptText() {
        prompt = findViewById(R.id.input)
    }

    private fun observeSongs() {
        viewModel.songs.observe(this) { songs ->
            if (!songs.isNullOrEmpty()) {
                // Limpiar el contenedor antes de agregar nuevas vistas
                resultContainer.removeAllViews()

                //Llamar al firebaseStore para pasarle el total de canciones en este prompt y aumentar el numero de prompts en 1
                EstadisticaProvider.afegeixResult(songs)

                estadisticaViewModel.guardarEstadistica(
                    UserManager.getUser().id.toString())

                // Inflar cada canción en el LinearLayout
                for (song in songs) {
                    val songView = LayoutInflater.from(this).inflate(R.layout.item_song, resultContainer, false)
                    val songName = songView.findViewById<TextView>(R.id.titleSong)
                    val artistName = songView.findViewById<TextView>(R.id.artistSong)
                    val songImage = songView.findViewById<ImageView>(R.id.imageSong)
                    val buttonHeart: ImageButton = songView.findViewById(R.id.toggle_heart)

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
                                viewModel.postSong(selectedAlbum.id, song)
                                // Mostrar un mensaje de confirmación
                                //Añadir cancion asertada para el usuario
                                EstadisticaProvider.afegeixFav()
                                estadisticaViewModel.guardarEstadistica(
                                    UserManager.getUser().id.toString())
                                Toast.makeText(this, "Cançó afegida a ${selectedAlbum.nombre}", Toast.LENGTH_SHORT).show()
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()

                    }

                    // Asignar los datos de la cancion
                    songName.text = song.name
                    artistName.text = song.artist

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
