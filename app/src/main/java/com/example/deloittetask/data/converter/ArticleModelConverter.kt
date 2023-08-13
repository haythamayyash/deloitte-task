package com.example.deloittetask.data.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.example.deloittetask.data.model.Articles.Article

class ArticleModelConverter {
    private val delimiter = ","

    @TypeConverter
    fun fromResultList(articleList: List<Article?>?): String {
        return Gson().toJson(articleList)
    }

    @TypeConverter
    fun toResultList(json: String): List<Article?> {
        val type = object : TypeToken<List<Article?>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromMediaList(mediaList: List<Article.Media?>?): String {
        return Gson().toJson(mediaList)
    }

    @TypeConverter
    fun toMediaList(json: String): List<Article.Media?> {
        val type = object : TypeToken<List<Article.Media?>>() {}.type
        return Gson().fromJson(json, type)
    }

    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.joinToString(delimiter)
    }

    @TypeConverter
    fun toStringList(data: String?): List<String>? {
        return data?.split(delimiter)
    }
}

