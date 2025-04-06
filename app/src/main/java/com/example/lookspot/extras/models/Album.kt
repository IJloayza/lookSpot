package com.example.lookspot.extras.models

import android.os.Parcel
import android.os.Parcelable

data class Album(
    val id: Int,
    var nombre: String,
    val id_usuario: Int,
    val usuario: User,
    var canciones: List<Song>
) {


    fun removeSong(song: Song) {
        if(canciones.contains(song)){
            canciones = canciones.filter { it.id != song.id }
        }
    }
}