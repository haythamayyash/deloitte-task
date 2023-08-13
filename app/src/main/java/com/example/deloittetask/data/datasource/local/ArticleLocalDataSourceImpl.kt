package com.example.deloittetask.data.datasource.local

import com.example.deloittetask.data.datasource.ArticleLocalDataSource
import com.example.deloittetask.data.model.Articles
import javax.inject.Inject

class ArticleLocalDataSourceImpl @Inject constructor(private val articleDao: ArticleDao) :
    ArticleLocalDataSource {
    override suspend fun getArticles(): List<Articles.Article> {
        return articleDao.getArticle()
    }

    override suspend fun insertArticles(articles: List<Articles.Article?>) {
        articleDao.insertArticle(articles)
    }

    override suspend fun deleteArticles() {
        articleDao.deleteArticles()
    }
}