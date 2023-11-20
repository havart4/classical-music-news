package com.vandele.classicalmusicnews.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.vandele.classicalmusicnews.data.local.database.dao.ArticlesDao
import com.vandele.classicalmusicnews.data.local.database.entity.ArticleEntity

@Database(
    version = 1,
    entities = [ArticleEntity::class],
)
abstract class CmnDatabase : RoomDatabase() {
    protected abstract val articlesDao: ArticlesDao

    fun getAllArticles() = articlesDao.getAllArticles()
}
