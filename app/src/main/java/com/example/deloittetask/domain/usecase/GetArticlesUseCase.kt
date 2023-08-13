package com.example.deloittetask.domain.usecase

import com.example.deloittetask.data.model.Articles
import com.example.deloittetask.domain.repository.ArticleRepository
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articleRepository: ArticleRepository,
) {
    suspend fun execute(): List<Articles.Article?> {
        return articleRepository.getArticles()
    }
}