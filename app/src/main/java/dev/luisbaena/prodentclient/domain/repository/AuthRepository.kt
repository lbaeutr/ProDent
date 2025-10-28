package dev.luisbaena.prodentclient.domain.repository

import dev.luisbaena.prodentclient.domain.model.User

/*
    * Define operaciones de autenticacion del usuario.
 */
interface AuthRepository {
    suspend fun login(email: String, password: String): Result<User>
    suspend fun logout(): Result<Unit>
    suspend fun getCurrentUser(): User?
    suspend fun saveUserSession(user: User)
    suspend fun clearUserSession()
    suspend fun isUserLoggedIn(): Boolean
    suspend fun getProfile(): Result<User>
    suspend fun updateProfile(
        nombre: String,
        apellido: String,
        email: String,
        telefono: String,
    ): Result<User>

    suspend fun changePassword(
        oldPassword: String,
        newPassword: String
    ): Result<Unit>
}