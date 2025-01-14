package com.example.lookspot

import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu{
    companion object{
        fun selectItemNavBar(navigationBar: BottomNavigationView, activity: AppCompatActivity) {
            navigationBar.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.help -> {
                        val i = Intent(activity, Welcome::class.java)
                        activity.startActivity(i)
                        true
                    }
                    R.id.settings -> {
                        val i = Intent(activity, Settings::class.java)
                        activity.startActivity(i)
                        true
                    }
                    R.id.home -> {
                        val i = Intent(activity, PromptActivity::class.java)
                        activity.startActivity(i)
                        true
                    }
                    R.id.favourite -> {
                        val i = Intent(activity, FavMusicActivity::class.java)
                        activity.startActivity(i)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}