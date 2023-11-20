package com.vandele.classicalmusicnews.ui.screen.bookmarks

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.vandele.classicalmusicnews.R
import com.vandele.classicalmusicnews.ui.components.ArticleItem
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing

@Composable
fun BookmarksScreen(contentPadding: PaddingValues, navigateToDetail: (articleId: String) -> Unit) {
    val viewModel = hiltViewModel<BookmarksViewModel>()
    val articles = viewModel.bookmarkedArticles.collectAsState(initial = null).value
    if (articles.isNullOrEmpty()) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .padding(CmnSpacing.m),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                stringResource(R.string.bookmarked_articles_will_appear_here),
                textAlign = TextAlign.Center,
            )
        }
    } else {
        LazyColumn(Modifier.fillMaxSize(), contentPadding = contentPadding) {
            items(articles) { article ->
                ArticleItem(
                    article = article,
                    onClick = { navigateToDetail(article.id) },
                    onBookmarkClicked = { viewModel.onBookmarkClicked(article) },
                )
            }
        }
    }
}
