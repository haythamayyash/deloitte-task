package com.example.deloittetask.domain.usecase

import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.repository.AppPreferenceRepository
import com.example.deloittetask.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val appPreferenceRepository: AppPreferenceRepository
) {
    suspend fun execute(user: User) {
        authenticationRepository.insertUser(user)
        appPreferenceRepository.nationalId = user.nationalId
    }
}