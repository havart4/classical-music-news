package com.vandele.classicalmusicnews.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.vandele.classicalmusicnews.model.Article
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import java.time.Instant
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale
import java.util.UUID
import javax.inject.Inject

class ArticlesApi @Inject constructor(private val httpClient: HttpClient) {
    suspend fun getArticles(url: String): Either<RemoteError, List<Article>> = getSuccessResponse {
        httpClient.get(url)
    }.map { response ->
        val rssChannel = try {
            RssParser().parse(response.bodyAsText())
        } catch (e: RssParsingException) {
            return RemoteError.Unknown(e).left()
        }
        rssChannel.toArticles()
    }
}

private fun RssChannel.toArticles() = items.map { it.toArticle() }

private fun RssItem.toArticle() = Article(
    author = author,
    id = UUID.randomUUID().toString(),
    image = image,
    link = link,
    pubDate = pubDate?.let { pubDateStringToInstant(it) },
    title = title,
)

fun pubDateStringToInstant(value: String): Instant? {
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