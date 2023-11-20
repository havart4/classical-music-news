package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.DarkModePreference
import com.vandele.classicalmusicnews.repository.SettingsRepository
import javax.inject.Inject

class SetDarkModePreferenceUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(darkModePreference: DarkModePreference) =
        settingsRepository.setDarkModePreference(darkModePreference)
}
