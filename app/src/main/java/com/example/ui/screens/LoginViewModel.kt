package com.example.ui.screens

import androidx.lifecycle.viewModelScope
import com.example.repository.AuthRepository
import com.example.ui.base.BaseViewModel
import com.example.ui.base.UiEvent
import com.example.util.Resource
import com.example.util.ValidationUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class LoginUiState(
    val email: String = "sarah@example.com",
    val password: String = "password123",
    val rememberMe: Boolean = true,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<LoginUiState>(LoginUiState()) {

    fun onEmailChanged(email: String) {
        updateState {
            copy(
                email = email,
                emailError = if (email.isNotEmpty() && !ValidationUtils.isValidEmail(email)) "Enter a valid email address" else null,
                errorMessage = null
            )
        }
    }

    fun onPasswordChanged(password: String) {
        updateState {
            copy(
                password = password,
                passwordError = if (password.isNotEmpty() && !ValidationUtils.isValidPassword(password)) "Password must be at least 6 characters" else null,
                errorMessage = null
            )
        }
    }

    fun onRememberMeChanged(rememberMe: Boolean) {
        updateState { copy(rememberMe = rememberMe) }
    }

    fun login() {
        val email = uiState.value.email.trim()
        val password = uiState.value.password

        val isEmailValid = ValidationUtils.isValidEmail(email)
        val isPasswordValid = ValidationUtils.isValidPassword(password)

        if (!isEmailValid || !isPasswordValid) {
            updateState {
                copy(
                    emailError = if (!isEmailValid) "Valid email is required" else null,
                    passwordError = if (!isPasswordValid) "Password must be at least 6 characters" else null
                )
            }
            return
        }

        authRepository.login(email, password)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateState { copy(isLoading = true, errorMessage = null) }
                    }
                    is Resource.Success -> {
                        updateState { copy(isLoading = false, isSuccess = true) }
                    }
                    is Resource.Error -> {
                        updateState { copy(isLoading = false, errorMessage = resource.message) }
                        sendEvent(UiEvent.ShowSnackbar(resource.message))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loginWithGoogle() {
        authRepository.loginWithGoogle()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = true, errorMessage = null) }
                    is Resource.Success -> updateState { copy(isLoading = false, isSuccess = true) }
                    is Resource.Error -> {
                        updateState { copy(isLoading = false, errorMessage = resource.message) }
                        sendEvent(UiEvent.ShowSnackbar(resource.message))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loginWithApple() {
        authRepository.loginWithApple()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = true, errorMessage = null) }
                    is Resource.Success -> updateState { copy(isLoading = false, isSuccess = true) }
                    is Resource.Error -> {
                        updateState { copy(isLoading = false, errorMessage = resource.message) }
                        sendEvent(UiEvent.ShowSnackbar(resource.message))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun loginAsGuest() {
        authRepository.loginAsGuest()
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> updateState { copy(isLoading = true, errorMessage = null) }
                    is Resource.Success -> updateState { copy(isLoading = false, isSuccess = true) }
                    is Resource.Error -> {
                        updateState { copy(isLoading = false, errorMessage = resource.message) }
                        sendEvent(UiEvent.ShowSnackbar(resource.message))
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    fun clearError() {
        updateState { copy(errorMessage = null) }
    }
}
