package com.example.deloittetask.data.repository

import com.example.deloittetask.data.datasource.local.AuthenticationLocalLocalDataSourceImpl
import com.example.deloittetask.domain.model.User
import com.example.deloittetask.domain.repository.AuthenticationRepository
import javax.inject.Inject

class AuthenticationRepositoryImpl @Inject constructor(private val authenticationLocalDataSource: AuthenticationLocalLocalDataSourceImpl) :
    AuthenticationRepository {
    override suspend fun insertUser(user: User) {
        authenticationLocalDataSource.insertUser(user)
    }

    override suspend fun getUser(userName: String, password: String): User {
        return authenticationLocalDataSource.getUser(userName, password)
    }

    override suspend fun getUserByID(id: Long): User {
        return authenticationLocalDataSource.getUser(id)
    }


}