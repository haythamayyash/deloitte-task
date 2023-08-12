package com.example.domain.repository

import com.example.domain.model.User

interface AuthenticationRepository {
   suspend fun insertUser(user: User)
   suspend fun getUser(userName: String, password: String) : User
}