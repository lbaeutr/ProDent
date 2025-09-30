package dev.luisbaena.prodentclient.data.remote.api

import android.util.JsonToken
import dev.luisbaena.prodentclient.data.remote.dto.LoginRequestDto
import dev.luisbaena.prodentclient.data.remote.dto.LoginResponseDto
import dev.luisbaena.prodentclient.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApiService {

    @POST("/usuarios/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    // Todo: TERMINAR
    @POST("/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    @GET("/usuarios/me")
    suspend fun getProfile(@Header("Authorization") token: String): UserDto

    @POST
    suspend fun refreshToken(@Header("Authorization") token: String): LoginResponseDto
}