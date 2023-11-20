package com.vandele.classicalmusicnews

import app.cash.turbine.test
import arrow.core.left
import arrow.core.right
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.CmnError
import com.vandele.classicalmusicnews.model.UiState
import com.vandele.classicalmusicnews.ui.screen.feed.FeedViewModel
import com.vandele.classicalmusicnews.util.MainDispatcherRule
import io.mockk.coEvery
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class FeedViewModelTest {
    @get:Rule
    val dispatcherRule = MainDispatcherRule()

    @Test
    fun `Should initially be loading`() = runTest {
        val feedArticlesFlow = MutableSharedFlow<List<Article>>()
        val viewModel = FeedViewModel(
            refreshArticlesUseCase = mockk {
                coEvery { this@mockk() } returns Unit.right()
            },
            toggleArticleBookmarkUseCase = mockk {
                coEvery { this@mockk(any()) } just runs
            },
            getFeedArticlesUseCase = mockk {
                coEvery { this@mockk() } returns feedArticlesFlow
            },
        )
        viewModel.feedState.test {
            feedArticlesFlow.emit(emptyList())
            assertEquals(UiState.Loading, awaitItem())
        }
    }

    @Test
    fun `Refresh failure and empty articles should give error state`() = runTest {
        val feedArticlesFlow = MutableSharedFlow<List<Article>>()
        val viewModel = FeedViewModel(
            refreshArticlesUseCase = mockk {
                coEvery { this@mockk() } returns CmnError.ConnectionError.left()
            },
            toggleArticleBookmarkUseCase = mockk {
                coEvery { this@mockk(any()) } just runs
            },
            getFeedArticlesUseCase = mockk {
                coEvery { this@mockk() } returns feedArticlesFlow
            },
        )
        viewModel.feedState.test {
            feedArticlesFlow.emit(emptyList())
            awaitItem()  // Await loading state
            viewModel.onResume()
            val item = awaitItem()
            assertTrue(item is UiState.Error)
            assertTrue((item as UiState.Error).error.cmnError is CmnError.ConnectionError)
        }
    }

    @Test
    fun `Refresh failure and nonempty articles should give content state`() = runTest {
        val feedArticlesFlow = MutableSharedFlow<List<Article>>()
        val viewModel = FeedViewModel(
            refreshArticlesUseCase = mockk {
                coEvery { this@mockk() } returns CmnError.ConnectionError.left()
            },
            toggleArticleBookmarkUseCase = mockk {
                coEvery { this@mockk(any()) } just runs
            },
            getFeedArticlesUseCase = mockk {
                coEvery { this@mockk() } returns feedArticlesFlow
            },
        )
        val articles = listOf(Article.getPreview())
        viewModel.feedState.test {
            feedArticlesFlow.emit(articles)
            val item = awaitItem()
            assertTrue(item is UiState.Content)
            assertTrue((item as UiState.Content).value.articles == articles)
        }
    }
}
