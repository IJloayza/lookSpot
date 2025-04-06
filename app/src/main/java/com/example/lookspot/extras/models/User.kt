package com.example.lookspot.extras.models


data class User(
    val id: Int,
    val nombre: String,
    val correo: String,
    val albums: List<Album>
)