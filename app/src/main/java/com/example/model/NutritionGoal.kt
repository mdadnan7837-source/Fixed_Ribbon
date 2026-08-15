package com.example.model

enum class NutritionGoal(
    val id: String,
    val title: String,
    val shortName: String,
    val tag: String,
    val emoji: String,
    val description: String
) {
    CANCER_AWARE(
        id = "cancer_aware",
        title = "Cancer-Aware Nutrition",
        shortName = "Cancer-Aware",
        tag = "Cancer-Aware",
        emoji = "🛡",
        description = "Make mindful food choices today to support long-term wellness and cell protection."
    ),
    WEIGHT_LOSS(
        id = "weight_loss",
        title = "Weight Loss & Management",
        shortName = "Weight Loss",
        tag = "Weight Loss",
        emoji = "⚖️",
        description = "Maintain a healthy caloric deficit with nutrient-dense, high-satiety meals."
    ),
    MUSCLE_GAIN(
        id = "muscle_gain",
        title = "Muscle Gain & Hypertrophy",
        shortName = "Muscle Gain",
        tag = "Muscle Gain",
        emoji = "💪",
        description = "Fuel muscle synthesis with optimal protein timing and balanced complex carbohydrates."
    ),
    HEART_HEALTH(
        id = "heart_health",
        title = "Heart Health & Cardio Care",
        shortName = "Heart Health",
        tag = "Heart Health",
        emoji = "❤️",
        description = "Focus on omega-3 fats, low sodium, and cholesterol-lowering soluble fiber."
    ),
    DIABETES_FRIENDLY(
        id = "diabetes_friendly",
        title = "Diabetes-Friendly & Glycemic Control",
        shortName = "Diabetes-Friendly",
        tag = "Diabetes-Friendly",
        emoji = "🩸",
        description = "Manage blood glucose with low-GI foods, steady fiber, and balanced macronutrients."
    );

    companion object {
        fun fromString(name: String): NutritionGoal {
            val normalized = name.lowercase().trim()
            return entries.find { 
                it.id.lowercase() == normalized || 
                it.title.lowercase() == normalized || 
                it.shortName.lowercase() == normalized ||
                it.tag.lowercase() == normalized ||
                normalized.contains(it.shortName.lowercase()) ||
                it.shortName.lowercase().contains(normalized)
            } ?: CANCER_AWARE
        }
    }
}
