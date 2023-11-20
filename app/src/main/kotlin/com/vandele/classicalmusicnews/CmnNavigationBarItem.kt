package com.vandele.classicalmusicnews

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Segment
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class CmnNavigationBarItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val route: String,
) {
    object Feed : CmnNavigationBarItem(
        labelRes = R.string.feed,
        icon = Icons.Rounded.Segment,
        route = FEED_ROUTE,
    )

    object Bookmarks : CmnNavigationBarItem(
        labelRes = R.string.bookmarks,
        icon = Icons.Rounded.Bookmark,
        route = BOOKMARKS_ROUTE,
    )

    object Settings : CmnNavigationBarItem(
        labelRes = R.string.settings,
        icon = Icons.Rounded.Settings,
        route = SETTINGS_ROUTE,
    )

    companion object {
        fun values() = listOf(Feed, Bookmarks, Settings)
    }
}
