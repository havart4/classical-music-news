package com.vandele.classicalmusicnews.model

data class CmnSettings(
    val darkModePreference: DarkModePreference = DarkModePreference.SYSTEM_DEFAULT,
    val disabledRssSources: Set<RssSource> = emptySet(),
)

enum class DarkModePreference {
    SYSTEM_DEFAULT, LIGHT, DARK
}
