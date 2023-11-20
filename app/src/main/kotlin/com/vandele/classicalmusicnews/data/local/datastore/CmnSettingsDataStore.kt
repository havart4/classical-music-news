package com.vandele.classicalmusicnews.data.local.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CmnSettingsDataStore @Inject constructor(application: Application) {
    private val dataStore = application.cmnSettingsDataStore
    private val darkModeKey = stringPreferencesKey("darkModePreferenceKey")
    private val disabledRssSourceIdsKey =
        stringSetPreferencesKey("disabledRssSourceIdsPreferenceKey")

    fun getDarkModePreference(): Flow<String?> = dataStore.data.map { it[darkModeKey] }

    suspend fun setDarkModePreference(darkModePreference: String) {
        dataStore.edit { it[darkModeKey] = darkModePreference }
    }

    fun getDisabledRssSourceIds(): Flow<Set<String>?> =
        dataStore.data.map { it[disabledRssSourceIdsKey] }

    suspend fun toggleDisabledRssSourceId(rssSourceId: String) {
        dataStore.edit {
            it[disabledRssSourceIdsKey] =
                it[disabledRssSourceIdsKey]?.let { currentDisabledRssSourceIds ->
                    currentDisabledRssSourceIds.toMutableSet().apply {
                        if (rssSourceId in currentDisabledRssSourceIds) {
                            remove(rssSourceId)
                        } else {
                            add(rssSourceId)
                        }
                    }
                } ?: setOf(rssSourceId)
        }
    }
}

private val Context.cmnSettingsDataStore by preferencesDataStore(name = "cmnSettingsDataStore")
