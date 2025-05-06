package com.example.lookspot.screens

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.lookspot.R
import com.example.lookspot.extras.classes.AlbumManager
import com.example.lookspot.extras.classes.UserManager
import com.example.lookspot.extras.models.Album
import com.example.lookspot.extras.models.UserViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText

    private val viewModel: UserViewModel by viewModels()

    companion object {
        var didWelcome: Boolean = false
        var idDispositiu = ""
        const val nomAplicacio = "LookSpot"
        const val idAplicacio = "lookSpot"
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

        idDispositiu = Settings.Secure.getString(
            getApplicationContext().contentResolver,
            Settings.Secure.ANDROID_ID
        )

        if (!didWelcome) {
            startActivity(Intent(this, Welcome::class.java))
        }

        // Inicializar vistas y botones
        initEditTexts()
        initButtonSign()

        observeUser()
    }

    private fun initEditTexts() {
        emailEdit = findViewById(R.id.emailAddress)
        passEdit = findViewById(R.id.password)
    }

    private fun initButtonSign() {
        val signIn = findViewById<Button>(R.id.credsLogin)
        signIn.setOnClickListener {
            val emailContent = emailEdit.text.toString()
            val passContent = passEdit.text.toString()

            if (emailContent.isEmpty() || passContent.isEmpty()) {
                Toast.makeText(this, "Els camps s'han de omplir", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.postUserLogin(emailContent, passContent)
            }
        }
    }

    private fun observeUser() {
        viewModel.user.observe(this) { user ->
            if (user != null) {

                val intent = Intent(this, PromptActivity::class.java)
                //Se pasa todos los albumes de user a AlbumManager
                UserManager.setUser(user)
                AlbumManager.setAlbums(user.albums as ArrayList<Album>)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error a la obtenció d'usuari(Incorrecte o no existeix)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}