@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)

package com.vandele.classicalmusicnews

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun CmnApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            val currentBackStackEntry = navController.currentBackStackEntryAsState().value
            if (currentBackStackEntry == null || currentBackStackEntry.isTopLevelDestination()) {
                CmnNavigationBar(
                    selectedNavigationBarItem = currentBackStackEntry?.toNavigationBarItem(),
                    onNavigationBarItemSelected = { navigationBarItem ->
                        navController.navigate(navigationBarItem)
                    },
                )
            }
        },
    ) { contentPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(contentPadding)
                .consumeWindowInsets(contentPadding)
                .windowInsetsPadding(WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal)),
        ) {
            CmnNavHost(navController)
        }
    }
}

@Composable
private fun CmnNavigationBar(
    selectedNavigationBarItem: CmnNavigationBarItem?,
    onNavigationBarItemSelected: (CmnNavigationBarItem) -> Unit,
) {
    NavigationBar {
        CmnNavigationBarItem.values().forEach { bottomNavigationItem ->
            val label = stringResource(bottomNavigationItem.labelRes)
            NavigationBarItem(
                selected = bottomNavigationItem == selectedNavigationBarItem,
                label = {
                    Text(label)
                },
                icon = {
                    Icon(
                        bottomNavigationItem.icon,
                        contentDescription = label,
                    )
                },
                onClick = { onNavigationBarItemSelected(bottomNavigationItem) }
            )
        }
    }
}

private fun NavHostController.navigate(navigationBarItem: CmnNavigationBarItem) {
    navigate(navigationBarItem.route) {
        // Pop up to start destination to avoid building up a large back stack
        // as users select navigation bar items
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        // Avoid multiple copies when selecting the same item
        launchSingleTop = true
        // Restore state when selecting a previously selected item
        restoreState = true
    }
}

private fun NavBackStackEntry.isTopLevelDestination() =
    CmnNavigationBarItem.values().map { it.route }.contains(destination.route)

private fun NavBackStackEntry.toNavigationBarItem() =
    CmnNavigationBarItem.values().first { it.route == destination.route }
