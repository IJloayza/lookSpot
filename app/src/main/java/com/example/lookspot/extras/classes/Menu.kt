package com.example.lookspot.extras.classes

import android.content.Context
import android.content.Intent
import android.view.MenuItem
import android.widget.ImageView


import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.lookspot.R
import com.example.lookspot.screens.AlbumActivity
import com.example.lookspot.screens.AlbumSongsActivity
import com.example.lookspot.screens.MainActivity
import com.example.lookspot.screens.PromptActivity
import com.example.lookspot.screens.SettingsActivity
import com.example.lookspot.screens.Welcome
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class Menu{
    companion object{
        private fun redirectTo(activity: AppCompatActivity, context: Context) {
            val i = Intent(context, activity::class.java)
            context.startActivity(i)
        }

        fun configureDrawerNavBar(navBar: NavigationView, activity: AppCompatActivity ) {

            when(activity) {
                is PromptActivity -> navBar.setCheckedItem(R.id.home)
                is SettingsActivity -> navBar.setCheckedItem(R.id.settings)
                is AlbumActivity -> navBar.setCheckedItem(R.id.albums);
                else -> "do nothing";
            }

            val toggle = activity.findViewById<ImageView>(R.id.menu_btn)
            val drawer = activity.findViewById<DrawerLayout>(R.id.main)

            toggle.setOnClickListener{
                drawer.openDrawer(GravityCompat.START)
            }

            navBar.setCheckedItem(R.id.home)
            navBar.setNavigationItemSelectedListener { item: MenuItem ->

                when (item.itemId) {
                    R.id.home -> redirectTo(PromptActivity(), activity)
                    R.id.albums -> redirectTo(AlbumActivity(), activity)
                    R.id.settings -> redirectTo(SettingsActivity(), activity)
                    R.id.help -> redirectTo(Welcome(), activity)
                    R.id.logout -> redirectTo(MainActivity(), activity)
                    else -> return@setNavigationItemSelectedListener false
                }
                true
            }
        }

        fun configureBottomNavBar(navigationBar: BottomNavigationView, activity: AppCompatActivity) {
            when(activity) {
                is PromptActivity -> navigationBar.selectedItemId = R.id.home
                is SettingsActivity -> navigationBar.selectedItemId = R.id.settings
                is AlbumActivity -> navigationBar.selectedItemId = R.id.albums;
                is AlbumSongsActivity -> "do nothing";
            }

            navigationBar.setOnItemSelectedListener { item ->
                if (item.itemId == navigationBar.selectedItemId) return@setOnItemSelectedListener false

                when (item.itemId) {
                    R.id.home -> redirectTo(PromptActivity(), activity)
                    R.id.albums -> redirectTo(AlbumActivity(), activity)
                    R.id.settings -> redirectTo(SettingsActivity(), activity)
                    R.id.help -> redirectTo(Welcome(), activity)
                    else -> return@setOnItemSelectedListener false
                }
                true
            }
        }
    }
}