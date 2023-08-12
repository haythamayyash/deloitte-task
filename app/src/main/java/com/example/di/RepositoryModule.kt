package com.example.di

import com.example.data.local.AuthenticationDao
import com.example.data.repository.AuthenticationRepositoryImpl
import com.example.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    fun provideAuthenticationRepository(authenticationDao: AuthenticationDao): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationDao)
    }
}