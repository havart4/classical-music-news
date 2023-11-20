package com.vandele.classicalmusicnews.model

sealed interface UiState<out T> {
    data object Loading : UiState<Nothing>
    data class Content<out T>(val value: T) : UiState<T>
    data class Error(val error: RetryableError) : UiState<Nothing>
}

data class RetryableError(val cmnError: CmnError, val onRetry: () -> Unit)
