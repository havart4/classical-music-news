package com.vandele.classicalmusicnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.vandele.classicalmusicnews.ui.CmnApp
import com.vandele.classicalmusicnews.ui.theme.CmnTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        setContent {
            CmnTheme {
                CmnApp()
            }
        }
    }
}
