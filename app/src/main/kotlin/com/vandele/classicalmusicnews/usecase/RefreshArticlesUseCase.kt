package com.vandele.classicalmusicnews.usecase

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.first
import javax.inject.Inject

private val sources = listOf(
    "https://thelistenersclub.com/feed/",
    "https://myscena.org/classical-music/feed/",
    "https://classicalmusicwithbigmike.com/feed/",
    "https://blog.naxos.com/feed",
    "https://theguardian.com/music/opera/rss",
)

class RefreshArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(): Either<CmnError, Unit> {
        return coroutineScope {
            // Fetch articles from each source
            val results = sources.map {
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

            // Make local storage reflect the fetched articles
            updateLocal(articles)

            return@coroutineScope Unit.right()
        }
    }

    private suspend fun updateLocal(fetchedArticles: List<Article>) {
        val articlesBeforeFetch = articlesRepository.getArticlesLocal().first()
        articlesRepository.insertArticlesLocal(fetchedArticles)
        val fetchedArticleIds = fetchedArticles.map { it.id }.toSet()
        val articlesToDelete = articlesBeforeFetch.filter { it.id !in fetchedArticleIds }
        articlesRepository.deleteArticlesLocal(articlesToDelete)
    }
}
