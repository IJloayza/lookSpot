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
        set(value) {
            field = value
        }


    fun addSong(song: Song) {
        listSong.add(song)
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

}