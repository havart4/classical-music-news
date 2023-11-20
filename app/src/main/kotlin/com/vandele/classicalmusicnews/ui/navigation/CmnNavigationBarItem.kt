package com.vandele.classicalmusicnews.ui.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Bookmark
import androidx.compose.material.icons.rounded.Segment
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.vandele.classicalmusicnews.R

sealed class CmnNavigationBarItem(
    @StringRes val labelRes: Int,
    val icon: ImageVector,
    val route: String,
) {
    data object Feed : CmnNavigationBarItem(
        labelRes = R.string.feed,
        icon = Icons.Rounded.Segment,
        route = FEED_ROUTE,
    )

    data object Bookmarks : CmnNavigationBarItem(
        labelRes = R.string.bookmarks,
        icon = Icons.Rounded.Bookmark,
        route = BOOKMARKS_ROUTE,
    )

    data object Settings : CmnNavigationBarItem(
        labelRes = R.string.settings,
        icon = Icons.Rounded.Settings,
        route = SETTINGS_ROUTE,
    )

    companion object {
        fun values() = listOf(Feed, Bookmarks, Settings)
    }
}