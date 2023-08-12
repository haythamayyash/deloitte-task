package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.domain.model.User

@Dao
interface AuthenticationDao {
    @Insert
    fun insertUsers(users: User)

    @Query("SELECT * FROM User WHERE email=:email AND password=:password")
    fun getUser(email: String, password: String): User
}