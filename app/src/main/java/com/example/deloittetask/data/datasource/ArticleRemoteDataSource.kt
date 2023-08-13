package com.example.deloittetask.data.datasource

import com.example.deloittetask.data.model.Articles

interface ArticleRemoteDataSource {
    suspend fun getArticle(): Articles?
}