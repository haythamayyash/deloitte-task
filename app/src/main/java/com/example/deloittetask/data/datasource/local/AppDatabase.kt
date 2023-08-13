package com.example.deloittetask.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.deloittetask.domain.model.User

@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao
    abstract fun articleDao(): ArticleDao

    companion object{
        const val DATABASE_NAME= "deloitte_database"
    }
}