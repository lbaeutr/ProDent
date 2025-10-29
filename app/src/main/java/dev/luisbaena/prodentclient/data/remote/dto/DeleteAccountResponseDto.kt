package dev.luisbaena.prodentclient.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class DeleteAccountResponseDto(
    val message: String,
    val status: Int? = null,
    val error: String? = null,
    val timestamp: String? = null,
    val path: String? = null
)

//TODO: revisar si es necesario ampliar el DTO con los campos status, error, timestamp, path ...
//@Serializable
//data class DeleteAccountResponseDto(
//    val message: String
//)