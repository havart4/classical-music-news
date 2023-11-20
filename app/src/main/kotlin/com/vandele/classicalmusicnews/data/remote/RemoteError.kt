package com.vandele.classicalmusicnews.data.remote

sealed interface RemoteError {
    data class ClientError(val code: Int) : RemoteError
    data class ServerError(val code: Int) : RemoteError
    data class Unknown(val reason: Throwable) : RemoteError
    object ConnectivityError : RemoteError
    object TimeoutError : RemoteError
}
