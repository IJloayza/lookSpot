package com.example.lookspot

import android.content.Intent


import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class Menu{
    companion object{
        fun selectItemNavBar(navigationBar: BottomNavigationView, activity: AppCompatActivity) {
            when(activity) {
                is PromptActivity -> navigationBar.selectedItemId = R.id.home
                is SettingsActivity -> navigationBar.selectedItemId = R.id.settings
                is FavMusicActivity -> navigationBar.selectedItemId = R.id.favourite;
            }

            navigationBar.setOnItemSelectedListener { item ->
                if (item.itemId == navigationBar.selectedItemId) return@setOnItemSelectedListener false

                when (item.itemId) {
                    R.id.help -> {
                        val i = Intent(activity, Welcome::class.java)
                        activity.startActivity(i)
                        true
                    }
                    R.id.settings -> {
                        val i = Intent(activity, SettingsActivity::class.java)
                        activity.startActivity(i)
                        true
                    }
                    R.id.home -> {
                        val i = Intent(activity, PromptActivity::class.java)
                        activity.startActivity(i)
                        true
                    }
                    R.id.favourite -> {
                        val i = Intent(activity, AlbumActivity::class.java)
                        activity.startActivity(i)
                        true
                    }
                    else -> false
                }
            }
        }
    }
}