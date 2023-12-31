package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.RefreshState
import com.vandele.classicalmusicnews.model.RetryableError
import com.vandele.classicalmusicnews.model.UiState
import com.vandele.classicalmusicnews.usecase.GetFeedArticlesUseCase
import com.vandele.classicalmusicnews.usecase.RefreshArticlesUseCase
import com.vandele.classicalmusicnews.usecase.ToggleArticleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val refreshArticlesUseCase: RefreshArticlesUseCase,
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
    getFeedArticlesUseCase: GetFeedArticlesUseCase,
) : ViewModel() {
    private val isPullRefreshing = MutableStateFlow(false)
    private val articlesRefreshState = MutableStateFlow<RefreshState>(RefreshState.Loading)
    private var lastResumeRefreshTime = Instant.MIN

    val feedState: Flow<UiState<FeedContent>> = combine(
        getFeedArticlesUseCase(),
        articlesRefreshState,
        isPullRefreshing,
    ) { articles, articlesRefreshState, isPullRefreshing ->
        combineToUiState(articles, articlesRefreshState, isPullRefreshing)
    }

    fun onResume() {
        if (Duration.between(lastResumeRefreshTime, Instant.now()).seconds >= 60) {
            refreshArticles()
            lastResumeRefreshTime = Instant.now()
        }
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
