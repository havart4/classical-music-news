package com.vandele.classicalmusicnews.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.ui.navigation.ARTICLE_ID_ARG
import com.vandele.classicalmusicnews.usecase.GetArticleUseCase
import com.vandele.classicalmusicnews.usecase.ToggleArticleBookmarkUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    private val toggleArticleBookmarkUseCase: ToggleArticleBookmarkUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val article: Flow<Article?> = getArticleUseCase(savedStateHandle[ARTICLE_ID_ARG] ?: "")

    fun onBookmarkClicked(article: Article) {
        viewModelScope.launch { toggleArticleBookmarkUseCase.invoke(article) }
    }

    fun onMozartClicked() {
        // TODO
    }
}
