package com.example.deloittetask.domain.repository

import com.example.deloittetask.data.model.Articles

interface ArticleRepository {
   suspend fun getArticles(): List<Articles.Article?>
}