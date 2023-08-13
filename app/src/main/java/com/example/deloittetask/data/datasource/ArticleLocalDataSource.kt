package com.example.deloittetask.data.datasource

import com.example.deloittetask.data.model.Articles

interface ArticleLocalDataSource {
    suspend fun getArticles(): Articles
    suspend fun insertArticles(articles: Articles)
    suspend fun deleteArticles()
}