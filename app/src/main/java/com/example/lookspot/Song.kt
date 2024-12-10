package com.example.lookspot

import java.util.Arrays

class Song(
    title: String,
    imagePort: Int,
    artist: String
) {
    var title: String = title
        get() = field
        set(value) {
            field = value
        }

    var imagePort: Int = imagePort
        get() = field
        set(value) {
            field = value
        }

    var artist: String = artist
        get() = field
        set(value) {
            field = value
        }

    companion object{
        var listSong: ArrayList<Song> = ArrayList()
            get() = field
            set(value) {
                field = value
            }
    }
}

// Clase para manejar favoritos
object FavoritesManager {
    private val favoriteSongs = ArrayList<Song>()

    fun addSong(song: Song) {
        if (!favoriteSongs.contains(song)) {
            favoriteSongs.add(song)
        }
    }

    fun removeSong(song: Song) {
        favoriteSongs.remove(song)
    }

    fun isFavorite(song: Song): Boolean {
        return favoriteSongs.contains(song)
    }

    fun getFavorites(): List<Song> {
        return favoriteSongs
    }
}