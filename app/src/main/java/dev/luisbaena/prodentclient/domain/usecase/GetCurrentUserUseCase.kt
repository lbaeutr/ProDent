package dev.luisbaena.prodentclient.domain.usecase

import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import javax.inject.Inject


// Este código obtiene el usuario actualmente autenticado.
// Si no hay ninguno, devuelve null.

class GetCurrentUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): User? {
        return authRepository.getCurrentUser()
    }
}