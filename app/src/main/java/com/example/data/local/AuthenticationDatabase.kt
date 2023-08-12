package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class AuthenticationDatabase : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao

    companion object{
        const val DATABASE_NAME= "deloitte_authentication_database"
    }
}