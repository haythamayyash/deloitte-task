package com.example.deloittetask.di

import android.content.Context
import androidx.room.Room
import com.example.deloittetask.data.local.AuthenticationDao
import com.example.deloittetask.data.local.AuthenticationDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AuthenticationModule {
    @Provides
    fun provideAuthenticationDatabase(@ApplicationContext context : Context) =
        Room.databaseBuilder(context, AuthenticationDatabase::class.java, AuthenticationDatabase.DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideAuthenticationDAO(database: AuthenticationDatabase): AuthenticationDao {
        return database.authenticationDao()
    }
}