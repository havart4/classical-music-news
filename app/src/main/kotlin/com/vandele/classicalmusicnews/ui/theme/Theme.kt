package com.vandele.classicalmusicnews.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun CmnTheme(
    isDark: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = getColorScheme(isDark = isDark),
        typography = Typography,
    ) {
        content()
    }
}
