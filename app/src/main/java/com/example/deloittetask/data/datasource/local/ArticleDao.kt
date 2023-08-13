package com.example.deloittetask.data.datasource.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.deloittetask.data.model.Articles

@Dao
interface ArticleDao {

    @Insert
    suspend fun insertArticle(users: Articles)

    @Query("SELECT * FROM Articles")
    suspend fun getArticle(): Articles

    @Query("DELETE FROM Articles")
    suspend fun deleteArticles()
}