package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.NutritionGoal
import com.example.repository.GoalManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class KpiSummary(
    val averageScore: Int = 82,
    val scoreStatus: String = "Good",
    val totalImprovement: String = "+12",
    val improvementSubtitle: String = "vs last week",
    val consistencyDays: Int = 7,
    val consistencyStatus: String = "Great",
    val goalProgressPercent: Int = 75
)

data class ChartDataPoint(
    val dateLabel: String,
    val score: Int,
    val isHighlighted: Boolean = false
)

data class NutrientTrendItem(
    val id: String,
    val name: String,
    val avgValue: String,
    val changePercent: String,
    val isIncrease: Boolean,
    val sparklineScores: List<Float>
)

data class GoalProgressItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val current: Double,
    val target: Double,
    val unit: String,
    val percent: Int
)

data class AchievementItem(
    val id: String,
    val title: String,
    val streakText: String,
    val type: String
)

data class ProgressUiState(
    val activeGoal: NutritionGoal = GoalManager.getGoal(),
    val userName: String = if (GoalManager.getGoal() == NutritionGoal.WEIGHT_LOSS) "Gogo Ji" else "Alex",
    val timeFilter: String = "This Week",
    val chartFilter: String = "7 Days",
    val kpiSummary: KpiSummary = KpiSummary(),
    val chartPoints: List<ChartDataPoint> = listOf(
        ChartDataPoint("Aug 9", 72),
        ChartDataPoint("Aug 10", 76),
        ChartDataPoint("Aug 11", 79),
        ChartDataPoint("Aug 12", 75),
        ChartDataPoint("Aug 13", 92),
        ChartDataPoint("Aug 14", 78),
        ChartDataPoint("Aug 15", 87, isHighlighted = true)
    ),
    val nutrientTrends: List<NutrientTrendItem> = listOf(
        NutrientTrendItem("fiber", "Fiber", "28g avg", "15%", true, listOf(22f, 24f, 25f, 27f, 26f, 28f, 28f)),
        NutrientTrendItem("fruits", "Fruits &\nVegetables", "5.2 cups", "10%", true, listOf(4.2f, 4.5f, 4.8f, 5.0f, 4.9f, 5.1f, 5.2f)),
        NutrientTrendItem("grains", "Whole\nGrains", "3 servings", "8%", true, listOf(2.2f, 2.4f, 2.5f, 2.8f, 2.7f, 3.0f, 3.0f)),
        NutrientTrendItem("meat", "Processed\nMeat", "0.5 oz", "20%", false, listOf(1.2f, 1.0f, 0.9f, 0.7f, 0.8f, 0.6f, 0.5f)),
        NutrientTrendItem("water", "Water Intake", "1.6 L avg", "12%", true, listOf(1.2f, 1.3f, 1.4f, 1.5f, 1.4f, 1.6f, 1.6f))
    ),
    val goalProgressList: List<GoalProgressItem> = listOf(
        GoalProgressItem("g1", "Increase Fiber Intake", "30g daily goal", 28.0, 30.0, "g", 93),
        GoalProgressItem("g2", "Eat More Fruits & Vegetables", "5 cups daily goal", 5.2, 5.0, "cups", 104),
        GoalProgressItem("g3", "Whole Grains", "3 servings daily goal", 3.0, 3.0, "servings", 100),
        GoalProgressItem("g4", "Limit Processed Meat", "Less than 1 oz daily", 0.5, 1.0, "oz", 50)
    ),
    val achievements: List<AchievementItem> = listOf(
        AchievementItem("a1", "Fiber Master", "7 days in a row", "FIBER"),
        AchievementItem("a2", "Veggie Lover", "5 days in a row", "VEGGIE"),
        AchievementItem("a3", "Whole Grain\nChampion", "3 days in a row", "GRAIN"),
        AchievementItem("a4", "Hydration Hero", "7 days in a row", "HYDRATION"),
        AchievementItem("a5", "Consistency Star", "10 days in a row", "STAR")
    )
)

class ProgressViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProgressUiState())
    val uiState: StateFlow<ProgressUiState> = _uiState.asStateFlow()

    init {
        observeGoal()
    }

    private fun observeGoal() {
        viewModelScope.launch {
            GoalManager.selectedGoal.collect { goal ->
                val isWeightLoss = (goal == NutritionGoal.WEIGHT_LOSS)
                val name = if (isWeightLoss) "Gogo Ji" else "Alex"
                _uiState.update { current ->
                    current.copy(activeGoal = goal, userName = name)
                }
            }
        }
    }
}
