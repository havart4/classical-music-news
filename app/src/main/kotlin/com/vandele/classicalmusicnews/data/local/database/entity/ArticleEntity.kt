package com.vandele.classicalmusicnews.data.local.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.RssSource
import java.time.Instant

@Entity(tableName = ARTICLE_TABLE_NAME)
data class ArticleEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,

    val author: String?,
    val image: String?,
    val link: String?,
    val pubDate: Instant?,
    val title: String?,
    val rssSourceId: String?,
    val isBookmarked: Boolean,
)

const val ARTICLE_TABLE_NAME = "Article"

fun Article.toArticleEntity() = ArticleEntity(
    id = id,
    author = author,
    image = image,
    link = link,
    pubDate = pubDate,
    title = title,
    rssSourceId = rssSource?.id,
    isBookmarked = isBookmarked,
)

fun ArticleEntity.toArticle() = Article(
    author = author,
    id = id,
    image = image,
    link = link,
    pubDate = pubDate,
    title = title,
    rssSource = rssSourceId?.let { RssSource.fromId(it) },
    isBookmarked = isBookmarked,
)
