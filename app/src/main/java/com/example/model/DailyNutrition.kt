package com.example.model

data class DailyNutrition(
    val date: String = "",
    val totalCalories: Int = 0,
    val targetCalories: Int = 2000,
    val proteinGrams: Double = 0.0,
    val carbsGrams: Double = 0.0,
    val fatGrams: Double = 0.0,
    val meals: List<Meal> = emptyList()
)
