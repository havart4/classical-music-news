package com.vandele.classicalmusicnews.model

data class Article(
    val author: String,
    val feedTitle: String,
    val id: String,
    val image: String,
    val link: String,
    val pubDate: Long,
    val title: String,
)
