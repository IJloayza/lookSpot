package com.example.lookspot

class Album(
    title:String) {

    val id = auto_increment++

    var title: String = title
        get() = field
        set(value) {
            field = value
        }

    var listSong: ArrayList<Song> = ArrayList()
        get() = field
        set(value) {
            field = value
        }


    fun addSong(song: Song) {
        listSong.add(song)
    }

    fun removeSong(song: Song) {
        listSong.remove(song)
    }

    companion object {
        private var auto_increment = 0
    }


}


object AlbumManager {
    private val albums = ArrayList<Album>() .apply {
        add(Album("Favourites").apply {
            listSong = SongManager.songs.shuffled()
                .take(4) as ArrayList<Song>
        })
    }

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

    fun getFavorites(): Album {
        return albums[0]
    }

    fun getAlbums(): List<Album> {
        return albums
    }

    fun getAlbumById(id: Int): Album? {
        for (album in albums) {
            if (album.id == id) return album
        }

        return null
    }

}