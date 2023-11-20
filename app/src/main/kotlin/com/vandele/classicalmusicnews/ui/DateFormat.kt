package com.vandele.classicalmusicnews.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun Instant.toMediumString(): String = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    .withLocale(LocalConfiguration.current.locales[0])
    .withZone(ZoneId.systemDefault())
    .format(this) ?: ""
