package com.vandele.classicalmusicnews.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vandele.classicalmusicnews.model.Article

@Entity(tableName = ARTICLE_TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val author: String,
    val feedTitle: String,
    val image: String,
    val link: String,
    val pubDate: Long,
    val title: String,
)

const val ARTICLE_TABLE_NAME = "Article"

fun ArticleEntity.toArticle() = Article(
    author = author,
    feedTitle = feedTitle,
    id = id,
    image = image,
    link = link,
    pubDate = pubDate,
    title = title,
)
