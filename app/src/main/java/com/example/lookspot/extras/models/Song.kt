package com.example.lookspot.extras.models

data class Song(
    val id: String,
    val name: String,
    val artist: String,
    val url: String,
    val image_url: String,
    val duration: Long,
    val album_type: String
)

