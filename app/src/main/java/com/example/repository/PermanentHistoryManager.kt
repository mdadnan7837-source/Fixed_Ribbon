package com.example.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class DailyNutritionReportData(
    val id: String,
    val dateTitle: String = "Today, Aug 15, 2025",
    val score: Int = 82,
    val scoreLabel: String = "Good Day",
    val userName: String = "Gogo Ji",
    val caloriesCurrent: Int = 1650,
    val caloriesTarget: Int = 1800,
    val proteinCurrent: Int = 108,
    val proteinTarget: Int = 120,
    val fiberCurrent: Int = 28,
    val fiberTarget: Int = 30,
    val waterCurrent: Double = 2.2,
    val waterTarget: Double = 2.5,
    val activityCurrent: Int = 48,
    val activityTarget: Int = 60,
    val weightLossInsightTitle: String = "Great progress today, Gogo Ji!",
    val weightLossInsightBody: String = "You stayed close to your calorie target and reached most of your protein goal. Keeping this consistency will support sustainable weight loss.",
    val breakfastName: String = "Oatmeal with Berries",
    val breakfastScore: Int = 90,
    val lunchName: String = "Grilled Chicken Bowl",
    val lunchScore: Int = 88,
    val dinnerName: String = "Lentil Soup",
    val dinnerScore: Int = 92,
    val snackName: String = "Evening Snack",
    val snackScore: Int = 62,
    val bestMealName: String = "Grilled Chicken Bowl",
    val bestMealScore: Int = 88,
    val couldImproveName: String = "Evening Snack",
    val couldImproveScore: Int = 62,
    val dailyWins: List<String> = listOf(
        "Protein target almost reached",
        "Stayed within calorie target",
        "High-fiber choices",
        "7-day healthy meal streak"
    ),
    val improvements: List<Pair<String, String>> = listOf(
        "Increase water" to "About 300 ml remaining to reach today's goal.",
        "Increase protein" to "12 g remaining to reach today's target.",
        "Reduce high-calorie snacking" to "Consider a protein-rich snack tomorrow."
    ),
    val tomorrowFocus: List<String> = listOf(
        "Start the day with a protein-rich breakfast",
        "Add vegetables to lunch and dinner",
        "Keep water nearby throughout the day"
    ),
    val aiVideoRecap: String = "Today you made strong progress toward your weight-loss goal. You stayed close to your calorie target and had several high-protein meals."
)

object PermanentHistoryManager {
    val defaultTodayReport = DailyNutritionReportData(
        id = "report_today",
        dateTitle = "Today, Aug 15, 2025",
        score = 82,
        scoreLabel = "Good Day"
    )

    val defaultYesterdayReport = DailyNutritionReportData(
        id = "report_yesterday",
        dateTitle = "Yesterday, Aug 14, 2025",
        score = 78,
        scoreLabel = "Steady Day",
        caloriesCurrent = 1720,
        proteinCurrent = 98,
        fiberCurrent = 24,
        waterCurrent = 1.9,
        activityCurrent = 35,
        weightLossInsightTitle = "Good effort yesterday, Gogo Ji!",
        weightLossInsightBody = "You maintained a solid deficit. Focus on adding a little more water and protein to power through your workouts."
    )

    val defaultAug13Report = DailyNutritionReportData(
        id = "report_aug13",
        dateTitle = "Aug 13, 2025",
        score = 92,
        scoreLabel = "Optimal Day",
        caloriesCurrent = 1610,
        proteinCurrent = 118,
        fiberCurrent = 31,
        waterCurrent = 2.6,
        activityCurrent = 65,
        weightLossInsightTitle = "Outstanding Day, Gogo Ji!",
        weightLossInsightBody = "All key macro targets achieved! Your energy balance was ideal for accelerated weight loss."
    )

    private val _reports = MutableStateFlow<List<DailyNutritionReportData>>(
        listOf(defaultTodayReport, defaultYesterdayReport, defaultAug13Report)
    )
    val reports: StateFlow<List<DailyNutritionReportData>> = _reports.asStateFlow()

    private val _selectedReport = MutableStateFlow<DailyNutritionReportData>(defaultTodayReport)
    val selectedReport: StateFlow<DailyNutritionReportData> = _selectedReport.asStateFlow()

    fun selectReport(report: DailyNutritionReportData) {
        _selectedReport.value = report
    }

    fun getReportById(id: String): DailyNutritionReportData? {
        return _reports.value.find { it.id == id }
    }

    fun saveDailyReport(newReport: DailyNutritionReportData) {
        _reports.update { list ->
            // Replace if same ID exists, else add to front
            val filtered = list.filterNot { it.id == newReport.id }
            listOf(newReport) + filtered
        }
        _selectedReport.value = newReport
    }
}
