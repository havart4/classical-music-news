package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.CmnSettings
import com.vandele.classicalmusicnews.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import javax.inject.Inject

class GetSettingsUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    operator fun invoke(): Flow<CmnSettings> = combine(
        settingsRepository.getDarkModePreference(),
        settingsRepository.getDisabledRssSources(),
    ) { darkModePreference, rssSources ->
        CmnSettings(darkModePreference, rssSources)
    }
}
