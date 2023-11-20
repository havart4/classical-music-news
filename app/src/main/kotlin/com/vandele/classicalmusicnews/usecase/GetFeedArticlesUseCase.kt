package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class GetFeedArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
    private val getSettingsUseCase: GetSettingsUseCase,
) {
    operator fun invoke(): Flow<List<Article>> = combine(
        articlesRepository.getArticlesLocal(),
        getSettingsUseCase(),
    ) { articles, settings ->
        articles.filter { it.rssSource !in settings.disabledRssSources }
    }
}
