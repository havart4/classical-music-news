package com.vandele.classicalmusicnews.repository

import android.util.Log
import arrow.core.Either
import com.vandele.classicalmusicnews.data.local.database.CmnDatabase
import com.vandele.classicalmusicnews.data.local.database.entity.toArticle
import com.vandele.classicalmusicnews.data.remote.ArticlesApi
import com.vandele.classicalmusicnews.data.remote.RemoteError
import com.vandele.classicalmusicnews.model.Article
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ArticlesRepository @Inject constructor(
    private val database: CmnDatabase,
    private val articlesApi: ArticlesApi,
) {
    fun getArticles(): Flow<List<Article>> = database.getAllArticles().map { articleEntities ->
        articleEntities.map { it.toArticle() }
    }

    suspend fun refreshArticles(): Either<RemoteError, Unit> = articlesApi.getArticles(
        "https://slippedisc.com/feed/"
    ).map {
        val x = it
        Log.d("x", x.toString())
        // TODO
    }
}
