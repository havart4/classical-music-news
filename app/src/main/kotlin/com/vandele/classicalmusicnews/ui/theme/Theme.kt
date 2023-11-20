package com.vandele.classicalmusicnews.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CmnTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = getColorScheme(),
        typography = Typography,
    ) {
        content()
    }
}
