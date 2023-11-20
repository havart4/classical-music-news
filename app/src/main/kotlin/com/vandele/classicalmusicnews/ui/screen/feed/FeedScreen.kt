@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import com.vandele.classicalmusicnews.model.UiState
import com.vandele.classicalmusicnews.ui.components.ArticleItem
import com.vandele.classicalmusicnews.ui.components.ArticleItemPlaceholder
import com.vandele.classicalmusicnews.ui.components.ErrorMessage
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing
import eu.bambooapps.material3.pullrefresh.PullRefreshDefaults
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicatorDefaults
import eu.bambooapps.material3.pullrefresh.pullRefresh
import eu.bambooapps.material3.pullrefresh.rememberPullRefreshState

@Composable
fun FeedScreen(contentPadding: PaddingValues, navigateToDetail: (id: String) -> Unit) {
    val viewModel = hiltViewModel<FeedViewModel>()
    ResumeObserver {
        viewModel.onResume()
    }
    when (
        val state = viewModel.feedState.collectAsState(initial = UiState.Loading).value
    ) {
        is UiState.Loading -> {
            ArticlesPlaceholder(contentPadding = contentPadding)
        }
        is UiState.Error -> {
            ErrorMessage(
                state.error,
                Modifier
                    .padding(contentPadding)
                    .padding(CmnSpacing.m)
                    .fillMaxSize(),
            )
        }
        is UiState.Content -> {
            ArticlesContent(
                state.value,
                contentPadding = contentPadding,
                navigateToDetail = navigateToDetail
            )
        }
    }
}

@Composable
private fun ArticlesContent(
    content: FeedContent,
    contentPadding: PaddingValues,
    navigateToDetail: (id: String) -> Unit,
) {
    val topPadding = contentPadding.calculateTopPadding()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = content.isPullRefreshing,
        onRefresh = content.onPullRefresh,
        refreshThreshold = PullRefreshDefaults.RefreshThreshold + topPadding,
        refreshingOffset = PullRefreshDefaults.RefreshingOffset + topPadding,
    )
    Box {
        LazyColumn(
            Modifier
                .fillMaxSize()
                .pullRefresh(pullRefreshState),
            contentPadding = contentPadding,
        ) {
            items(content.articles) { article ->
                ArticleItem(
                    article,
                    onClick = { navigateToDetail(article.id) },
                    onBookmarkClicked = { content.onBookmarkClicked(article) },
                )
            }
        }
        PullRefreshIndicator(
            refreshing = content.isPullRefreshing,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            colors = PullRefreshIndicatorDefaults.colors(
                contentColor = MaterialTheme.colorScheme.primary,
            ),
        )
    }
}

@Composable
private fun ArticlesPlaceholder(contentPadding: PaddingValues) {
    LazyColumn(Modifier.fillMaxWidth(), contentPadding = contentPadding) {
        items(10) {
            ArticleItemPlaceholder()
        }
    }
}

@Composable
private fun ResumeObserver(onResume: () -> Unit) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val lifecycleEventObserver = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                onResume()
            }
        }
        lifecycleOwner.lifecycle.addObserver(lifecycleEventObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleEventObserver)
        }
    }
}
