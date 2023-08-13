package com.example.deloittetask.di

import com.example.deloittetask.BuildConfig
import com.example.deloittetask.data.datasource.remote.ApiService
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideGson() = Gson()

    @Provides
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BuildConfig.NY_TIMES_BASE_URL)
            .build()
    }

    @Provides
    fun provideShareService(retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
}