package com.vandele.classicalmusicnews.model

import java.time.Instant

data class Article(
    val author: String?,
    val id: String,
    val image: String?,
    val link: String?,
    val pubDate: Instant?,
    val title: String?,
    val rssSource: RssSource?,
    val isBookmarked: Boolean,
)
