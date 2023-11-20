package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.repository.ArticlesRepository
import javax.inject.Inject

class GetArticleUseCase @Inject constructor(private val articlesRepository: ArticlesRepository) {
    suspend operator fun invoke(articleId: String) =
        articlesRepository.getArticleLocal(articleId = articleId)
}
