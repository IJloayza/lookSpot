package com.example.lookspot.extras.models

import android.os.Parcel
import android.os.Parcelable

data class Song(
    val id: String,
    val name: String,
    val artist: String,
    val url: String,
    val image_url: String,
    val duration: Long,
    val album_type: String
)

