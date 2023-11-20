package com.vandele.classicalmusicnews.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.vandele.classicalmusicnews.model.CmnError
import io.ktor.client.network.sockets.ConnectTimeoutException
import io.ktor.client.network.sockets.SocketTimeoutException
import io.ktor.client.plugins.HttpRequestTimeoutException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import io.ktor.util.network.UnresolvedAddressException
import kotlin.coroutines.cancellation.CancellationException

suspend inline fun getSuccessResponse(
    block: () -> HttpResponse,
): Either<CmnError, HttpResponse> {
    val response = try {
        block()
    } catch (unresolvedAddressException: UnresolvedAddressException) {
        return CmnError.ConnectionError.left()
    } catch (timeoutException: HttpRequestTimeoutException) {
        return CmnError.TimeoutError.left()
    } catch (timeoutException: ConnectTimeoutException) {
        return CmnError.TimeoutError.left()
    } catch (timeoutException: SocketTimeoutException) {
        return CmnError.TimeoutError.left()
    } catch (throwable: Throwable) {
        if (throwable is CancellationException) throw throwable
        return CmnError.Unknown(throwable).left()
    }
    return when (response.status.value) {
        in 200..299 -> response.right()
        in 400..499 -> CmnError.ClientError(code = response.status.value).left()
        in 500..599 -> CmnError.ServerError(code = response.status.value).left()
        else -> CmnError.Unknown(ResponseException(response, response.bodyAsText())).left()
    }
}
