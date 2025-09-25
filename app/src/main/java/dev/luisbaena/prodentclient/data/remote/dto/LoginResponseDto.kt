package dev.luisbaena.prodentclient.data.remote.dto

import android.os.Message
import kotlinx.serialization.Serializable

@Serializable
data class LoginResponseDto(
    val success: Boolean,
    val message: String,
    val data : UserDto?
)
