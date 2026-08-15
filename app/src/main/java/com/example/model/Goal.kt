package com.example.model

data class Goal(
    val id: String = "1",
    val title: String = "Weight Loss",
    val targetCalories: Int = 2000,
    val targetProteinGrams: Double = 150.0,
    val targetCarbsGrams: Double = 200.0,
    val targetFatGrams: Double = 60.0,
    val targetWeightKg: Double = 70.0
)
