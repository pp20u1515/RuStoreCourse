package com.example.rustorecourse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.usecase.GetAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsScreenViewModel @Inject constructor(
    private val getAppUseCase: GetAppUseCase
): ViewModel() {
    private val _appDetailsState = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val appDetailsState: StateFlow<AppDetailsState> = _appDetailsState.asStateFlow()

    fun loadApp() {
        viewModelScope.launch {
            _appDetailsState.value = AppDetailsState.Loading
            try {
                val loadedApp = getAppUseCase.invoke()
                _appDetailsState.value = AppDetailsState.Success(loadedApp)
            } catch (e: Exception) {
                _appDetailsState.value = AppDetailsState.Error("Не удалось загрузить приложение: ${e.message}")
            }
        }
    }

    sealed class AppDetailsState{
        object Loading: AppDetailsState()
        data class Success(val app: App): AppDetailsState()
        data class Error(val message: String): AppDetailsState()
    }
}