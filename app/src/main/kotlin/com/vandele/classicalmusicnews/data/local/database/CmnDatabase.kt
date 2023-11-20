package com.vandele.classicalmusicnews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.vandele.classicalmusicnews.data.local.database.dao.ArticleDao
import com.vandele.classicalmusicnews.data.local.database.dao.MozartOpinionDao
import com.vandele.classicalmusicnews.data.local.database.entity.ArticleEntity
import com.vandele.classicalmusicnews.data.local.database.entity.MozartOpinionEntity

@Database(
    version = 1,
    entities = [ArticleEntity::class, MozartOpinionEntity::class],
)
@TypeConverters(Converters::class)
abstract class CmnDatabase : RoomDatabase() {
    protected abstract val articleDao: ArticleDao
    protected abstract val mozartOpinionDao: MozartOpinionDao

    fun getAllArticles() = articleDao.getAllArticles()

    fun getArticle(articleId: String) = articleDao.getArticle(articleId = articleId)

    suspend fun insertArticles(articles: List<ArticleEntity>) = articleDao.insertArticles(articles)

    suspend fun deleteArticles(articleIds: List<String>) = articleIds.forEach { articleId ->
        articleDao.deleteArticle(articleId = articleId)
    }

    suspend fun updateArticle(article: ArticleEntity) = articleDao.updateArticle(article)

    fun getMozartOpinion(articleLink: String) =
        mozartOpinionDao.getMozartOpinion(articleLink = articleLink)

    suspend fun insertMozartOpinion(mozartOpinion: MozartOpinionEntity) =
        mozartOpinionDao.insertMozartOpinion(mozartOpinion)

    suspend fun deleteMozartOpinion(articleLink: String) =
        mozartOpinionDao.deleteMozartOpinion(articleLink = articleLink)
}
