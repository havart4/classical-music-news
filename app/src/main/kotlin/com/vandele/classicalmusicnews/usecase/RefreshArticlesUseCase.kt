package com.vandele.classicalmusicnews.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.model.RssSource
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RefreshArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(): Either<CmnError, Unit> {
        return coroutineScope {
            // Fetch articles from each source
            val results = RssSource.values().map {
                async { articlesRepository.getArticlesRemote(it) }
            }.awaitAll()

            // Get a list of successfully fetched articles
            val articles = results.mapNotNull { it.getOrNull() }.flatten()

            // Return the first error if we failed to fetch any articles
            if (articles.isEmpty()) {
                results.forEach {
                    if (it is Either.Left<CmnError>) {
                        return@coroutineScope it
                    }
                }
                return@coroutineScope CmnError.Unknown(Throwable("articles was empty")).left()
            }

            updateLocal(articles)

            return@coroutineScope Unit.right()
        }
    }

    /**
     * Update locally saved articles to correspond with the articles fetched from the backend.
     * Locally saved articles that are neither bookmarked nor in [fetchedArticles] will be deleted.
     */
    private suspend fun updateLocal(fetchedArticles: List<Article>) {
        val locallySavedArticleIds =
            articlesRepository.getArticlesLocal().first().map { it.id }.toSet()
        val bookmarkedArticleIds = articlesRepository.getArticlesLocal().first()
            .filter { it.isBookmarked }
            .map { it.id }
            .toSet()
        val fetchedArticleIds = fetchedArticles.map { it.id }.toSet()

        // Update existing and add new articles
        articlesRepository.insertArticlesLocal(
            fetchedArticles.map {
                if (it.id in bookmarkedArticleIds) {
                    it.copy(isBookmarked = true)
                } else {
                    it
                }
            }
        )

        // Delete articles that are neither bookmarked nor in fetchedArticles
        val articleIdsToDelete = locallySavedArticleIds.filter {
            it !in bookmarkedArticleIds && it !in fetchedArticleIds
        }
        articlesRepository.deleteArticlesLocal(articleIdsToDelete)
    }
}
