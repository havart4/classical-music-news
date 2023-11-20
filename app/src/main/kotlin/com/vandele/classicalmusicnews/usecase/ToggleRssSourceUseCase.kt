package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.RssSource
import com.vandele.classicalmusicnews.repository.SettingsRepository
import javax.inject.Inject

class ToggleRssSourceUseCase @Inject constructor(
    private val settingsRepository: SettingsRepository,
) {
    suspend operator fun invoke(rssSource: RssSource) =
        settingsRepository.toggleRssSource(rssSource)
}
