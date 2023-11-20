package com.vandele.classicalmusicnews.ui.screen.settings

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsScreen() {
    LazyColumn(Modifier.fillMaxWidth()) {
        repeat(50) {
            item {
                Text("Settings")
            }
        }
    }
}
