package com.example.lookspot

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Settings : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val menu = supportFragmentManager.findFragmentById(R.id.fragMenu) as Menu
        menu.viewLifecycleOwnerLiveData.observe(this) { viewLifecycleOwner ->
            if (viewLifecycleOwner != null) {
                menu.setPageElementAsActive(Menu.Page.SETTINGS)
            }
        }


        // Obtener las preferencias compartidas
        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE)

        // Verificar si el tema oscuro está habilitado desde SharedPreferences
        val isDarkMode = preferences.getBoolean("dark_mode", false)

        // Configurar el estado del switch según el tema actual
        val darkModeSwitch: SwitchCompat = findViewById(R.id.themeMode)
        darkModeSwitch.isChecked = isDarkMode

        // Configurar el listener para el switch
        darkModeSwitch.setOnCheckedChangeListener { _, isChecked ->
            // Si el estado del interruptor ha cambiado
            if (isChecked) {
                // Cambiar a modo oscuro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                preferences.edit().putBoolean("dark_mode", true).apply()
            } else {
                // Cambiar a modo claro
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                preferences.edit().putBoolean("dark_mode", false).apply()
            }
        }


    }
}