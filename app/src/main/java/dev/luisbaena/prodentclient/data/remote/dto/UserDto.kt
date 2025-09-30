package dev.luisbaena.prodentclient.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String,
    val roles: String
){
}
