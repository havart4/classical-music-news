package com.vandele.classicalmusicnews.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.vandele.classicalmusicnews.R
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.model.RetryableError
import com.vandele.classicalmusicnews.ui.theme.CmnSpacing

@Composable
fun ErrorMessage(
    retryableError: RetryableError,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = retryableError.cmnError.getString(),
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(CmnSpacing.xs))
            Button(onClick = retryableError.onRetry) {
                Text(text = stringResource(R.string.try_again))
            }
        }
    }
}

@Composable
private fun CmnError.getString(): String = when (this) {
    is CmnError.ClientError -> genericErrorMessage()
    is CmnError.ServerError -> genericErrorMessage()
    is CmnError.ConnectionError -> stringResource(R.string.connection_error)
    is CmnError.TimeoutError -> stringResource(R.string.timeout_error)
    is CmnError.Unknown -> genericErrorMessage()
    CmnError.ParseError -> stringResource(R.string.parse_error)
}

@Composable
private fun genericErrorMessage() = stringResource(R.string.generic_error_message)
