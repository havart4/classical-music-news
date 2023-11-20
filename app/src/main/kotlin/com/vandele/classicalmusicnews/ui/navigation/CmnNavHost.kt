package com.vandele.classicalmusicnews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vandele.classicalmusicnews.ui.screen.bookmarks.BookmarksScreen
import com.vandele.classicalmusicnews.ui.screen.detail.DetailScreen
import com.vandele.classicalmusicnews.ui.screen.feed.FeedScreen
import com.vandele.classicalmusicnews.ui.screen.settings.SettingsScreen
import java.net.URLEncoder

const val FEED_ROUTE = "feed"
const val BOOKMARKS_ROUTE = "bookmarks"
const val SETTINGS_ROUTE = "settings"

const val ARTICLE_ID_ARG = "articleId"

@Composable
fun CmnNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = FEED_ROUTE,
    ) {
        composable(route = FEED_ROUTE) {
            FeedScreen(
                navigateToDetail = { articleId ->
                    navController.navigate(
                        "detail/${encodeArgument(articleId)}"
                    )
                }
            )
        }
        composable(route = "detail/{$ARTICLE_ID_ARG}") {
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

private fun encodeArgument(argument: String) = URLEncoder.encode(argument, Charsets.UTF_8.name())
