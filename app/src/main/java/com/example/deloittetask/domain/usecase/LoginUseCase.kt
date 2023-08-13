package com.example.deloittetask.domain.usecase

import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.repository.AppPreferenceRepository
import com.example.deloittetask.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val appPreferenceRepository: AppPreferenceRepository
) {
    suspend fun execute(email: String, password: String): User? {
        val user = authenticationRepository.getUser(email, password)
        user?.let {
            appPreferenceRepository.isLoggedIn = true
            appPreferenceRepository.nationalId = user.nationalId
        }
        return user
    }
}