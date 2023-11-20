package com.vandele.classicalmusicnews.ui.screen.bookmarks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.usecase.GetBookmarkedArticlesUseCase
import com.vandele.classicalmusicnews.usecase.ToggleArticleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarksViewModel @Inject constructor(
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
    getBookmarkedArticlesUseCase: GetBookmarkedArticlesUseCase,
) : ViewModel() {
    val bookmarkedArticles: Flow<List<Article>> = getBookmarkedArticlesUseCase()

    fun onBookmarkClicked(article: Article) {
        viewModelScope.launch { toggleArticleBookmarkUseCase(article) }
    }
}
