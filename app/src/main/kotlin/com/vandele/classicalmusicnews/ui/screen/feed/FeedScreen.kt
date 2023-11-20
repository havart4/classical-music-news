package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing
import com.vandele.classicalmusicnews.ui.toMediumString

@Composable
fun FeedScreen(navigateToDetail: (id: String) -> Unit) {
    val viewModel = hiltViewModel<FeedViewModel>()
    ResumeObserver {
        viewModel.fetchArticles()
    }
    val articles by viewModel.articles.collectAsState(initial = emptyList())
    LazyColumn(Modifier.fillMaxWidth()) {
        items(articles) { article ->
            ArticleItem(article, navigateToDetail)
        }
    }
}

@Composable
fun ArticleItem(article: Article, navigateToDetail: (id: String) -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { navigateToDetail(article.id) }
            .padding(CmnSpacing.m)
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(article, Modifier.weight(1f))
            article.image?.let { imageUrl ->
                Spacer(Modifier.width(CmnSpacing.m))
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = null,
                    modifier = Modifier
                        .size(120.dp, 80.dp)
                        .clip(MaterialTheme.shapes.small),
                    contentScale = ContentScale.Crop,
                )
            }
        }
    }
}

@Composable
private fun Text(article: Article, modifier: Modifier = Modifier) {
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
