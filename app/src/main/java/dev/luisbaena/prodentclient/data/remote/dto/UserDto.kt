package dev.luisbaena.prodentclient.data.remote.dto

import dev.luisbaena.prodentclient.domain.model.User
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val roles: String
) {
    // Mapea UserDto a User, agregando el token proporcionado
    fun toDomain(token: String) = User(
        id = "",
        nombre = name,
        apellido = lastname,
        email = email,
        telefono = phone,
        role = roles,
        token = token
    )
}
