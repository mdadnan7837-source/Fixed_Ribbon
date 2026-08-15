package com.example.repository

import com.example.model.ChipType
import com.example.model.FoodAnalysisData
import com.example.model.FoodAnalysisItem
import com.example.model.HealthInsightChip
import com.example.model.NutrientMetric
import com.example.model.StatusColorType
import com.example.model.defaultFoodAnalysisList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object TemporaryFoodHistoryManager {
    private val _items = MutableStateFlow<List<FoodAnalysisItem>>(defaultFoodAnalysisList)
    val items: StateFlow<List<FoodAnalysisItem>> = _items.asStateFlow()

    private val _selectedItem = MutableStateFlow<FoodAnalysisItem>(defaultFoodAnalysisList.first())
    val selectedItem: StateFlow<FoodAnalysisItem> = _selectedItem.asStateFlow()

    fun addFoodItem(item: FoodAnalysisItem) {
        _items.update { listOf(item) + it.filterNot { existing -> existing.id == item.id } }
        _selectedItem.value = item
    }

    fun deleteFoodItem(id: String) {
        _items.update { list ->
            val updated = list.filterNot { it.id == id }
            if (_selectedItem.value.id == id && updated.isNotEmpty()) {
                _selectedItem.value = updated.first()
            }
            updated
        }
    }

    fun selectFoodItem(item: FoodAnalysisItem) {
        _selectedItem.value = item
    }

    fun clearTemporaryHistory() {
        _items.value = emptyList()
    }

    fun getSelectedItemAsData(userName: String = "Gogo Ji"): FoodAnalysisData {
        val item = _selectedItem.value
        return item.toFoodAnalysisData(userName)
    }
}

fun FoodAnalysisItem.toFoodAnalysisData(userName: String = "Gogo Ji"): FoodAnalysisData {
    val defaultMetrics = listOf(
        NutrientMetric("Calories", "520 kcal", "Moderate", StatusColorType.ORANGE),
        NutrientMetric("Protein", "42 g", "High", StatusColorType.GREEN),
        NutrientMetric("Fiber", "9 g", "High", StatusColorType.GREEN),
        NutrientMetric("Total Fat", "18 g", "Moderate", StatusColorType.ORANGE),
        NutrientMetric("Saturated Fat", "4 g", "Low", StatusColorType.GREEN),
        NutrientMetric("Carbohydrates", "45 g", "Moderate", StatusColorType.ORANGE),
        NutrientMetric("Sodium", "620 mg", "Moderate", StatusColorType.ORANGE),
        NutrientMetric("Added Sugar", "1.5 g", "Low", StatusColorType.GREEN),
        NutrientMetric("Cholesterol", "35 mg", "Low", StatusColorType.GREEN),
        NutrientMetric("Potassium", "650 mg", "High", StatusColorType.GREEN)
    )

    return FoodAnalysisData(
        foodName = this.foodName,
        ingredients = this.ingredients,
        scanTimestamp = this.scanTimestamp,
        score = this.score,
        scoreStatus = this.scoreStatus,
        greatChoiceTitle = "Great Choice, $userName!",
        greatChoiceSubtitle = this.aiNutritionInsight ?: "This meal provides strong protein and fiber while fitting well into your weight-loss nutrition goals.",
        healthInsights = if (this.healthInsights.isNotEmpty()) this.healthInsights else listOf(
            HealthInsightChip("High in Protein", ChipType.BLUE_TAG),
            HealthInsightChip("High in Fiber", ChipType.GREEN_LEAF),
            HealthInsightChip("Balanced", ChipType.BLUE_TAG),
            HealthInsightChip("Heart Healthy", ChipType.HEART_RED)
        ),
        nutrientMetrics = if (this.nutrientMetrics.isNotEmpty()) this.nutrientMetrics else defaultMetrics,
        highSodiumTitle = if (this.highSodiumWarning != null) "Sodium Warning" else "Sodium & Hydration",
        highSodiumWarning = this.highSodiumWarning ?: "Ensure adequate water intake throughout the day to support healthy sodium balance and metabolism.",
        aiRecommendation = this.aiRecommendation ?: "Pair this meal with fresh vegetables and plenty of water for optimal satiety and weight-loss progress."
    )
}
