package com.example.lookspot.extras.models


data class User(
    val id: Int,
    val nombre: String,
    val correo: String,
    val albums: List<Album>
)

data class UserCreate(
    val nombre: String,
    val correo: String,
    val contrasena: String
)