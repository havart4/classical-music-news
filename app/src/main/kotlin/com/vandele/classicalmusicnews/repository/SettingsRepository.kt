package com.vandele.classicalmusicnews.repository

import com.vandele.classicalmusicnews.data.local.datastore.CmnSettingsDataStore
import com.vandele.classicalmusicnews.model.DarkModePreference
import com.vandele.classicalmusicnews.model.RssSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SettingsRepository @Inject constructor(
    private val dataStore: CmnSettingsDataStore,
) {
    fun getDarkModePreference(): Flow<DarkModePreference> =
        dataStore.getDarkModePreference().map { darkModePreferenceString ->
            DarkModePreference.values().firstOrNull { it.name == darkModePreferenceString }
                ?: DarkModePreference.SYSTEM_DEFAULT
        }

    suspend fun setDarkModePreference(darkModePreference: DarkModePreference) {
        dataStore.setDarkModePreference(darkModePreference.name)
    }

    fun getDisabledRssSources() = dataStore.getDisabledRssSourceIds().map { disabledRssSourceIds ->
        disabledRssSourceIds?.mapNotNull { disabledRssSourceId ->
            RssSource.values().firstOrNull { rssSource -> rssSource.id == disabledRssSourceId }
        }?.toSet() ?: emptySet()
    }

    suspend fun toggleRssSource(rssSource: RssSource) {
        dataStore.toggleDisabledRssSourceId(rssSource.id)
    }
}
