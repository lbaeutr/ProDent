package dev.luisbaena.prodentclient.data.remote.api

import android.util.JsonToken
import dev.luisbaena.prodentclient.data.remote.dto.ChangePasswordRequestDto
import dev.luisbaena.prodentclient.data.remote.dto.DeleteAccountResponseDto
import dev.luisbaena.prodentclient.data.remote.dto.LoginRequestDto
import dev.luisbaena.prodentclient.data.remote.dto.LoginResponseDto
import dev.luisbaena.prodentclient.data.remote.dto.ProfileResponseDto
import dev.luisbaena.prodentclient.data.remote.dto.ProfileUserDto
import dev.luisbaena.prodentclient.data.remote.dto.UpdateProfileRequestDto
import dev.luisbaena.prodentclient.data.remote.dto.UserDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface AuthApiService {

    /**
     * Login - Devuelve token
     */
    @POST("/usuarios/login")
    suspend fun login(@Body request: LoginRequestDto): LoginResponseDto

    /**
     * Logout - Invalida el token
     */
    @POST("/logout")
    suspend fun logout(@Header("Authorization") token: String): Response<Unit>

    /**
     * Obtener perfil del usuario actual
     */
    @GET("/usuarios/me")
    suspend fun getProfile(@Header("Authorization") token: String): ProfileUserDto

    /**
     * Actualizar perfil del usuario actual
     */    @PUT("usuarios/me")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body profile: UpdateProfileRequestDto
    ): ProfileResponseDto

    /**
     * Cambiar contraseña del usuario actual
     */
    @PUT("usuarios/me")
    suspend fun changePassword(
        @Header("Authorization") token: String,
        @Body password: ChangePasswordRequestDto
    ): Response<Unit>

    /**
     * Eliminar cuenta de un usuario siendo Admin
     */
    @DELETE("usuarios/{email}")
    suspend fun deleteAccount(
        @Path("email") email: String,
        @Header("Authorization") token: String
    ): DeleteAccountResponseDto


    // todo implementar refresh token en el backend
    @POST
    suspend fun refreshToken(@Header("Authorization") token: String): LoginResponseDto
}