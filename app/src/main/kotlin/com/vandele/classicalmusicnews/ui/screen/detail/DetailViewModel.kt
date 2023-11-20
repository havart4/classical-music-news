package com.vandele.classicalmusicnews.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.ui.navigation.ARTICLE_ID_ARG
import com.vandele.classicalmusicnews.usecase.GetArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getArticleUseCase: GetArticleUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _article = MutableStateFlow<Article?>(null)
    val article: StateFlow<Article?> = _article

    init {
        val articleId: String? = savedStateHandle[ARTICLE_ID_ARG]
        articleId?.let {
            loadArticle(it)
        }
    }

    fun onBookmarkClicked() {
        // TODO
    }

    fun onMozartClicked() {
        // TODO
    }

    private fun loadArticle(articleId: String) {
        viewModelScope.launch {
            val result = getArticleUseCase(articleId)
            _article.value = result
        }
    }
}
