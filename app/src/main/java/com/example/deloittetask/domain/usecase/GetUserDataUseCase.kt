package com.example.deloittetask.domain.usecase

import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.repository.AuthenticationRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val repository: AuthenticationRepository) {
    suspend fun execute(id: Long): User? {
        return repository.getUserByID(id)
    }
}