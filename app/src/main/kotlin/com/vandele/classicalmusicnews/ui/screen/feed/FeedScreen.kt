@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import coil.compose.rememberAsyncImagePainter
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.UiState
import com.vandele.classicalmusicnews.ui.components.BookmarkButton
import com.vandele.classicalmusicnews.ui.components.ErrorMessage
import com.vandele.classicalmusicnews.ui.components.PlaceholderBox
import com.vandele.classicalmusicnews.ui.rememberTextHeight
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing
import com.vandele.classicalmusicnews.ui.toMediumString
import eu.bambooapps.material3.pullrefresh.PullRefreshIndicator
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
    val pullRefreshState = rememberPullRefreshState(
        refreshing = content.isPullRefreshing,
        onRefresh = content.onPullRefresh,
    )
    Box {
        LazyColumn(
            Modifier
                .fillMaxWidth()
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
private fun ArticleItem(article: Article, onClick: () -> Unit, onBookmarkClicked: () -> Unit) {
    Surface(
        Modifier
            .clickable(onClick = onClick)
            .padding(CmnSpacing.m)
    ) {
        Row(Modifier.fillMaxWidth()) {
            TextColumn(article, Modifier.weight(1f))
            Spacer(Modifier.width(CmnSpacing.s))
            Column(horizontalAlignment = Alignment.End) {
                article.image?.let { imageUrl ->
                    Image(
                        painter = rememberAsyncImagePainter(imageUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(120.dp, 80.dp)
                            .clip(MaterialTheme.shapes.small),
                        contentScale = ContentScale.Crop,
                    )
                }
                BookmarkButton(onClick = onBookmarkClicked, isBookmarked = article.isBookmarked)
            }
        }
    }
}

@Composable
private fun ArticleItemPlaceholder() {
    Surface(Modifier.padding(CmnSpacing.m)) {
        Row(Modifier.fillMaxWidth()) {
            TextColumnPlaceholder(Modifier.weight(1f))
            Spacer(Modifier.width(CmnSpacing.m))
            PlaceholderBox(Modifier.size(120.dp, 80.dp), shape = MaterialTheme.shapes.small)
        }
    }
}

@Composable
private fun TextColumn(article: Article, modifier: Modifier = Modifier) {
    Column(modifier) {
        Text(
            text = article.title ?: "",
            style = MaterialTheme.typography.titleMedium,
            overflow = TextOverflow.Ellipsis,
            maxLines = 3,
        )
        Text(
            text = article.channelTitle ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = article.pubDate?.toMediumString() ?: "",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun TextColumnPlaceholder(modifier: Modifier = Modifier) {
    Column(modifier) {
        val mediumTextHeight = rememberTextHeight(MaterialTheme.typography.titleMedium)
        repeat(3) {
            PlaceholderBox(
                Modifier
                    .fillMaxWidth()
                    .height(
                        mediumTextHeight
                            .minus(CmnSpacing.xxs)
                            .coerceAtLeast(0.dp)
                    )
            )
            Spacer(Modifier.height(CmnSpacing.xxs))
        }
        val smallTextHeight = rememberTextHeight(MaterialTheme.typography.bodySmall)
        repeat(2) {
            PlaceholderBox(
                Modifier
                    .fillMaxWidth(0.5f)
                    .height(
                        smallTextHeight
                            .minus(CmnSpacing.xxs)
                            .coerceAtLeast(0.dp)
                    )
            )
            Spacer(Modifier.height(CmnSpacing.xxs))
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
