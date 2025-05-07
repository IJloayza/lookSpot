package com.example.lookspot.screens

import android.content.Intent
import android.os.Bundle
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
import com.example.lookspot.extras.models.UserCreate
import com.example.lookspot.extras.models.UserViewModel
import com.example.lookspot.extras.testing.LogInValidator
import com.example.lookspot.extras.testing.SignUpValidator

class SignUpActivity : AppCompatActivity() {

    private val viewModel: UserViewModel by viewModels()

    private lateinit var nameEdit: EditText
    private lateinit var emailEdit: EditText
    private lateinit var passEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_sign_up)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initFields()
        initButtons()
        initObservers()
    }

    private fun initFields() {
        nameEdit = findViewById(R.id.fullName)
        emailEdit = findViewById(R.id.emailAddress)
        passEdit = findViewById(R.id.password)
    }

    private fun initButtons() {
        val logInBtn = findViewById<Button>(R.id.credsLogin)
        val signUpBtn = findViewById<Button>(R.id.signIn)

        signUpBtn.setOnClickListener {
            signUp()
        }

        logInBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun initObservers() {
        viewModel.user.observe(this) { user ->
            if (user != null) {

                val intent = Intent(this, PromptActivity::class.java)
                //Se pasa todos los albumes de user a AlbumManager
                UserManager.setUser(user)
                AlbumManager.setAlbums(user.albums as ArrayList<Album>)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error creating user", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun signUp() {
        val nameContent = nameEdit.text.toString()
        val emailContent = emailEdit.text.toString()
        val passContent = passEdit.text.toString()

        var isInvalid = false

        try {
            SignUpValidator.validateName(nameContent)
        } catch (e: IllegalArgumentException) {
            nameEdit.error = e.message
            isInvalid = true
        }

        try {
            LogInValidator.validateEmail(emailContent)
        } catch (e: IllegalArgumentException) {
            emailEdit.error = e.message
            isInvalid = true
        }

        try {
            SignUpValidator.validatePassword(passContent)
        } catch (e: IllegalArgumentException) {
            passEdit.error = e.message
            isInvalid = true
        }


        if (isInvalid) return


        viewModel.createUser(nameContent, emailContent, passContent)
    }
}