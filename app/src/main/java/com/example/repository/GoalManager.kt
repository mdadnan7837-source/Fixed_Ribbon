package com.example.repository

import com.example.model.NutritionGoal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

object GoalManager {
    private val _selectedGoal = MutableStateFlow(NutritionGoal.WEIGHT_LOSS)
    val selectedGoal: StateFlow<NutritionGoal> = _selectedGoal.asStateFlow()

    fun setGoal(goal: NutritionGoal) {
        _selectedGoal.value = goal
    }

    fun setGoalByName(name: String) {
        _selectedGoal.value = NutritionGoal.fromString(name)
    }

    fun getGoal(): NutritionGoal = _selectedGoal.value
}

