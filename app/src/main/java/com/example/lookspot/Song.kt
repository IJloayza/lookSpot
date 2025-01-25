package com.example.lookspot

import android.os.Parcel
import android.os.Parcelable

class Song(
    title: String,
    imagePort: Int,
    artist: String
): Parcelable {
    // Constructor secundario para leer desde un Parcel
    constructor(parcel: Parcel) : this(
    parcel.readString() ?: "",
    parcel.readInt(),
    parcel.readString() ?: ""
    )

    // Escribe los datos en el Parcel
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeInt(imagePort)
        parcel.writeString(artist)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }

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
}

// Clase para manejar canciones
object SongManager {
    val songs = ArrayList<Song>().apply {
        add(Song("Godzilla", R.drawable.img_musictobemurderedby, "Eminem"))
        add(Song("My eyes", R.drawable.img_utopia, "Travis Scott"))
        add(Song("Predator", R.drawable.img_predator, "Ice Cube"))
        add(Song("Annihilate", R.drawable.img_annihilate, "Metro Boomin"))
        add(Song("Zero", R.drawable.img_zero, "LYMK"))
        add(Song("I’ve been searching for you", R.drawable.img_centaur, "Jessie Mueller"))
        add(Song("I wonder", R.drawable.img_graduation, "Kanye West"))
        add(Song("Sicko mode", R.drawable.img_sickoworld, "Drake, Travis Scott"))
        add(Song("Superhero", R.drawable.img_heroesvillains, "Future"))
    }

    fun addSong(song: Song) {
        if (!songs.contains(song)) {
            songs.add(song)
        }
    }

    fun removeSong(song: Song) {
        songs.remove(song)
    }

    fun isFavorite(song: Song): Boolean {
        return AlbumManager.getFavorites().listSong.contains(song)
    }

}




