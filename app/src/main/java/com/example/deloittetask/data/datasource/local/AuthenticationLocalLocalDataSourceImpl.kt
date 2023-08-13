package com.example.deloittetask.data.datasource.local

import com.example.deloittetask.data.datasource.AuthenticationLocalDataSource
import com.example.deloittetask.domain.model.User
import javax.inject.Inject

class AuthenticationLocalLocalDataSourceImpl @Inject constructor(private val authenticationDao: AuthenticationDao) :
    AuthenticationLocalDataSource {
    override suspend fun insertUser(user: User) {
        authenticationDao.insertUsers(user)
    }

    override suspend fun getUser(email: String, password: String): User {
        return authenticationDao.getUser(email, password)
    }

    override suspend fun getUser(id: Long): User {
        return authenticationDao.getUser(id)
    }

}