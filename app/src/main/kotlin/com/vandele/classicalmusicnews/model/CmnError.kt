package com.vandele.classicalmusicnews.model

sealed interface CmnError {
    data class ClientError(val code: Int) : CmnError
    data class ServerError(val code: Int) : CmnError
    data class Unknown(val reason: Throwable) : CmnError
    data object ConnectionError : CmnError
    data object TimeoutError : CmnError
    data object ParseError : CmnError
}