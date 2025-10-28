package dev.luisbaena.prodentclient.domain.usecase


import dev.luisbaena.prodentclient.domain.model.User
import dev.luisbaena.prodentclient.domain.repository.AuthRepository
import javax.inject.Inject

class GetProfileUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<User> {
        return authRepository.getProfile()
    }
}