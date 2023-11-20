package com.vandele.classicalmusicnews

import androidx.lifecycle.ViewModel
import com.vandele.classicalmusicnews.model.DarkModePreference
import com.vandele.classicalmusicnews.usecase.GetSettingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    getSettingsUseCase: GetSettingsUseCase,
) : ViewModel() {
    val uiState: Flow<MainActivityUiState> = getSettingsUseCase().map {
        MainActivityUiState.Success(it.darkModePreference)
    }
}

sealed interface MainActivityUiState {
    data object Loading : MainActivityUiState
    data class Success(val darkModePreference: DarkModePreference) : MainActivityUiState
}
