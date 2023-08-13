package com.example.deloittetask.data

import com.example.deloittetask.domain.model.User

interface AuthenticationDataSource {
    fun insertUser(user: User)

    fun getUser(email: String, password: String): User

    fun getUser(id: Long): User
}