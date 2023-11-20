package com.vandele.classicalmusicnews.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vandele.classicalmusicnews.model.MozartOpinion
import java.time.Instant

@Entity(tableName = MOZART_OPINION_TABLE_NAME)
data class MozartOpinionEntity(
    @PrimaryKey(autoGenerate = false)
    val articleLink: String,

    val message: String,
    val createdAt: Instant,
)

const val MOZART_OPINION_TABLE_NAME = "MozartOpinion"

fun MozartOpinion.toMozartOpinionEntity() = MozartOpinionEntity(
    articleLink = articleLink,
    message = message,
    createdAt = createdAt,
)

fun MozartOpinionEntity.toMozartOpinion() = MozartOpinion(
    articleLink = articleLink,
    message = message,
    createdAt = createdAt,
)
