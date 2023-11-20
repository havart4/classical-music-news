package com.vandele.classicalmusicnews.ui.screen.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.vandele.classicalmusicnews.ui.navigation.ARTICLE_ID_ARG
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(savedStateHandle: SavedStateHandle) : ViewModel() {
    val articleId: String? = savedStateHandle[ARTICLE_ID_ARG]
}
