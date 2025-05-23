package com.example.lookspot.screens

import android.annotation.SuppressLint
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
import com.example.lookspot.extras.testing.LogInValidator

class MainActivity : AppCompatActivity() {

    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText

    private val viewModel: UserViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Inicializar vistas y botones
        initEditTexts()
        initButtons()

        observeUser()
    }

    private fun initEditTexts() {
        emailEdit = findViewById(R.id.emailAddress)
        passEdit = findViewById(R.id.password)
    }

    private fun initButtons() {
        val logIn = findViewById<Button>(R.id.credsLogin)
        val signIn = findViewById<Button>(R.id.signIn)

        logIn.setOnClickListener {
            logIn()
        }

        signIn.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
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
                Toast.makeText(this, "Error finding user", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun logIn() {
        val emailContent = emailEdit.text.toString()
        val passContent = passEdit.text.toString()

        var isInvalid = false

        try {
            LogInValidator.validateEmail(emailContent)
        } catch (e: IllegalArgumentException) {
            emailEdit.error = e.message
            isInvalid = true
        }

        try {
            LogInValidator.validatePassword(passContent)
        } catch (e: IllegalArgumentException) {
            passEdit.error = e.message
            isInvalid = true
        }


        if (isInvalid) return

        viewModel.postUserLogin(emailContent, passContent)
    }
}