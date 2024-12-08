package com.example.lookspot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Settings : AppCompatActivity() {
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
    }
}