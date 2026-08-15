package com.example.model

data class Meal(
    val id: String = "",
    val name: String = "",
    val calories: Int = 0,
    val proteinGrams: Double = 0.0,
    val carbsGrams: Double = 0.0,
    val fatGrams: Double = 0.0,
    val timestamp: Long = System.currentTimeMillis()
)
