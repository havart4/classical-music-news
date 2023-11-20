package com.vandele.classicalmusicnews.ui.screen.detail

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun DetailScreen() {
    val viewModel = hiltViewModel<DetailViewModel>()
    LazyColumn(Modifier.fillMaxWidth()) {
        repeat(50) {
            item {
                Text("Detail")
            }
        }
    }
}
