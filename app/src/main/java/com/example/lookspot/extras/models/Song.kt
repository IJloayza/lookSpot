package com.example.lookspot.extras.models

import android.os.Parcel
import android.os.Parcelable

data class Song(
    val id: String,
    val nombre: String,
    val artista: String,
    val url: String,
    val image_url: String,
    val duracion: Long,
    val albumType: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readLong() ?: 0,
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(nombre)
        parcel.writeString(artista)
        parcel.writeString(url)
        parcel.writeString(image_url)
        parcel.writeLong(duracion)
        parcel.writeString(albumType)
    }

    override fun describeContents(): Int = 0


    //Permite la creacion de Song en la lista de album que enviada en los intents
    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song = Song(parcel)
        override fun newArray(size: Int): Array<Song?> = arrayOfNulls(size)
    }
}