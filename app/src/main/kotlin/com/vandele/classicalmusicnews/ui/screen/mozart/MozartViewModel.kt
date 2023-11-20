@file:OptIn(ExperimentalCoroutinesApi::class)

package com.vandele.classicalmusicnews.ui.screen.mozart

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vandele.classicalmusicnews.model.Article
import com.vandele.classicalmusicnews.model.MozartOpinion
import com.vandele.classicalmusicnews.model.RefreshState
import com.vandele.classicalmusicnews.model.RetryableError
import com.vandele.classicalmusicnews.ui.navigation.ARTICLE_ID_ARG
import com.vandele.classicalmusicnews.usecase.GetArticleUseCase
import com.vandele.classicalmusicnews.usecase.GetMozartOpinionUseCase
import com.vandele.classicalmusicnews.usecase.RefreshMozartOpinionUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MozartViewModel @Inject constructor(
    private val refreshMozartOpinionUseCase: RefreshMozartOpinionUseCase,
    savedStateHandle: SavedStateHandle,
    getArticleUseCase: GetArticleUseCase,
    getMozartOpinionUseCase: GetMozartOpinionUseCase,
) : ViewModel() {
    val article: Flow<Article?> = getArticleUseCase(savedStateHandle[ARTICLE_ID_ARG] ?: "")

    private val mozartOpinionRefreshState = MutableStateFlow<RefreshState>(RefreshState.Loading)
    private val mozartOpinion = article.flatMapLatest { getMozartOpinionUseCase(it?.id ?: "") }

    val mozartScreenState: Flow<MozartScreenState> = combine(
        mozartOpinion,
        mozartOpinionRefreshState,
    ) { mozartOpinion, mozartOpinionRefreshState ->
        if (mozartOpinion != null) {
            MozartScreenState.Content(mozartOpinion)
        } else {
            when (mozartOpinionRefreshState) {
                is RefreshState.Loading, is RefreshState.Content -> {
                    MozartScreenState.Loading
                }
                is RefreshState.Error -> {
                    MozartScreenState.Error(
                        RetryableError(
                            mozartOpinionRefreshState.cmnError,
                            onRetry = ::refreshMozartOpinion,
                        )
                    )
                }
            }
        }
    }


    init {
        viewModelScope.launch {
            if (mozartOpinion.first() == null) {
                refreshMozartOpinion()
            }
        }
    }

    private fun refreshMozartOpinion() {
        viewModelScope.launch {
            article.first()?.link?.let { articleLink ->
                mozartOpinionRefreshState.value = RefreshState.Loading
                Log.d("MozartViewModel", "Invoking refreshMozartOpinionUseCase")
                refreshMozartOpinionUseCase(articleLink).map {
                    mozartOpinionRefreshState.value = RefreshState.Content
                }.mapLeft {
                    mozartOpinionRefreshState.value = RefreshState.Error(it)
                }
            }
        }
    }
}

sealed interface MozartScreenState {
    data object Loading : MozartScreenState
    data class Error(val retryableError: RetryableError) : MozartScreenState
    data class Content(val mozartOpinion: MozartOpinion) : MozartScreenState
}
