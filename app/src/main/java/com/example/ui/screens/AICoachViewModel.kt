package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.ChatMessage
import com.example.model.CoachInsight
import com.example.model.NutritionGoal
import com.example.model.TodayOverviewItem
import com.example.repository.AiCoachRepository
import com.example.repository.FakeAiCoachRepository
import com.example.repository.GoalManager
import com.example.util.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class AiCoachUiState(
    val userName: String = "Alex",
    val activeGoal: NutritionGoal = NutritionGoal.WEIGHT_LOSS,
    val chatMessages: List<ChatMessage> = emptyList(),
    val todayOverview: List<TodayOverviewItem> = emptyList(),
    val coachInsight: CoachInsight? = null,
    val inputText: String = "",
    val isSending: Boolean = false,
    val quickPrompts: List<String> = listOf(
        "Healthy dinner ideas",
        "Foods to avoid",
        "How much protein do I need?",
        "Tips to reduce sugar"
    )
)

class AICoachViewModel(
    private val repository: AiCoachRepository = FakeAiCoachRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(AiCoachUiState())
    val uiState: StateFlow<AiCoachUiState> = _uiState.asStateFlow()

    init {
        loadData()
        observeGoal()
    }

    private fun observeGoal() {
        viewModelScope.launch {
            GoalManager.selectedGoal.collect { goal ->
                val isWeightLoss = (goal == NutritionGoal.WEIGHT_LOSS)
                val name = if (isWeightLoss) "Gogo Ji" else "Alex"
                _uiState.update { current ->
                    current.copy(userName = name, activeGoal = goal)
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            repository.getChatHistory().collect { res ->
                if (res is Resource.Success) {
                    _uiState.update { it.copy(chatMessages = res.data ?: emptyList()) }
                }
            }
        }
        viewModelScope.launch {
            repository.getTodayOverview().collect { res ->
                if (res is Resource.Success) {
                    _uiState.update { it.copy(todayOverview = res.data ?: emptyList()) }
                }
            }
        }
        viewModelScope.launch {
            repository.getCoachInsights().collect { res ->
                if (res is Resource.Success) {
                    _uiState.update { it.copy(coachInsight = res.data) }
                }
            }
        }
    }

    fun onInputTextChanged(newText: String) {
        _uiState.update { it.copy(inputText = newText) }
    }

    fun onSendMessage(text: String = _uiState.value.inputText) {
        val trimmed = text.trim()
        if (trimmed.isEmpty() || _uiState.value.isSending) return

        _uiState.update { it.copy(inputText = "", isSending = true) }

        viewModelScope.launch {
            repository.sendMessage(trimmed).collect { res ->
                if (res is Resource.Success) {
                    repository.getChatHistory().collect { historyRes ->
                        if (historyRes is Resource.Success) {
                            _uiState.update {
                                it.copy(
                                    chatMessages = historyRes.data ?: emptyList(),
                                    isSending = false
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    fun onClearChat() {
        viewModelScope.launch {
            repository.clearChat().collect {
                repository.getChatHistory().collect { historyRes ->
                    if (historyRes is Resource.Success) {
                        _uiState.update { it.copy(chatMessages = historyRes.data ?: emptyList()) }
                    }
                }
            }
        }
    }

    fun onQuickPromptClicked(prompt: String) {
        onSendMessage(prompt)
    }
}
