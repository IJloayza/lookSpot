package com.example.lookspot

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.delay

class Loading : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_loading)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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