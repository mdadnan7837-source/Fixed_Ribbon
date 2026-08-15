package com.example.ui.screens

import androidx.lifecycle.viewModelScope
import com.example.repository.AuthRepository
import com.example.ui.base.BaseViewModel
import com.example.ui.base.UiEvent
import com.example.util.Resource
import com.example.util.ValidationUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class ForgotPasswordUiState(
    val email: String = "",
    val emailError: String? = null,
    val isLoading: Boolean = false,
    val successMessage: String? = null,
    val errorMessage: String? = null
)

class ForgotPasswordViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<ForgotPasswordUiState>(ForgotPasswordUiState()) {

    fun onEmailChanged(email: String) {
        updateState {
            copy(
                email = email,
                emailError = if (email.isNotEmpty() && !ValidationUtils.isValidEmail(email)) "Enter a valid email address" else null,
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun sendResetLink() {
        val email = uiState.value.email.trim()
        if (!ValidationUtils.isValidEmail(email)) {
            updateState { copy(emailError = "Valid email address is required") }
            return
        }

        authRepository.forgotPassword(email)
            .onEach { resource ->
                when (resource) {
                    is Resource.Loading -> {
                        updateState { copy(isLoading = true, errorMessage = null, successMessage = null) }
                    }
                    is Resource.Success -> {
                        updateState {
                            copy(
                                isLoading = false,
                                successMessage = resource.data,
                                errorMessage = null
                            )
                        }
                    }
                    is Resource.Error -> {
                        updateState {
                            copy(
                                isLoading = false,
                                errorMessage = resource.message,
                                successMessage = null
                            )
                        }
                        sendEvent(UiEvent.ShowSnackbar(resource.message))
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}
