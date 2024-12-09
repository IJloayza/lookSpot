package com.example.lookspot

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