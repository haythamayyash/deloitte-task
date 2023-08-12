package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.AuthenticationRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend fun execute(email: String, password: String): User {
        return repository.getUser(email, password)
    }
}