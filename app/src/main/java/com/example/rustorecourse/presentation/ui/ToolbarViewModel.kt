package com.example.rustorecourse.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rustorecourse.domain.usecase.GetWishListStatusUseCase
import com.example.rustorecourse.domain.usecase.UpdateWishListStatusUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ToolbarViewModel @Inject constructor(
    val getWishListStatus: GetWishListStatusUseCase,
    val updateWishListStatus: UpdateWishListStatusUseCase
): ViewModel() {
    private val _appId = MutableStateFlow("")
    private val _wishListState = MutableStateFlow<WishlistState>(WishlistState.Initial)
    val wishlistState: StateFlow<WishlistState> = _wishListState.asStateFlow()

    fun setAppId(appId: String) {
        if (_appId.value != appId) {
            _appId.value = appId
            loadWishlistStatus()
        }
    }

    private fun loadWishlistStatus() {
        viewModelScope.launch {
            _wishListState.value = WishlistState.Loading
            try {
                val status = getWishListStatus(_appId.value)
                _wishListState.value = WishlistState.Success(status)
            } catch (e: Exception) {
                _wishListState.value = WishlistState.Error(e.message ?: "Ошибка загрузки")
            }
        }
    }

    fun toggleWishlist() {
        viewModelScope.launch {
            val currentState = _wishListState.value
            if (currentState !is WishlistState.Success) return@launch
            _wishListState.value = WishlistState.Loading

            try {
                val newStatus = !currentState.isInWishlist
                updateWishListStatus(_appId.value, newStatus)
                _wishListState.value = WishlistState.Success(newStatus)
            } catch (e: Exception) {
                _wishListState.value = WishlistState.Error(e.message ?: "Ошибка обновления")
            }
        }
    }

    sealed class WishlistState {
        object Initial : WishlistState()
        object Loading : WishlistState()
        data class Success(val isInWishlist: Boolean) : WishlistState()
        data class Error(val message: String) : WishlistState()
    }
}