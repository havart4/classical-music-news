package com.vandele.classicalmusicnews.data.local.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.vandele.classicalmusicnews.data.local.database.entity.ARTICLE_TABLE_NAME
import com.vandele.classicalmusicnews.data.local.database.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM $ARTICLE_TABLE_NAME ORDER by pubDate DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Delete
    suspend fun deleteArticles(articles: List<ArticleEntity>)
}
