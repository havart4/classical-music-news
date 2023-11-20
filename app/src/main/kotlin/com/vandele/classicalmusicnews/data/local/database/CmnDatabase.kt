package com.vandele.classicalmusicnews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vandele.classicalmusicnews.data.local.database.dao.ArticlesDao
import com.vandele.classicalmusicnews.data.local.database.entity.ArticleEntity

@Database(
    version = 1,
    entities = [ArticleEntity::class],
)
@TypeConverters(Converters::class)
abstract class CmnDatabase : RoomDatabase() {
    protected abstract val articlesDao: ArticlesDao

    fun getAllArticles() = articlesDao.getAllArticles()

    fun getArticle(articleId: String) = articlesDao.getArticle(articleId)

    suspend fun insertArticles(articles: List<ArticleEntity>) = articlesDao.insertArticles(articles)

    suspend fun deleteArticles(articleIds: List<String>) = articleIds.forEach { articleId ->
        articlesDao.deleteArticle(articleId)
    }

    suspend fun updateArticle(article: ArticleEntity) = articlesDao.updateArticle(article)
}
