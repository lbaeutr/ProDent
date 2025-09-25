package dev.luisbaena.prodentclient.data.remote.dto

import dev.luisbaena.prodentclient.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val id: String,
    val nombre: String,
    val apellido: String,
    val email: String,
    val telefono: String,
    val token: String,
    val role: String
){
    fun toDomain(): User{
        return User(
            id = id,
            nombre = nombre,
            apellido = apellido,
            email = email,
            telefono = telefono,
            token = token,
            role = role
        )
    }
}
