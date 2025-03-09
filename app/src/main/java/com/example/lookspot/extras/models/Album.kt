package com.example.lookspot.extras.models

import android.os.Parcel
import android.os.Parcelable

data class Album(
    val id: Int,
    var nombre: String,
    val id_usuario: Int,
    val usuario: User,
    var canciones: List<Song>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readParcelable(User::class.java.classLoader)!!,
        parcel.createTypedArrayList(Song) ?: emptyList()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(nombre)
        parcel.writeInt(id_usuario)
        parcel.writeParcelable(usuario, flags)
        parcel.writeTypedList(canciones)
    }

    override fun describeContents(): Int = 0


    //Permite la creacion de album en la lista de user cuando se envía por intent
    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album = Album(parcel)
        override fun newArray(size: Int): Array<Album?> = arrayOfNulls(size)
    }

    fun removeSong(song: Song) {
        if(canciones.contains(song)){
            canciones = canciones.filter { it.id != song.id }
        }
    }
}