package com.vandele.classicalmusicnews.ui.screen.mozart

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.vandele.classicalmusicnews.R
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
        Box(Modifier.weight(1f)) {
            when (state) {
                is MozartScreenState.Loading -> {
                    MozartScreenLoading(contentPadding)
                }
                is MozartScreenState.Error -> {
                    ErrorMessage(
                        state.retryableError,
                        Modifier
                            .fillMaxSize()
                            .padding(bottom = contentPadding.calculateBottomPadding())
                            .padding(CmnSpacing.m),
                    )
                }
                is MozartScreenState.Content -> {
                    MozartScreenContent(state, contentPadding)
                }
            }
        }
    }
}

@Composable
private fun MozartScreenLoading(contentPadding: PaddingValues) {
    val infiniteTransition = rememberInfiniteTransition(label = "mozart-image-transition")
    val scale by infiniteTransition.animateFloat(
        initialValue = 0.9f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 600),
            repeatMode = RepeatMode.Reverse,
        ),
        label = "mozart-image-scale",
    )
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = contentPadding.calculateBottomPadding())
            .padding(CmnSpacing.m),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Icon(
            ImageVector.vectorResource(R.drawable.ic_mozart),
            contentDescription = "Mozart",
            modifier = Modifier
                .size(200.dp)
                .scale(scale),
            tint = LocalContentColor.current,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = stringResource(R.string.asking_mozart),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
private fun MozartScreenContent(state: MozartScreenState.Content, contentPadding: PaddingValues) {
    Column(
        Modifier
            .padding(horizontal = CmnSpacing.m)
            .verticalScroll(rememberScrollState()),
    ) {
        Spacer(Modifier.height(CmnSpacing.m))
        Text(state.mozartOpinion.message)
        Spacer(Modifier.height(CmnSpacing.m))
        Spacer(Modifier.height(contentPadding.calculateBottomPadding()))
    }
}
