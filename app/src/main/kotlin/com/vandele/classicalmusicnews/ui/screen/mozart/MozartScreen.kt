package com.vandele.classicalmusicnews.ui.screen.mozart

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.vandele.classicalmusicnews.ui.components.CmnTopAppBar
import com.vandele.classicalmusicnews.ui.components.ErrorMessage
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing

@Composable
fun MozartScreen(contentPadding: PaddingValues, navigateUp: () -> Unit) {
    val viewModel = hiltViewModel<MozartViewModel>()
    val article by viewModel.article.collectAsState(null)
    val state = viewModel.mozartScreenState.collectAsState(MozartScreenState.Loading).value
    Column(Modifier.fillMaxSize()) {
        CmnTopAppBar(
            onBackButtonClicked = navigateUp,
            title = article?.title ?: "",
        )
        Box(
            Modifier
                .weight(1f)
                .padding(bottom = contentPadding.calculateBottomPadding())
                .padding(CmnSpacing.m),
        ) {
            when (state) {
                is MozartScreenState.Loading -> {
                    MozartScreenLoading()
                }
                is MozartScreenState.Error -> {
                    ErrorMessage(state.retryableError, Modifier.fillMaxSize())
                }
                is MozartScreenState.Content -> {
                    MozartScreenContent(state)
                }
            }
        }
    }
}

@Composable
private fun MozartScreenLoading() {
    Text("Loading")
}

@Composable
private fun MozartScreenContent(state: MozartScreenState.Content) {
    Text(state.mozartOpinion.message)
}
