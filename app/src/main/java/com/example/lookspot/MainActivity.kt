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