package com.vandele.classicalmusicnews.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.vandele.classicalmusicnews.R
import com.vandele.classicalmusicnews.ui.components.BookmarkButton
import com.vandele.classicalmusicnews.ui.components.CmnTopAppBar

@Composable
fun DetailScreen(navigateUp: () -> Unit, navigateToMozart: (articleId: String) -> Unit) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val article by viewModel.article.collectAsState(null)
    val webViewState = rememberWebViewState(url = article?.link ?: "")
    // The reason we have a Scaffold here instead of putting the TopAppBar and the content in a
    // Column is that the WebView made the TopAppBar invisible while it was loading.
    Scaffold(
        topBar = {
            CmnTopAppBar(
                onBackButtonClicked = navigateUp,
                title = article?.title ?: "",
                actions = {
                    BookmarkButton(
                        onClick = {
                            article?.let { viewModel.onBookmarkClicked(it) }
                        },
                        isBookmarked = article?.isBookmarked == true,
                    )
                    IconButton(
                        onClick = {
                            article?.id?.let { articleId ->
                                navigateToMozart(articleId)
                            }
                        },
                    ) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.ic_mozart),
                            contentDescription = stringResource(R.string.mozart),
                        )
                    }
                },
            )
        },
    ) { contentPadding ->
        Box(Modifier.fillMaxSize()) {
            WebView(
                webViewState,
                Modifier
                    .padding(contentPadding)
            )
        }
    }
}
