package com.vandele.classicalmusicnews.usecase

import com.vandele.classicalmusicnews.repository.MozartOpinionRepository
import javax.inject.Inject

class GetMozartOpinionUseCase @Inject constructor(
    private val mozartOpinionRepository: MozartOpinionRepository,
) {
    operator fun invoke(articleLink: String) =
        mozartOpinionRepository.getMozartOpinionLocal(articleLink = articleLink)
}
