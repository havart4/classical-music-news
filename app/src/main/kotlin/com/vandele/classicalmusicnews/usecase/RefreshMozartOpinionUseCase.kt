package com.vandele.classicalmusicnews.usecase

import arrow.core.Either
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.repository.MozartOpinionRepository
import javax.inject.Inject

class RefreshMozartOpinionUseCase @Inject constructor(
    private val mozartOpinionRepository: MozartOpinionRepository,
) {
    suspend operator fun invoke(articleLink: String): Either<CmnError, Unit> =
        mozartOpinionRepository.getMozartOpinionRemote(articleLink = articleLink).map {
            mozartOpinionRepository.insertMozartOpinionLocal(it)
        }
}
