package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.RefreshState
import com.vandele.classicalmusicnews.model.RetryableError
import com.vandele.classicalmusicnews.model.UiState
import com.vandele.classicalmusicnews.usecase.GetArticlesUseCase
import com.vandele.classicalmusicnews.usecase.RefreshArticlesUseCase
import com.vandele.classicalmusicnews.usecase.ToggleArticleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val refreshArticlesUseCase: RefreshArticlesUseCase,
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
    getArticlesUseCase: GetArticlesUseCase,
) : ViewModel() {
    private val isPullRefreshing = MutableStateFlow(false)
    private val articlesRefreshState = MutableStateFlow<RefreshState>(RefreshState.Loading)

    val feedState: Flow<UiState<FeedContent>> = combine(
        getArticlesUseCase(),
        articlesRefreshState,
        isPullRefreshing,
    ) { articles, refreshStatus, isPullRefreshing ->
        combineToUiState(articles, refreshStatus, isPullRefreshing)
    }

    fun onResume() {
        refreshArticles()
    }

    private fun onPullRefresh() {
        isPullRefreshing.value = true
        if (articlesRefreshState.value is RefreshState.Loading) return
        refreshArticles()
    }

    private fun refreshArticles() {
        articlesRefreshState.value = RefreshState.Loading
        viewModelScope.launch {
            refreshArticlesUseCase()
                .onLeft { articlesRefreshState.value = RefreshState.Error(it) }
                .onRight { articlesRefreshState.value = RefreshState.Content }
            isPullRefreshing.value = false
        }
    }

    private fun combineToUiState(
        articles: List<Article>,
        articlesRefreshState: RefreshState,
        isPullRefreshing: Boolean,
    ): UiState<FeedContent> =
        if (articles.isNotEmpty()) {
            UiState.Content(
                FeedContent(
                    articles = articles,
                    isPullRefreshing = isPullRefreshing,
                    onPullRefresh = ::onPullRefresh,
                    onBookmarkClicked = ::onBookmarkClicked,
                )
            )
        } else {
            when (articlesRefreshState) {
                is RefreshState.Content -> UiState.Content(
                    FeedContent(
                        articles = articles,
                        isPullRefreshing = isPullRefreshing,
                        onPullRefresh = ::onPullRefresh,
                        onBookmarkClicked = ::onBookmarkClicked,
                    )
                )
                is RefreshState.Error -> UiState.Error(
                    RetryableError(
                        cmnError = articlesRefreshState.cmnError,
                        onRetry = ::refreshArticles,
                    )
                )
                is RefreshState.Loading -> UiState.Loading
            }
        }

    private fun onBookmarkClicked(article: Article) {
        viewModelScope.launch { toggleArticleBookmarkUseCase(article) }
    }
}

data class FeedContent(
    val articles: List<Article>,
    val onBookmarkClicked: (Article) -> Unit,
    val isPullRefreshing: Boolean,
    val onPullRefresh: () -> Unit,
)
