package com.example.deloittetask.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deloittetask.domain.model.User

@Dao
interface AuthenticationDao {
    @Insert
    suspend fun insertUsers(users: User)

    @Query("SELECT * FROM User WHERE email=:email AND password=:password")
    suspend fun getUser(email: String, password: String): User

    @Query("SELECT * FROM User WHERE nationalId=:id")
    suspend fun getUser(id: Long): User


}