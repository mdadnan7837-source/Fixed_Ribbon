package com.example.repository

import com.example.model.Goal
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeGoalRepository : BaseRepository(), GoalRepository {

    private var currentGoal = Goal(
        id = "goal_1",
        title = "Healthy Lean & Fit",
        targetCalories = 2200,
        targetProteinGrams = 160.0,
        targetCarbsGrams = 220.0,
        targetFatGrams = 65.0,
        targetWeightKg = 70.0
    )

    override fun getGoal(): Flow<Resource<Goal>> = safeApiCall {
        currentGoal
    }

    override fun updateGoal(goal: Goal): Flow<Resource<Goal>> = safeApiCall {
        currentGoal = goal
        currentGoal
    }
}
