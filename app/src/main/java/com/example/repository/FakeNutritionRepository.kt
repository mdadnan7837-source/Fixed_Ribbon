package com.example.repository

import com.example.model.DailyNutrition
import com.example.model.Meal
import com.example.util.DateFormatter
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeNutritionRepository : BaseRepository(), NutritionRepository {

    private val sampleMeals = mutableListOf(
        Meal("m1", "Oatmeal with Berries & Honey", 350, 12.0, 55.0, 6.0),
        Meal("m2", "Grilled Chicken Salad", 450, 42.0, 15.0, 14.0),
        Meal("m3", "Protein Smoothie", 280, 25.0, 30.0, 4.0)
    )

    override fun getDailyNutrition(date: String): Flow<Resource<DailyNutrition>> = safeApiCall {
        val totalCal = sampleMeals.sumOf { it.calories }
        val totalProtein = sampleMeals.sumOf { it.proteinGrams }
        val totalCarbs = sampleMeals.sumOf { it.carbsGrams }
        val totalFat = sampleMeals.sumOf { it.fatGrams }

        DailyNutrition(
            date = date.ifEmpty { DateFormatter.getCurrentDateString() },
            totalCalories = totalCal,
            targetCalories = 2200,
            proteinGrams = totalProtein,
            carbsGrams = totalCarbs,
            fatGrams = totalFat,
            meals = sampleMeals.toList()
        )
    }

    override fun addMeal(date: String, meal: Meal): Flow<Resource<Meal>> = safeApiCall {
        val newMeal = meal.copy(id = "m_${System.currentTimeMillis()}")
        sampleMeals.add(newMeal)
        newMeal
    }

    override fun deleteMeal(date: String, mealId: String): Flow<Resource<Unit>> = safeApiCall {
        sampleMeals.removeAll { it.id == mealId }
    }
}
