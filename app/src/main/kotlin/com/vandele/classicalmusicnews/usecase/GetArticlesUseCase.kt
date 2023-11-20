package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {
    operator fun invoke(): Flow<List<Article>> = articlesRepository.getArticles()
}
