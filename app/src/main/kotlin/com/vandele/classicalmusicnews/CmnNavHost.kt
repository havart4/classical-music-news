package com.vandele.classicalmusicnews

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vandele.classicalmusicnews.ui.screen.bookmarks.BookmarksScreen
import com.vandele.classicalmusicnews.ui.screen.detail.DetailScreen
import com.vandele.classicalmusicnews.ui.screen.feed.FeedScreen
import com.vandele.classicalmusicnews.ui.screen.settings.SettingsScreen

@Composable
fun CmnNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = FEED_ROUTE,
    ) {
        composable(route = FEED_ROUTE) {
            FeedScreen(navigateToDetail = { navController.navigate(DETAIL_ROUTE) })
        }
        composable(route = DETAIL_ROUTE) {
            DetailScreen()
        }
        composable(route = BOOKMARKS_ROUTE) {
            BookmarksScreen()
        }
        composable(route = SETTINGS_ROUTE) {
            SettingsScreen()
        }
    }
}

const val FEED_ROUTE = "feed"
const val DETAIL_ROUTE = "detail"
const val BOOKMARKS_ROUTE = "bookmarks"
const val SETTINGS_ROUTE = "settings"
