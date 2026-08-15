package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.User
import com.example.repository.AuthRepository
import com.example.repository.GoalManager
import com.example.repository.FakeAuthRepository
import com.example.repository.FakeProfileRepository
import com.example.repository.ProfileRepository
import com.example.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfileUiState(
    val user: User = User(
        id = "user_101",
        name = "Alex Johnson",
        email = "alex.johnson@email.com",
        age = 28,
        weightKg = 72.5,
        heightCm = 178.0,
        gender = "Male"
    ),
    val activeGoal: String = GoalManager.getGoal().title,
    val memberSince: String = "May 15, 2024",
    val streakDays: Int = 18,
    val avgScore: Int = 82,
    val daysTracked: Int = 27,
    val waterIntake: String = "1.8 L",
    val achievementsCount: Int = 12,
    val dietaryPreferences: String = "Vegetarian • No Red Meat • Low Sodium",
    val notificationsEnabled: Boolean = true,
    val appVersion: String = "1.0.0",
    val isLoading: Boolean = false
)

class ProfileViewModel(
    private val repository: ProfileRepository = FakeProfileRepository(),
    private val authRepository: AuthRepository = FakeAuthRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUiState())
    val uiState: StateFlow<ProfileUiState> = _uiState.asStateFlow()

    init {
        loadProfile()
        observeGoal()
    }

    private fun observeGoal() {
        viewModelScope.launch {
            GoalManager.selectedGoal.collect { goal ->
                val isWeightLoss = (goal == com.example.model.NutritionGoal.WEIGHT_LOSS)
                val name = if (isWeightLoss) "Gogo Ji" else "Alex Johnson"
                val email = if (isWeightLoss) "gogoji@email.com" else "alex.johnson@email.com"
                _uiState.update { current ->
                    current.copy(
                        activeGoal = goal.title,
                        user = current.user.copy(name = name, email = email)
                    )
                }
            }
        }
    }

    private fun loadProfile() {
        viewModelScope.launch {
            repository.getUserProfile().collect { res ->
                if (res is Resource.Success && res.data != null) {
                    val isWeightLoss = (GoalManager.getGoal() == com.example.model.NutritionGoal.WEIGHT_LOSS)
                    val name = if (isWeightLoss) "Gogo Ji" else "Alex Johnson"
                    val email = if (isWeightLoss) "gogoji@email.com" else "alex.johnson@email.com"
                    _uiState.update { state ->
                        state.copy(
                            user = res.data.copy(name = name, email = email)
                        )
                    }
                }
            }
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            authRepository.logout().collect { res ->
                if (res is Resource.Success) {
                    onLoggedOut()
                }
            }
        }
    }
}

