package com.example.deloittetask.data.datasource

import com.example.deloittetask.data.model.Articles

interface ArticleLocalDataSource {
    suspend fun getArticles(): List<Articles.Article?>
    suspend fun insertArticles(articles: List<Articles.Article?>)
    suspend fun deleteArticles()
}