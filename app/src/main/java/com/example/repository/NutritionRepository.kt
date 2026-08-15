package com.example.repository

import com.example.model.DailyNutrition
import com.example.model.Meal
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface NutritionRepository {
    fun getDailyNutrition(date: String): Flow<Resource<DailyNutrition>>
    fun addMeal(date: String, meal: Meal): Flow<Resource<Meal>>
    fun deleteMeal(date: String, mealId: String): Flow<Resource<Unit>>
}
