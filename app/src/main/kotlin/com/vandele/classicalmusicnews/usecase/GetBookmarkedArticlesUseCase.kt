package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetBookmarkedArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {
    operator fun invoke(): Flow<List<Article>> = articlesRepository.getArticlesLocal()
        .map { articles -> articles.filter { article -> article.isBookmarked } }
}
