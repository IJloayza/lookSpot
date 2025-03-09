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

    fun isAlbum(album: Album): Boolean {
        return albums.contains(album)
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