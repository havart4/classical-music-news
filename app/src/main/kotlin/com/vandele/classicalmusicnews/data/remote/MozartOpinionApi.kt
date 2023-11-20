package com.vandele.classicalmusicnews.data.remote

import arrow.core.Either
import arrow.core.left
import com.vandele.classicalmusicnews.data.remote.dto.MozartOpinionDto
import com.vandele.classicalmusicnews.model.CmnError
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
import io.ktor.client.call.body
import io.ktor.client.plugins.timeout
import io.ktor.client.request.get
import javax.inject.Inject

class MozartOpinionApi @Inject constructor(private val httpClient: HttpClient) {
    private val baseUrl = "https://cmn-backend.vandele.repl.co/"

    suspend fun getMozartOpinion(
        articleLink: String,
    ): Either<CmnError, MozartOpinionDto> =
        getSuccessResponse {
            httpClient.get("$baseUrl/mozart?link=${urlEncode(articleLink)}") {
                timeout { connectTimeoutMillis = 60_000 }
            }
        }.map { response ->
            try {
                response.body()
            } catch (e: NoTransformationFoundException) {
                return CmnError.ParseError.left()
            }
        }
}
