package com.example.domain.usecase

import com.example.domain.model.User
import com.example.domain.repository.AuthenticationRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend fun execute(user: User) {
        repository.insertUser(user)
    }
}