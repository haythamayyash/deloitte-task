package com.example.deloittetask.data.datasource.remote

import com.example.deloittetask.data.datasource.ArticleRemoteDataSource
import com.example.deloittetask.data.model.Articles
import javax.inject.Inject

class ArticleRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    ArticleRemoteDataSource {
    override suspend fun getArticle(): Articles {
        return apiService.getArticles()
    }
}