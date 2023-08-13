package com.example.deloittetask.data.repository

import com.example.deloittetask.data.datasource.ArticleLocalDataSource
import com.example.deloittetask.data.datasource.ArticleRemoteDataSource
import com.example.deloittetask.data.model.Articles
import com.example.deloittetask.domain.repository.ArticleRepository
import com.example.deloittetask.util.isConnectionException
import java.lang.Exception
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject

class ArticleRepositoryImpl @Inject constructor(
    private val articleLocalDataSource: ArticleLocalDataSource,
    private val articleRemoteDataSource: ArticleRemoteDataSource
) : ArticleRepository {

    override suspend fun getArticles(): List<Articles.Article?> {
        return try {
            updateArticleFromRemoteDataSource()
            articleLocalDataSource.getArticles()
        } catch (e: Exception) {
            if (e is UnknownHostException ||
                e is NoRouteToHostException ||
                e is SocketTimeoutException
            ) {
                articleLocalDataSource.getArticles()
            } else {
                throw Exception(e.message)
            }
        }
    }

    private suspend fun updateArticleFromRemoteDataSource() {
        val remoteArticles = articleRemoteDataSource.getArticle()
        remoteArticles?.articles?.let {
            articleLocalDataSource.deleteArticles()
            articleLocalDataSource.insertArticles(it)
        }
    }
}