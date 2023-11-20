package com.vandele.classicalmusicnews.model

sealed interface RefreshState {
    data object Loading : RefreshState
    data object Content : RefreshState
    data class Error(val cmnError: CmnError) : RefreshState
}
