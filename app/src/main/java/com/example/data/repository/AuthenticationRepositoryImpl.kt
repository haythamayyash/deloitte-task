package com.example.data.repository

import com.example.data.local.AuthenticationDao
import com.example.domain.model.User
import com.example.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val authenticationDao: AuthenticationDao) :
    AuthenticationRepository {
    override suspend fun insertUser(user: User) {
        authenticationDao.insertUsers(user)
    }

    override suspend fun getUser(userName: String, password: String): User {
        return authenticationDao.getUser(userName, password)
    }


}