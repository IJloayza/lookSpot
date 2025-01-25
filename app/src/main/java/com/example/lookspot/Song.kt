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
