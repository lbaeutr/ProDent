package dev.luisbaena.prodentclient.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateProfileRequestDto(
    val name: String,
    val lastname: String,
    val email: String,
    val phone: String
)

