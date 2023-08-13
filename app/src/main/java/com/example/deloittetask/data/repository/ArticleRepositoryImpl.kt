package com.example.deloittetask.data.repository

import com.example.deloittetask.data.datasource.ArticleLocalDataSource
import com.example.deloittetask.data.datasource.ArticleRemoteDataSource
import com.example.deloittetask.data.model.Articles
import com.example.deloittetask.domain.repository.ArticleRepository
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun getArticle(): Articles {
        updateArticleFromRemoteDataSource()
        return articleLocalDataSource.getArticles()
    }

    private suspend fun updateArticleFromRemoteDataSource() {
        val remoteArticles = articleRemoteDataSource.getArticle()
        remoteArticles?.let {
            articleLocalDataSource.deleteArticles()
            articleLocalDataSource.insertArticles(it)
        }
    }
}