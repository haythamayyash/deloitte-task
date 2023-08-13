package com.example.deloittetask.di

import android.content.Context
import androidx.room.Room
import com.example.deloittetask.data.datasource.local.AuthenticationDao
import com.example.deloittetask.data.datasource.local.AppDatabase
import com.example.deloittetask.data.datasource.local.ArticleDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAuthenticationDAO(database: AppDatabase): AuthenticationDao {
        return database.authenticationDao()
    }

    @Provides
    fun provideArticleDAO(database: AppDatabase): ArticleDao {
        return database.articleDao()
    }
}