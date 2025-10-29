package dev.luisbaena.prodentclient.domain.usecase

import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import javax.inject.Inject

class DeleteAccountByEmailUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String): Result<String> {
        return authRepository.deleteAccountByEmail(email)
    }
}

