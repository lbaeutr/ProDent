package dev.luisbaena.prodentclient.domain.model

data class User(
    val id: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val token: String,
    val role: String
)
