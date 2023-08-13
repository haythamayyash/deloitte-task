package com.example.deloittetask.data.datasource.local

import com.example.deloittetask.data.datasource.ArticleLocalDataSource
import com.example.deloittetask.data.model.Articles
import javax.inject.Inject

class ArticleLocalDataSourceImpl @Inject constructor(private val articleDao: ArticleDao) :
    ArticleLocalDataSource {
    override suspend fun getArticles(): Articles {
        return articleDao.getArticle()
    }

    override suspend fun insertArticles(articles: Articles) {
        articleDao.insertArticle(articles)
    }

    override suspend fun deleteArticles() {
        articleDao.deleteArticles()
    }
}