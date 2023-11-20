package com.vandele.classicalmusicnews.model

import java.time.Instant

data class MozartOpinion(
    val articleLink: String,
    val message: String,
    val createdAt: Instant,
)
