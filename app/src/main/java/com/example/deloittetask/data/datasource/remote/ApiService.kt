package com.example.deloittetask.data.datasource.remote

import com.example.deloittetask.BuildConfig.NY_TIMES_API_KEY
import com.example.deloittetask.data.model.Articles
import retrofit2.http.GET

interface ApiService {
    @GET("/svc/mostpopular/v2/viewed/30.json?api-key=$NY_TIMES_API_KEY")
    suspend fun getArticles(): Articles
}