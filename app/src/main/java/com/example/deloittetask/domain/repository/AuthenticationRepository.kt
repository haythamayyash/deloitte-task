package com.example.deloittetask.domain.repository

import com.example.deloittetask.domain.model.User

interface AuthenticationRepository {
   suspend fun insertUser(user: User)
   suspend fun getUser(userName: String, password: String) : User?
   suspend fun getUserByID(id: Long) : User?
}