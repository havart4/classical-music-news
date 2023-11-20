package com.vandele.classicalmusicnews.data.local.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.vandele.classicalmusicnews.data.local.database.entity.ARTICLE_TABLE_NAME
import com.vandele.classicalmusicnews.data.local.database.entity.ArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticlesDao {
    @Query("SELECT * FROM $ARTICLE_TABLE_NAME WHERE id = :articleId")
    fun getArticle(articleId: String): Flow<ArticleEntity?>

    @Query("SELECT * FROM $ARTICLE_TABLE_NAME ORDER by pubDate DESC")
    fun getAllArticles(): Flow<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<ArticleEntity>)

    @Query("DELETE FROM $ARTICLE_TABLE_NAME WHERE id = :articleId")
    suspend fun deleteArticle(articleId: String)

    @Update
    suspend fun updateArticle(article: ArticleEntity)
}
