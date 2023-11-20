package com.vandele.classicalmusicnews.repository

import arrow.core.Either
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.vandele.classicalmusicnews.data.local.database.CmnDatabase
import com.vandele.classicalmusicnews.data.local.database.entity.toArticle
import com.vandele.classicalmusicnews.data.local.database.entity.toArticleEntity
import com.vandele.classicalmusicnews.data.remote.ArticlesApi
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.model.RssSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val database: CmnDatabase,
    private val articlesApi: ArticlesApi,
) {
    fun getArticlesLocal(): Flow<List<Article>> = database.getAllArticles().map { articleEntities ->
        articleEntities.map { it.toArticle() }
    }

    fun getArticleLocal(articleId: String) =
        database.getArticle(articleId = articleId).map { it?.toArticle() }

    suspend fun insertArticlesLocal(articles: List<Article>) =
        database.insertArticles(articles.map { it.toArticleEntity() })

    suspend fun deleteArticlesLocal(articleIds: List<String>) = database.deleteArticles(articleIds)

    suspend fun getArticlesRemote(rssSource: RssSource): Either<CmnError, List<Article>> {
        return articlesApi.getArticles(url = rssSource.link).map { it.toArticles(rssSource) }
    }

    suspend fun bookmarkArticle(article: Article) {
        database.updateArticle(article.copy(isBookmarked = true).toArticleEntity())
    }

    suspend fun unbookmarkArticle(article: Article) {
        database.updateArticle(article.copy(isBookmarked = false).toArticleEntity())
    }
}

private fun RssChannel.toArticles(rssSource: RssSource) = items.map { it.toArticle(rssSource) }

private fun RssItem.toArticle(rssSource: RssSource?) = Article(
    author = author,
    id = link.let {
        if (it.isNullOrBlank()) UUID.randomUUID().toString() else it
    },
    image = image,
    link = link,
    pubDate = pubDate?.let { pubDateStringToInstant(it) },
    title = title,
    rssSource = rssSource,
    isBookmarked = false,
)

private fun pubDateStringToInstant(value: String): Instant? {
    val pubDateFormats = listOf(
        "EEE, dd MMM yyyy HH:mm:ss zzz",
        "EEE, dd MMM yyyy HH:mm zzz",
        "EEE, dd MMM yyyy HH:mm zzz",
        "EEE, dd MMM yyyy HH:mm:ss Z",
    )
    pubDateFormats.forEach { format ->
        try {
            val formatter = DateTimeFormatter.ofPattern(format, Locale.ENGLISH)
            return ZonedDateTime.parse(value, formatter).toInstant()
        } catch (e: DateTimeParseException) {
            // No-op
        }
    }
    return null
}
