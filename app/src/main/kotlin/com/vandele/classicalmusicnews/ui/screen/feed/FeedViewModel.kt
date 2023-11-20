package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.usecase.GetArticlesUseCase
import com.vandele.classicalmusicnews.usecase.RefreshArticlesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getArticlesUseCase: GetArticlesUseCase,
    private val refreshArticlesUseCase: RefreshArticlesUseCase,
) : ViewModel() {
    val articles = getArticlesUseCase()

    fun fetchArticles() {
        viewModelScope.launch {
            val result = refreshArticlesUseCase()
        }
    }
}
