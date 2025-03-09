package com.example.lookspot.screens

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.lookspot.R

class Loading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading)


        val newIntent = Intent(this, PromptActivity::class.java)

        // Crear el Handler y el Runnable
        val handler = Handler(Looper.getMainLooper())
        val runnable = Runnable {
            // Enviar prompt de nuevo al home para procesar las respuestas
            newIntent.putExtra("prompt", intent.extras?.getString("prompt"))
            startActivity(newIntent)
        }

        // Programar la ejecución con el Handler
        handler.postDelayed(runnable, 3000)

        // Botón cancelar
        val cancel: Button = findViewById(R.id.cancel_button)
        cancel.setOnClickListener {
            // Cancelar el Handler
            handler.removeCallbacks(runnable)
            // Redirigir al Prompt con "recoveryPrompt"
            newIntent.putExtra("recoveryPrompt", intent.extras?.getString("prompt"))
            startActivity(newIntent)
        }

    }
}