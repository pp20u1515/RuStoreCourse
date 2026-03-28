package com.example.rustorecourse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.usecase.GetRemoteAppUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppDetailsScreenViewModel @Inject constructor(
    private val getRemoteAppUseCase: GetRemoteAppUseCase
): ViewModel() {
    private val _appDetailsState = MutableStateFlow<AppDetailsState>(AppDetailsState.Loading)
    val appDetailsState: StateFlow<AppDetailsState> = _appDetailsState.asStateFlow()

    fun loadApp(id: String) {
        viewModelScope.launch {
            _appDetailsState.value = AppDetailsState.Loading
            try {
                val loadedApp = getRemoteAppUseCase.invoke(id)

                _appDetailsState.value = when {
                    loadedApp.isSuccess ->{
                        val app = loadedApp.getOrNull() ?: null

                        if (app == null){
                            AppDetailsState.Error("Не удалось загрузить данные!")
                        }
                        else{
                            AppDetailsState.Success(app)
                        }
                    }
                    else -> {
                        val errorMessage = loadedApp.exceptionOrNull()?.message ?: "Неизвестная ошибка"
                        AppDetailsState.Error("Не удалось загрузить приложение: $errorMessage")
                    }
                }
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