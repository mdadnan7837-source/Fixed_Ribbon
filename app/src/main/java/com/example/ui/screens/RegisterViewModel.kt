package com.example.ui.screens

import androidx.lifecycle.viewModelScope
import com.example.repository.AuthRepository
import com.example.ui.base.BaseViewModel
import com.example.ui.base.UiEvent
import com.example.util.Resource
import com.example.util.ValidationUtils
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

data class RegisterUiState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val gender: String = "Female",
    val age: String = "28",
    val height: String = "168",
    val weight: String = "62",
    val primaryGoal: String = "Healthy Lifestyle",
    val termsAccepted: Boolean = false,
    
    val nameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val ageError: String? = null,
    val heightError: String? = null,
    val weightError: String? = null,
    val termsError: String? = null,

    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class RegisterViewModel(
    private val authRepository: AuthRepository
) : BaseViewModel<RegisterUiState>(RegisterUiState()) {

    fun onNameChanged(name: String) {
        updateState {
            copy(
                name = name,
                nameError = if (name.isNotEmpty() && !ValidationUtils.isValidName(name)) "Name must be at least 2 characters" else null,
                errorMessage = null
            )
        }
    }

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
        val confirm = uiState.value.confirmPassword
        updateState {
            copy(
                password = password,
                passwordError = if (password.isNotEmpty() && !ValidationUtils.isValidPassword(password)) "Minimum 6 characters" else null,
                confirmPasswordError = if (confirm.isNotEmpty() && !ValidationUtils.doPasswordsMatch(password, confirm)) "Passwords do not match" else null,
                errorMessage = null
            )
        }
    }

    fun onConfirmPasswordChanged(confirmPassword: String) {
        val pwd = uiState.value.password
        updateState {
            copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = if (confirmPassword.isNotEmpty() && !ValidationUtils.doPasswordsMatch(pwd, confirmPassword)) "Passwords do not match" else null,
                errorMessage = null
            )
        }
    }

    fun onGenderChanged(gender: String) {
        updateState { copy(gender = gender) }
    }

    fun onAgeChanged(age: String) {
        updateState {
            copy(
                age = age,
                ageError = if (age.isNotEmpty() && !ValidationUtils.isValidAge(age)) "Age must be between 12 and 120" else null
            )
        }
    }

    fun onHeightChanged(height: String) {
        updateState {
            copy(
                height = height,
                heightError = if (height.isNotEmpty() && !ValidationUtils.isValidHeight(height)) "Height in cm (50 - 250)" else null
            )
        }
    }

    fun onWeightChanged(weight: String) {
        updateState {
            copy(
                weight = weight,
                weightError = if (weight.isNotEmpty() && !ValidationUtils.isValidWeight(weight)) "Weight in lb (50 - 600)" else null
            )
        }
    }

    fun onPrimaryGoalChanged(goal: String) {
        updateState { copy(primaryGoal = goal) }
        com.example.repository.GoalManager.setGoalByName(goal)
    }

    fun onTermsAcceptedChanged(accepted: Boolean) {
        updateState { copy(termsAccepted = accepted, termsError = null) }
    }

    fun register() {
        val s = uiState.value
        val isNameValid = ValidationUtils.isValidName(s.name)
        val isEmailValid = ValidationUtils.isValidEmail(s.email)
        val isPasswordValid = ValidationUtils.isValidPassword(s.password)
        val isConfirmValid = ValidationUtils.doPasswordsMatch(s.password, s.confirmPassword)
        val isAgeValid = ValidationUtils.isValidAge(s.age)
        val isHeightValid = ValidationUtils.isValidHeight(s.height)
        val isWeightValid = ValidationUtils.isValidWeight(s.weight)
        val isTermsValid = s.termsAccepted

        if (!isNameValid || !isEmailValid || !isPasswordValid || !isConfirmValid ||
            !isAgeValid || !isHeightValid || !isWeightValid || !isTermsValid) {
            updateState {
                copy(
                    nameError = if (!isNameValid) "Full Name is required" else null,
                    emailError = if (!isEmailValid) "Valid email is required" else null,
                    passwordError = if (!isPasswordValid) "Minimum 6 characters" else null,
                    confirmPasswordError = if (!isConfirmValid) "Passwords do not match" else null,
                    ageError = if (!isAgeValid) "Valid age required" else null,
                    heightError = if (!isHeightValid) "Valid height in cm required" else null,
                    weightError = if (!isWeightValid) "Valid weight in lb required" else null,
                    termsError = if (!isTermsValid) "You must accept Terms & Conditions" else null
                )
            }
            return
        }

        authRepository.register(
            name = s.name.trim(),
            email = s.email.trim(),
            password = s.password,
            gender = s.gender,
            age = s.age.toIntOrNull() ?: 25,
            heightCm = s.height.toDoubleOrNull() ?: 170.0,
            weightKg = s.weight.toDoubleOrNull() ?: 65.0,
            primaryGoal = s.primaryGoal
        )
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
