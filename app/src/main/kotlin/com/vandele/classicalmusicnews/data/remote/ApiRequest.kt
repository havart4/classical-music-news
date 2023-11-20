package com.vandele.classicalmusicnews.data.remote

import arrow.core.Either
import arrow.core.left
import arrow.core.right
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
): Either<RemoteError, HttpResponse> {
    val response = try {
        block()
    } catch (unresolvedAddressException: UnresolvedAddressException) {
        return RemoteError.ConnectivityError.left()
    } catch (timeoutException: HttpRequestTimeoutException) {
        return RemoteError.TimeoutError.left()
    } catch (timeoutException: ConnectTimeoutException) {
        return RemoteError.TimeoutError.left()
    } catch (timeoutException: SocketTimeoutException) {
        return RemoteError.TimeoutError.left()
    } catch (throwable: Throwable) {
        if (throwable is CancellationException) throw throwable
        return RemoteError.Unknown(throwable).left()
    }
    return when (response.status.value) {
        in 200..299 -> response.right()
        in 400..499 -> RemoteError.ClientError(code = response.status.value).left()
        in 500..599 -> RemoteError.ServerError(code = response.status.value).left()
        else -> RemoteError.Unknown(ResponseException(response, response.bodyAsText())).left()
    }
}
