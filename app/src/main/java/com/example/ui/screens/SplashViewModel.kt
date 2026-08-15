package com.example.ui.screens

import androidx.lifecycle.viewModelScope
import com.example.repository.AuthRepository
import com.example.ui.base.BaseViewModel
import com.example.ui.base.UiEvent
import com.example.util.Resource
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class SplashUiState(
    val isLoading: Boolean = true,
    val isLoggedIn: Boolean = false
)

class SplashViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<SplashUiState>(SplashUiState()) {

    init {
        checkSession()
    }

    fun checkSession() {
        updateState { copy(isLoading = true) }
        authRepository.checkSession()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateState { copy(isLoading = true) }
                    }
                    is Resource.Success -> {
                        val isLoggedIn = resource.data
                        updateState { copy(isLoading = false, isLoggedIn = isLoggedIn) }
                    }
                    is Resource.Error -> {
                        updateState { copy(isLoading = false, isLoggedIn = false) }
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
