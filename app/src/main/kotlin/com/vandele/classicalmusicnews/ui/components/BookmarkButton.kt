package com.vandele.classicalmusicnews.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.BookmarkBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.vandele.classicalmusicnews.R

@Composable
fun BookmarkButton(onClick: () -> Unit, isBookmarked: Boolean) {
    IconButton(onClick = onClick) {
        Icon(
            if (isBookmarked) {
                Icons.Rounded.Bookmark
            } else {
                Icons.Rounded.BookmarkBorder
            },
            contentDescription = stringResource(R.string.bookmark),
        )
    }
}