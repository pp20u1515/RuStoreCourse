package com.example.rustorecourse.presentation.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.usecase.GetAppDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsScreenViewModel @Inject constructor(
    private val getLocalAppDetailsUseCase: GetAppDetailsUseCase,
    private val savedStateHandle: SavedStateHandle
): ViewModel() {
    private val _appDetailsState = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val appDetailsState: StateFlow<AppDetailsState> = _appDetailsState.asStateFlow()

    init {
        val appId = savedStateHandle.get<String>("appId") ?: ""

        viewModelScope.launch {
            loadApp(appId)
        }
    }

    fun loadApp(id: String) {
        viewModelScope.launch {
            _appDetailsState.value = AppDetailsState.Loading

            val loadedApp = getLocalAppDetailsUseCase.invoke(id).getOrNull()

            _appDetailsState.value = when {
                loadedApp == null -> {
                    AppDetailsState.Error("Не удалось загрузить приложение!")
                }
                loadedApp != null -> {
                    AppDetailsState.Success(loadedApp)
                }
                else -> {
                    AppDetailsState.Error("Не удалось загрузить данные!")
                }
            }
        }
    }

    sealed class AppDetailsState{
        object Loading: AppDetailsState()
        data class Success(val app: App): AppDetailsState()
        data class Error(val message: String): AppDetailsState()
    }
}