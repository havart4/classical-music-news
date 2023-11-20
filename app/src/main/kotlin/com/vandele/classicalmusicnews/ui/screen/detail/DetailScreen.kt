@file:OptIn(ExperimentalMaterial3Api::class)

package com.vandele.classicalmusicnews.ui.screen.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import com.vandele.classicalmusicnews.R
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.ui.components.BookmarkButton

@Composable
fun DetailScreen(navigateBack: () -> Unit) {
    val viewModel = hiltViewModel<DetailViewModel>()
    val article by viewModel.article.collectAsState()
    val webViewState = rememberWebViewState(url = article?.link ?: "")
    // The reason we have a Scaffold here instead of putting the TopAppBar and the content in a
    // Column is that the WebView would make the TopAppBar invisible while it was loading.
    Scaffold(
        topBar = {
            DetailTopAppBar(
                onBackButtonClicked = navigateBack,
                article = article,
                onBookmarkClicked = viewModel::onBookmarkClicked,
                onMozartClicked = viewModel::onMozartClicked,
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

@Composable
private fun DetailTopAppBar(
    onBackButtonClicked: () -> Unit,
    article: Article?,
    onBookmarkClicked: () -> Unit,
    onMozartClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(article?.title ?: "", maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(onClick = onBackButtonClicked) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = stringResource(R.string.back),
                )
            }
        },
        actions = {
            BookmarkButton(
                onClick = onBookmarkClicked,
                isBookmarked = article?.isBookmarked == true,
            )
            IconButton(onClick = onMozartClicked) {
                Icon(
                    ImageVector.vectorResource(R.drawable.ic_mozart),
                    contentDescription = stringResource(R.string.mozart),
                )
            }
        }
    )
}