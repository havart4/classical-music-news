package com.vandele.classicalmusicnews.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.vandele.classicalmusicnews.ui.screen.bookmarks.BookmarksScreen
import com.vandele.classicalmusicnews.ui.screen.detail.DetailScreen
import com.vandele.classicalmusicnews.ui.screen.feed.FeedScreen
import com.vandele.classicalmusicnews.ui.screen.mozart.MozartScreen
import com.vandele.classicalmusicnews.ui.screen.settings.SettingsScreen
import java.net.URLEncoder

const val FEED_ROUTE = "feed"
const val BOOKMARKS_ROUTE = "bookmarks"
const val SETTINGS_ROUTE = "settings"

const val ARTICLE_ID_ARG = "articleId"

@Composable
fun CmnNavHost(
    navController: NavHostController,
    contentPadding: PaddingValues,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = FEED_ROUTE,
        modifier = modifier,
    ) {
        composable(route = FEED_ROUTE) {
            FeedScreen(
                contentPadding = contentPadding,
                navigateToDetail = navController::navigateToDetail,
            )
        }
        composable(route = BOOKMARKS_ROUTE) {
            BookmarksScreen(
                contentPadding = contentPadding,
                navigateToDetail = navController::navigateToDetail,
            )
        }
        composable(route = SETTINGS_ROUTE) {
            SettingsScreen(contentPadding = contentPadding)
        }
        composable(route = "detail/{$ARTICLE_ID_ARG}") {
            DetailScreen(
                navigateUp = navController::navigateUp,
                navigateToMozart = navController::navigateToMozart,
            )
        }
        composable(route = "mozart/{$ARTICLE_ID_ARG}") {
            MozartScreen(contentPadding = contentPadding, navigateUp = navController::navigateUp)
        }
    }
}

private fun NavHostController.navigateToDetail(articleId: String) {
    navigate(
        "detail/${encodeArgument(articleId)}"
    )
}

private fun NavHostController.navigateToMozart(articleId: String) {
    navigate(
        "mozart/${encodeArgument(articleId)}"
    )
}

private fun encodeArgument(argument: String) = URLEncoder.encode(argument, Charsets.UTF_8.name())
