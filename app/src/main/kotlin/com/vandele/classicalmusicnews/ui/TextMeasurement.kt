package com.vandele.classicalmusicnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.toSize

@Composable
fun rememberTextSize(
    text: String,
    style: TextStyle,
): DpSize {
    with(LocalDensity.current) {
        val textMeasurer = rememberTextMeasurer()
        return remember(style) {
            val measureResult = textMeasurer.measure(AnnotatedString(text), style)
            measureResult.size.toSize().toDpSize()
        }
    }
}

@Composable
fun rememberTextHeight(style: TextStyle): Dp {
    return rememberTextSize("", style).height
}
