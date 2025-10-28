package dev.luisbaena.prodentclient.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ChangePasswordRequestDto(
    val password: String,
    val passwordRepeat: String
)