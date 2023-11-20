package com.vandele.classicalmusicnews.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.ui.rememberTextHeight
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing
import com.vandele.classicalmusicnews.ui.toMediumString

@Composable
fun ArticleItem(article: Article, onClick: () -> Unit, onBookmarkClicked: () -> Unit) {
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
fun ArticleItemPlaceholder() {
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
        article.rssSource?.let {
            Text(
                text = stringResource(article.rssSource.nameRes),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
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
