package com.example.deloittetask.di

import android.content.Context
import android.content.SharedPreferences
import com.example.deloittetask.data.datasource.ArticleLocalDataSource
import com.example.deloittetask.data.datasource.ArticleRemoteDataSource
import com.example.deloittetask.data.datasource.local.AppPreferenceDataSourceImpl
import com.example.deloittetask.data.datasource.local.ArticleDao
import com.example.deloittetask.data.datasource.local.ArticleLocalDataSourceImpl
import com.example.deloittetask.data.datasource.local.AuthenticationLocalLocalDataSourceImpl
import com.example.deloittetask.data.datasource.remote.ApiService
import com.example.deloittetask.data.datasource.remote.ArticleRemoteDataSourceImpl
import com.example.deloittetask.data.repository.AppPreferenceRepositoryImpl
import com.example.deloittetask.data.repository.ArticleRepositoryImpl
import com.example.deloittetask.data.repository.AuthenticationRepositoryImpl
import com.example.deloittetask.domain.repository.AppPreferenceRepository
import com.example.deloittetask.domain.repository.ArticleRepository
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
    fun provideAuthenticationRepository(authenticationLocalDataSource: AuthenticationLocalLocalDataSourceImpl): AuthenticationRepository {
        return AuthenticationRepositoryImpl(authenticationLocalDataSource)
    }


    @Provides
    fun provideAppPreferenceRepository(appPreferenceDataSource: AppPreferenceDataSourceImpl): AppPreferenceRepository {
        return AppPreferenceRepositoryImpl(appPreferenceDataSource)
    }

    @Provides
    fun provideArticleLocalDataSource(
        articleDao: ArticleDao
    ): ArticleLocalDataSource {
        return ArticleLocalDataSourceImpl(articleDao)
    }

    @Provides
    fun provideArticleRemoteDataSource(
        apiService: ApiService
    ): ArticleRemoteDataSource {
        return ArticleRemoteDataSourceImpl(apiService)
    }

    @Provides
    fun provideArticleRepository(
        articleLocalDataSource: ArticleLocalDataSource,
        articleRemoteDataSource: ArticleRemoteDataSource
    ): ArticleRepository {
        return ArticleRepositoryImpl(articleLocalDataSource, articleRemoteDataSource)
    }
}