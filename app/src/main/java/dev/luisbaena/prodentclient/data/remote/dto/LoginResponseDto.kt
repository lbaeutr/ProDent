package dev.luisbaena.prodentclient.data.remote.dto


import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val token: String,
)
