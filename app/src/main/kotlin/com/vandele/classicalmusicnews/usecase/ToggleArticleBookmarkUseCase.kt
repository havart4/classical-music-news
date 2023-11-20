package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import javax.inject.Inject

class ToggleArticleBookmarkUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(article: Article) {
        if (article.isBookmarked) {
            articlesRepository.unbookmarkArticle(article)
        } else {
            articlesRepository.bookmarkArticle(article)
        }
    }
}
