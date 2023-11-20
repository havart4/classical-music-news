package com.vandele.classicalmusicnews.repository

import com.vandele.classicalmusicnews.data.local.database.CmnDatabase
import com.vandele.classicalmusicnews.data.local.database.entity.ArticleEntity
import com.vandele.classicalmusicnews.data.local.database.entity.toArticle
import com.vandele.classicalmusicnews.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesRepository @Inject constructor(private val database: CmnDatabase) {
    fun getArticles(): Flow<List<Article>> =
        database.getAllArticles().map { articleEntities ->
            articleEntities.map(ArticleEntity::toArticle)
        }
}
