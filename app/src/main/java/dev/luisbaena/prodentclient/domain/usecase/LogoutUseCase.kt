package dev.luisbaena.prodentclient.domain.usecase

import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import javax.inject.Inject


//Este código cierra la sesión del usuario y limpia sus datos.
// Si algo falla, igual borra la sesión y sigue adelante.

class LogoutUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<Unit>{
        return try {
            authRepository.clearUserSession()
            authRepository.logout()

        } catch (e: Exception){

            authRepository.clearUserSession()
            Result.success(Unit)
        }
    }
}