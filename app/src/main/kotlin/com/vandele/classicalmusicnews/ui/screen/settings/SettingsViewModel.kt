package com.vandele.classicalmusicnews.ui.screen.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.CmnSettings
import com.vandele.classicalmusicnews.model.DarkModePreference
import com.vandele.classicalmusicnews.model.RssSource
import com.vandele.classicalmusicnews.usecase.GetSettingsUseCase
import com.vandele.classicalmusicnews.usecase.SetDarkModePreferenceUseCase
import com.vandele.classicalmusicnews.usecase.ToggleRssSourceUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val setDarkModePreferenceUseCase: SetDarkModePreferenceUseCase,
    private val toggleRssSourceUseCase: ToggleRssSourceUseCase,
    getSettingsUseCase: GetSettingsUseCase,
) : ViewModel() {
    val settings: Flow<CmnSettings> = getSettingsUseCase()

    fun setDarkModePreference(darkModePreference: DarkModePreference) {
        viewModelScope.launch { setDarkModePreferenceUseCase(darkModePreference) }
    }

    fun toggleRssSource(rssSource: RssSource) {
        viewModelScope.launch { toggleRssSourceUseCase(rssSource) }
    }
}
