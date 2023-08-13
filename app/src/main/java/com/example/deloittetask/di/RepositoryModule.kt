package com.example.deloittetask.di

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import com.example.deloittetask.data.AppPreferenceDataSource
import com.example.deloittetask.data.local.AppPreferenceDataSourceImpl
import com.example.deloittetask.data.local.AuthenticationLocalDataSource
import com.example.deloittetask.data.repository.AppPreferenceRepositoryImpl
import com.example.deloittetask.data.repository.AuthenticationRepositoryImpl
import com.example.deloittetask.domain.repository.AppPreferenceRepository
import com.example.deloittetask.domain.repository.AuthenticationRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    fun provideSharedPreference(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("deloitte", Context.MODE_PRIVATE)
    }

    @Provides
    fun provideAuthenticationRepository(authenticationLocalDataSource: AuthenticationLocalDataSource): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationLocalDataSource)
    }


    @Provides
    fun provideAppPreferenceRepository(appPreferenceDataSource: AppPreferenceDataSourceImpl): AppPreferenceRepository {
        return AppPreferenceRepositoryImpl(appPreferenceDataSource)
    }


}