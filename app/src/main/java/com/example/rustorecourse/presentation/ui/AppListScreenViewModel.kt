package com.example.rustorecourse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.usecase.GetRemoteListOfAppsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppListScreenViewModel @Inject constructor(
    private val getRemoteListOfApss: GetRemoteListOfAppsUseCase
): ViewModel() {

    private val _appListScreenState = MutableStateFlow<AppListScreenState>(AppListScreenState.Loading)
    val appListScreenState: StateFlow<AppListScreenState> = _appListScreenState.asStateFlow()

    init {
        loadApps()
    }

    private fun loadApps() {
        viewModelScope.launch {
            _appListScreenState.value = AppListScreenState.Loading
            try {
                val listOfApps = getRemoteListOfApss.invoke()
                _appListScreenState.value = when {
                    listOfApps.isSuccess -> {
                        val apps = listOfApps.getOrNull() ?: emptyList()

                        if (apps.isEmpty()){
                            AppListScreenState.Error("Список приложений пуст")
                        }
                        else{
                            AppListScreenState.Success(apps)
                        }
                    }
                    else -> {
                        val errorMessage = listOfApps.exceptionOrNull()?.message ?: "Неизвестная ошибка"
                        AppListScreenState.Error("Не удалось загрузить приложения: $errorMessage")
                    }
                }
            }catch (e: Exception){
                _appListScreenState.value = AppListScreenState.Error("Не удалось загрузить приложения: ${e.message}")
            }
        }
    }

    sealed class AppListScreenState{
        object Loading: AppListScreenState()
        data class Success(val listOfApps: List<AppDetailsItem>): AppListScreenState()
        data class Error(val message: String): AppListScreenState()
    }
}