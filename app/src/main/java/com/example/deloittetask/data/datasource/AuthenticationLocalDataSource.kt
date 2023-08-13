package com.example.deloittetask.data.datasource

import com.example.deloittetask.domain.model.User

interface AuthenticationLocalDataSource {
    suspend fun insertUser(user: User)

    suspend fun getUser(email: String, password: String): User

    suspend fun getUser(id: Long): User
}