package com.example.lookspot.extras.classes

import com.example.lookspot.extras.models.Album

object AlbumManager {
    private var albums = ArrayList<Album>()

    fun addAlbum(album: Album) {
        if (!albums.contains(album)) {
            albums.add(album)
        }
    }

    fun removeAlbum(album: Album) {
        albums.remove(album)
    }

    fun updateAlbum(album: Album, newName: String){
        this.getAlbumById(album.id)?.nombre = newName
    }

    fun isAlbum(album: Album): Boolean {
        for (alb in albums) {
            if (alb.id == album.id) return true
        }
        return false
    }


    fun getAlbums(): List<Album> {
        return albums
    }

    fun setAlbums(albums: ArrayList<Album>) {
        this.albums = albums
    }

    fun getAlbumById(id: Int): Album? {
        for (album in albums) {
            if (album.id == id) return album
        }

        return null
    }

}