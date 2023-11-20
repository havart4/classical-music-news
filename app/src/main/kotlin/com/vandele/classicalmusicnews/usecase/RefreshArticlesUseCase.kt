package com.vandele.classicalmusicnews.usecase

import arrow.core.Either
import com.vandele.classicalmusicnews.data.remote.RemoteError
import com.vandele.classicalmusicnews.repository.ArticlesRepository
import javax.inject.Inject

class RefreshArticlesUseCase @Inject constructor(
    private val articlesRepository: ArticlesRepository,
) {
    suspend operator fun invoke(): Either<RemoteError, Unit> = articlesRepository.refreshArticles()
}
