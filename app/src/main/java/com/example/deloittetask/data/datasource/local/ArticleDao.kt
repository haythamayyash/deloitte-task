package com.example.deloittetask.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deloittetask.data.model.Articles

@Dao
interface ArticleDao {

    @Insert
    suspend fun insertArticle(articles: List<Articles.Article?>)

    @Query("SELECT * FROM article")
    suspend fun getArticle(): List<Articles.Article>

    @Query("DELETE FROM article")
    suspend fun deleteArticles()
}