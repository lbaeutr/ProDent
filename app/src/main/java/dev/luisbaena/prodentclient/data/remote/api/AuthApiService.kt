package dev.luisbaena.prodentclient.data.remote.api

import android.util.JsonToken
import dev.luisbaena.prodentclient.data.remote.dto.LoginRequestDto
import dev.luisbaena.prodentclient.data.remote.dto.LoginResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    @POST("/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    @POST
    suspend fun refreshToken(@Header("Authorization") token: String): LoginResponseDto
}