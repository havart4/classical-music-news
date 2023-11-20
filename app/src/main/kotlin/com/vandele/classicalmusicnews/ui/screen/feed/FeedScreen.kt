package com.vandele.classicalmusicnews.ui.screen.feed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver

@Composable
fun FeedScreen(navigateToDetail: () -> Unit) {
    val viewModel = hiltViewModel<FeedViewModel>()
    ResumeObserver {
        viewModel.fetchArticles()
    }
    LazyColumn(Modifier.fillMaxWidth()) {
        repeat(50) {
            item {
                Text("Feed", modifier = Modifier.clickable(onClick = navigateToDetail))
            }
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
