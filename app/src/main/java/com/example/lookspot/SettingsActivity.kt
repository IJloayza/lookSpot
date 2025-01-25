package com.example.lookspot

import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SwitchCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class SettingsActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_settings)


        val navigationBar = findViewById<BottomNavigationView>(R.id.nav_bar)
        Menu.configureBottomNavBar(navigationBar, this)
        //navigationBar.selectedItemId = R.id.settings

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