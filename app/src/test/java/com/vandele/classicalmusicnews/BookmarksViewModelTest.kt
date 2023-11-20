package com.vandele.classicalmusicnews

import app.cash.turbine.test
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.ui.screen.bookmarks.BookmarksViewModel
import com.vandele.classicalmusicnews.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class BookmarksViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `Should emit articles from the bookmarked article use case`() = runTest {
        val bookmarkedArticlesFlow = MutableSharedFlow<List<Article>>()
        val viewModel = BookmarksViewModel(
            toggleArticleBookmarkUseCase = mockk {
                coEvery { this@mockk(any()) } just runs
            },
            getBookmarkedArticlesUseCase = mockk {
                every {
                    this@mockk()
                } returns bookmarkedArticlesFlow
            },
        )
        viewModel.bookmarkedArticles.test {
            bookmarkedArticlesFlow.emit(emptyList())
            assertEquals(emptyList<Article>(), awaitItem())
            val articles = listOf(Article.getPreview())
            bookmarkedArticlesFlow.emit(articles)
            assertEquals(articles, awaitItem())
        }
    }
}
