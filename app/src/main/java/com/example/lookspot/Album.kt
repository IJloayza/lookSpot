package com.example.lookspot

class Album(
    title:String) {

    var title: String = title
        get() = field
        set(value) {
            field = value
        }

    var listSong: ArrayList<Song> = ArrayList()
        get() = field

    var numberOfSongs = listSong.size
        get() = field
    companion object{
        val listOfAlbum : ArrayList<Album> = ArrayList()
            get() = field
    }
}
// Clase para manejar canciones
object SongManager {
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

object AlbumManager {
    private val albums = ArrayList<Album>()

    fun addAlbum(album: Album) {
        if (!albums.contains(album)) {
            albums.add(album)
        }
    }

    fun removeAlbum(album: Album) {
        albums.remove(album)
    }

    fun isAlbum(album: Album): Boolean {
        return albums.contains(album)
    }

    fun getAlbums(): List<Album> {
        return albums
    }
}