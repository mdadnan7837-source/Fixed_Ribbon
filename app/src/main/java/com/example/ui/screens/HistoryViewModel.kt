package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.NutritionGoal
import com.example.repository.DailyNutritionReportData
import com.example.repository.GoalManager
import com.example.repository.PermanentHistoryManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class WeeklySummary(
    val averageScore: Int = 82,
    val scoreStatus: String = "Good",
    val bestDayScore: Int = 92,
    val bestDayDate: String = "Aug 13, 2025",
    val totalDays: Int = 7,
    val timePeriodLabel: String = "This Week",
    val trendScores: List<Float> = listOf(68f, 74f, 72f, 78f, 92f, 78f, 82f)
)

data class DayNutrientProgress(
    val name: String,
    val current: Double,
    val target: Double,
    val unit: String,
    val isWarning: Boolean = false,
    val isAchieved: Boolean = false
)

data class DailyHistoryRecord(
    val id: String,
    val title: String, // "Today", "Yesterday", "Aug 13, 2025", etc.
    val dateSubtitle: String, // "Aug 15, 2025", etc.
    val score: Int,
    val fiber: DayNutrientProgress,
    val fruitsVeggies: DayNutrientProgress,
    val wholeGrains: DayNutrientProgress,
    val processedMeat: DayNutrientProgress,
    val aiSummary: String,
    val originalReport: DailyNutritionReportData? = null
)

data class HistoryUiState(
    val activeGoal: NutritionGoal = GoalManager.getGoal(),
    val userName: String = if (GoalManager.getGoal() == NutritionGoal.WEIGHT_LOSS) "Gogo Ji" else "Alex",
    val isLoading: Boolean = false,
    val weeklySummary: WeeklySummary = WeeklySummary(),
    val historyRecords: List<DailyHistoryRecord> = emptyList()
)

/**
 * Demo Cancer-Aware history data matching the APPROVED reference image exactly.
 * These are the four Cancer-Aware nutrition metrics called for by the reference
 * (Fiber, Fruits & Vegetables, Whole Grains, Processed Meat) — intentionally kept
 * separate from [DailyNutritionReportData], which models the unrelated
 * Weight-Loss "EOD report" (calories/protein/water/activity) used elsewhere.
 */
private fun buildDemoHistoryRecords(): List<DailyHistoryRecord> = listOf(
    DailyHistoryRecord(
        id = "history_today",
        title = "Today",
        dateSubtitle = "Aug 15, 2025",
        score = 87,
        fiber = DayNutrientProgress("Fiber", 28.0, 30.0, "g"),
        fruitsVeggies = DayNutrientProgress("Fruits & Vegetables", 5.2, 5.0, "cups"),
        wholeGrains = DayNutrientProgress("Whole Grains", 3.0, 3.0, "servings"),
        processedMeat = DayNutrientProgress("Processed Meat", 0.5, 1.0, "oz", isWarning = true),
        aiSummary = "Great job! You met your fiber goal and had an excellent variety of fruits and vegetables today."
    ),
    DailyHistoryRecord(
        id = "history_yesterday",
        title = "Yesterday",
        dateSubtitle = "Aug 14, 2025",
        score = 78,
        fiber = DayNutrientProgress("Fiber", 24.0, 30.0, "g"),
        fruitsVeggies = DayNutrientProgress("Fruits & Vegetables", 4.1, 5.0, "cups"),
        wholeGrains = DayNutrientProgress("Whole Grains", 2.0, 3.0, "servings"),
        processedMeat = DayNutrientProgress("Processed Meat", 0.8, 1.0, "oz", isWarning = true),
        aiSummary = "Good effort! Try adding more whole grains and reducing processed foods tomorrow."
    ),
    DailyHistoryRecord(
        id = "history_aug13",
        title = "Aug 13, 2025",
        dateSubtitle = "",
        score = 92,
        fiber = DayNutrientProgress("Fiber", 30.0, 30.0, "g", isAchieved = true),
        fruitsVeggies = DayNutrientProgress("Fruits & Vegetables", 5.6, 5.0, "cups", isAchieved = true),
        wholeGrains = DayNutrientProgress("Whole Grains", 3.0, 3.0, "servings", isAchieved = true),
        processedMeat = DayNutrientProgress("Processed Meat", 0.0, 1.0, "oz", isAchieved = true),
        aiSummary = "Excellent! You achieved all your nutrition goals. Keep up the amazing work!"
    ),
    // NOTE: the approved reference is cropped by the bottom nav bar for this row —
    // only the date and score (75/100) are visible; the four ring values below are
    // not legible in the source image. Reasonable values consistent with a 75 score
    // are used here as placeholders pending confirmation in the visual refinement pass.
    DailyHistoryRecord(
        id = "history_aug12",
        title = "Aug 12, 2025",
        dateSubtitle = "",
        score = 75,
        fiber = DayNutrientProgress("Fiber", 22.0, 30.0, "g"),
        fruitsVeggies = DayNutrientProgress("Fruits & Vegetables", 3.8, 5.0, "cups"),
        wholeGrains = DayNutrientProgress("Whole Grains", 2.0, 3.0, "servings"),
        processedMeat = DayNutrientProgress("Processed Meat", 0.9, 1.0, "oz", isWarning = true),
        aiSummary = "Solid day overall. A bit more fiber and fewer processed snacks will help you climb back toward your best days."
    )
)

class HistoryViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(
        HistoryUiState(historyRecords = buildDemoHistoryRecords())
    )
    val uiState: StateFlow<HistoryUiState> = _uiState.asStateFlow()

    init {
        observeGoal()
    }

    private fun observeGoal() {
        viewModelScope.launch {
            GoalManager.selectedGoal.collect { goal ->
                val isWeightLoss = (goal == NutritionGoal.WEIGHT_LOSS)
                val name = if (isWeightLoss) "Gogo Ji" else "Alex"
                _uiState.update { it.copy(activeGoal = goal, userName = name) }
            }
        }
    }

    /**
     * Builds a minimal [DailyNutritionReportData] snapshot from a history record so the
     * existing Daily Nutrition Report screen (a separate, already-implemented screen) can
     * still be opened from a history card, without altering that screen or its shared
     * repository defaults.
     */
    fun selectReport(record: DailyHistoryRecord) {
        val snapshot = DailyNutritionReportData(
            id = record.id,
            dateTitle = if (record.dateSubtitle.isNotEmpty()) "${record.title}, ${record.dateSubtitle}" else record.title,
            score = record.score,
            scoreLabel = if (record.score >= 90) "Optimal Day" else if (record.score >= 80) "Good Day" else "Steady Day",
            fiberCurrent = record.fiber.current.toInt(),
            fiberTarget = record.fiber.target.toInt()
        )
        PermanentHistoryManager.selectReport(snapshot)
    }
}
