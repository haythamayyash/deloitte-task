package com.example.deloittetask.data.local

import com.example.deloittetask.data.AuthenticationDataSource
import com.example.deloittetask.domain.model.User
import javax.inject.Inject

class AuthenticationLocalDataSource @Inject constructor(private val authenticationDao: AuthenticationDao) :
    AuthenticationDataSource {
    override fun insertUser(user: User) {
        authenticationDao.insertUsers(user)
    }

    override fun getUser(email: String, password: String): User {
        return authenticationDao.getUser(email, password)
    }

    override fun getUser(id: Long): User {
        return authenticationDao.getUser(id)
    }

}