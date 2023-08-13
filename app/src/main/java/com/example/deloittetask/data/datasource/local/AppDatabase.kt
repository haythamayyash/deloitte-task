package com.example.deloittetask.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.deloittetask.data.converter.ArticleModelConverter
import com.example.deloittetask.data.model.Articles
import com.example.deloittetask.domain.model.User

@Database(entities = [User::class, Articles.Article::class], version = 1)
@TypeConverters(ArticleModelConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun authenticationDao(): AuthenticationDao
    abstract fun articleDao(): ArticleDao

    companion object {
        const val DATABASE_NAME = "deloitte_database"
    }
}