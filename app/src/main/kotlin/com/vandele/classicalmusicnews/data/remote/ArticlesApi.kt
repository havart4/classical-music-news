package com.vandele.classicalmusicnews.data.remote

import arrow.core.Either
import arrow.core.left
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.exception.RssParsingException
import com.prof18.rssparser.model.RssChannel
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import javax.inject.Inject

class ArticlesApi @Inject constructor(private val httpClient: HttpClient) {
    suspend fun getArticles(url: String): Either<RemoteError, RssChannel> = getSuccessResponse {
        httpClient.get(url)
    }.map { response ->
        val rssChannel = try {
            RssParser().parse(response.bodyAsText())
        } catch (e: RssParsingException) {
            return RemoteError.Unknown(e).left()
        }
        rssChannel
    }
}
