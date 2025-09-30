package dev.luisbaena.prodentclient.data.repository

import android.util.Log
import dev.luisbaena.prodentclient.data.local.preferencias.UserPreferences
import dev.luisbaena.prodentclient.data.remote.api.AuthApiService
import dev.luisbaena.prodentclient.data.remote.dto.LoginRequestDto
import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject


/*
 *  Logica de autenticacion conectadndose al API remoto y gestionando la sesion localmente.
 *  Implementa las operaciones definidas en AuthRepository.
 *  Login: llama al API, guarda la sesion localmente.
 *  Logout : llama al API (ignora errores), limpia la sesion local.(Aunque falle el API)
 *  getCurrentUser: obtiene el usuario logueado de las preferencias.
 *  saveUserSession: guarda el usuario manualmente en las preferencias.
 *  clearUserSession: limpia la sesion manualmente.
 *  isUserLoggedIn: verifica si hay un usuario logueado.
 *
 */

class AuthRepositoryImpl @Inject constructor(
    private val apiService: AuthApiService,
    private val userPreferences: UserPreferences
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<User> {
        return try {
            val request = LoginRequestDto(email, password)
            val loginResponse = apiService.login(request)

            if (loginResponse.token.isNotEmpty()) {
                // Obtener datos completos del usuario
                try {
                    val userDto = apiService.getProfile("Bearer ${loginResponse.token}")

                    val user = User(
                        id = "",
                        nombre = userDto.name,
                        apellido = userDto.lastname,
                        email = userDto.email,
                        telefono = userDto.phone,
                        token = loginResponse.token,
                        role = userDto.roles
                    )

                    userPreferences.saveUser(user)
                    Log.d(
                        "AuthRepo",
                        "Usuario guardado con perfil: email=${user.email} ,token=${user.token}"
                    )
                    Result.success(user)
                } catch (e: Exception) {
                    // Si falla obtener el perfil, guardar con datos mínimos
                    val user = User(
                        id = "",
                        nombre = "",
                        apellido = "",
                        email = email,
                        telefono = "",
                        role = "",
                        token = loginResponse.token
                    )
                    userPreferences.saveUser(user)
                    Log.d(
                        "AuthRepo",
                        "Usuario guardado con datos mínimos: token=${user.token}, email=${user.email}"
                    )
                    Result.success(user)

                }
            } else {
                Result.failure(Exception("Token vacío en la respuesta"))
            }
        } catch (e: HttpException) {
            val errorMessage = when (e.code()) {
                400 -> "Datos inválidos"
                401 -> "Email o contraseña incorrectos"
                404 -> "Usuario no encontrado"
                422 -> "Datos de entrada no válidos"
                500 -> "Error interno del servidor"
                502, 503 -> "Servicio no disponible temporalmente"
                else -> "Error de conexión (${e.code()})"
            }
            Result.failure(Exception(errorMessage))
        } catch (e: IOException) {
            Result.failure(Exception("Sin conexión a internet. Verifica tu conexión."))
        } catch (e: Exception) {
            Result.failure(Exception("Error inesperado: ${e.message}"))
        }
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            val currentUser = getCurrentUser()
            if (currentUser != null) {
                try {
                    apiService.logout("Bearer ${currentUser.token}")
                } catch (_: Exception) {
                    // Ignorar errores de logout
                }
            }
            userPreferences.clearUser() // Limpiar sesión local
            Result.success(Unit)
        } catch (_: Exception) {
            // Ignoramos errores de logout, incluso si falla la llamada, devolver OK!
            Result.success(Unit)
        }
    }

    override suspend fun getCurrentUser(): User? {
        return userPreferences.getUser()
    }

    override suspend fun saveUserSession(user: User) { // Corregido: era 'saverUserSession'
        userPreferences.saveUser(user)
    }

    override suspend fun clearUserSession() {
        userPreferences.clearUser()
    }

    override suspend fun isUserLoggedIn(): Boolean {
        return userPreferences.isLoggedIn()
    }
}
