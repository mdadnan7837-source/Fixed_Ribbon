package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.FoodAnalysisData
import com.example.model.FoodAnalysisItem
import com.example.model.NutritionGoal
import com.example.repository.GoalManager
import com.example.repository.TemporaryFoodHistoryManager
import com.example.repository.toFoodAnalysisData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class FoodAnalysisUiState(
    val activeGoal: NutritionGoal = GoalManager.getGoal(),
    val userName: String = if (GoalManager.getGoal() == NutritionGoal.WEIGHT_LOSS) "Gogo Ji" else "Alex",
    val data: FoodAnalysisData = FoodAnalysisData(),
    val items: List<FoodAnalysisItem> = emptyList(),
    val isSaved: Boolean = false,
    val isLoading: Boolean = false
)

class FoodAnalysisViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(FoodAnalysisUiState())
    val uiState: StateFlow<FoodAnalysisUiState> = _uiState.asStateFlow()

    init {
        observeData()
    }

    private fun observeData() {
        viewModelScope.launch {
            combine(
                GoalManager.selectedGoal,
                TemporaryFoodHistoryManager.items,
                TemporaryFoodHistoryManager.selectedItem
            ) { goal, itemsList, selected ->
                val isWeightLoss = (goal == NutritionGoal.WEIGHT_LOSS)
                val name = if (isWeightLoss) "Gogo Ji" else "Alex"
                val foodData = selected.toFoodAnalysisData(name)
                
                FoodAnalysisUiState(
                    activeGoal = goal,
                    userName = name,
                    data = foodData,
                    items = itemsList
                )
            }.collect { newState ->
                _uiState.value = newState
            }
        }
    }

    fun selectFoodItem(item: FoodAnalysisItem) {
        TemporaryFoodHistoryManager.selectFoodItem(item)
    }

    fun deleteFoodItem(id: String) {
        TemporaryFoodHistoryManager.deleteFoodItem(id)
    }

    fun addScannedFood(item: FoodAnalysisItem) {
        TemporaryFoodHistoryManager.addFoodItem(item)
    }
}

