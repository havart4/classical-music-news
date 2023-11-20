package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun FeedScreen(navigateToDetail: () -> Unit) {
    val viewModel = hiltViewModel<FeedViewModel>()
    LazyColumn(Modifier.fillMaxWidth()) {
        repeat(50) {
            item {
                Text("Feed", modifier = Modifier.clickable(onClick = navigateToDetail))
            }
        }
    }
}
