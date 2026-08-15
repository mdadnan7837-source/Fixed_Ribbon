package com.example.repository

import com.example.model.Goal
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface GoalRepository {
    fun getGoal(): Flow<Resource<Goal>>
    fun updateGoal(goal: Goal): Flow<Resource<Goal>>
}
