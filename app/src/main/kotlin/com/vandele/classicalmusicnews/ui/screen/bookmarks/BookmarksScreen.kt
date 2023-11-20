package com.vandele.classicalmusicnews.ui.screen.bookmarks

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BookmarksScreen(contentPadding: PaddingValues) {
    LazyColumn(Modifier.fillMaxWidth()) {
        repeat(50) {
            item {
                Text("Bookmarks")
            }
        }
    }
}
