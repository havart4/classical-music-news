package com.vandele.classicalmusicnews.repository

import arrow.core.Either
import com.vandele.classicalmusicnews.data.local.database.CmnDatabase
import com.vandele.classicalmusicnews.data.local.database.entity.toMozartOpinion
import com.vandele.classicalmusicnews.data.local.database.entity.toMozartOpinionEntity
import com.vandele.classicalmusicnews.data.remote.MozartOpinionApi
import com.vandele.classicalmusicnews.data.remote.dto.MozartOpinionDto
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.model.MozartOpinion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import javax.inject.Inject

class MozartOpinionRepository @Inject constructor(
    private val database: CmnDatabase,
    private val mozartOpinionApi: MozartOpinionApi,
) {
    fun getMozartOpinionLocal(articleLink: String): Flow<MozartOpinion?> =
        database.getMozartOpinion(articleLink = articleLink).map { it?.toMozartOpinion() }

    suspend fun insertMozartOpinionLocal(mozartOpinion: MozartOpinion) =
        database.insertMozartOpinion(mozartOpinion.toMozartOpinionEntity())

    suspend fun deleteMozartOpinionLocal(mozartOpinion: MozartOpinion) =
        database.deleteMozartOpinion(articleLink = mozartOpinion.articleLink)

    suspend fun getMozartOpinionRemote(articleLink: String): Either<CmnError, MozartOpinion> {
        return mozartOpinionApi.getMozartOpinion(articleLink = articleLink)
            .map { it.toMozartOpinion(articleLink = articleLink) }
    }
}

private fun MozartOpinionDto.toMozartOpinion(articleLink: String) = MozartOpinion(
    articleLink = articleLink,
    message = message,
    createdAt = Instant.now(),
)
