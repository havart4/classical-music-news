package com.vandele.classicalmusicnews.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.vandele.classicalmusicnews.ui.rememberTextSize
import io.github.fornewid.placeholder.foundation.PlaceholderHighlight
import io.github.fornewid.placeholder.foundation.placeholder
import io.github.fornewid.placeholder.foundation.shimmer

@Composable
fun PlaceholderText(
    text: String,
    modifier: Modifier = Modifier,
    style: TextStyle = LocalTextStyle.current,
) {
    Box(
        modifier = modifier
            .size(rememberTextSize(text, style))
            .placeholder(
                visible = true,
                color = placeholderColor(),
                shape = RoundedCornerShape(4.dp),
                highlight = placeholderHighlight(),
            ),
    )
}

@Composable
fun PlaceholderBox(modifier: Modifier = Modifier, shape: Shape = RectangleShape) {
    Box(
        modifier.placeholder(
            visible = true,
            color = placeholderColor(),
            shape = shape,
            highlight = placeholderHighlight()
        )
    )
}

@Composable
private fun placeholderColor() = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    .compositeOver(MaterialTheme.colorScheme.surface)

@Composable
private fun placeholderHighlight() = PlaceholderHighlight.shimmer(
    MaterialTheme.colorScheme.surface.copy(alpha = 0.75f)
)
