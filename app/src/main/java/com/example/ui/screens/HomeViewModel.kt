package com.example.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.NutritionGoal
import com.example.repository.GoalManager
import com.example.repository.NutritionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class NutrientProgress(
    val name: String,
    val current: Double,
    val target: Double,
    val unit: String,
    val isWarning: Boolean = false,
    val isAchieved: Boolean = false
)

data class RecentFoodAnalysisData(
    val title: String = "Bacon (Processed Meat)",
    val score: Int = 45,
    val alertMessage: String = "Processed meat detected",
    val description: String = "High in sodium and preservatives."
)

data class HomeMealItem(
    val id: String,
    val category: String, // Breakfast, Lunch, Dinner, Snack
    val name: String,
    val calories: Int
)

data class HomeUiState(
    val isLoading: Boolean = false,
    val userName: String = "Alex",
    val greetingMessage: String = "Good Morning, Alex! 👋",
    val subtitleMessage: String = "You're one step closer to your healthiest self.",
    val notificationCount: Int = 3,
    val activeGoal: NutritionGoal = NutritionGoal.WEIGHT_LOSS,
    val currentGoalTitle: String = "Weight Loss",
    val currentGoalDescription: String = "Create a sustainable calorie deficit while nourishing your body.",
    val progressPercentage: Int = 69,
    val caloriesCurrent: Int = 1250,
    val caloriesTarget: Int = 1800,
    val proteinCurrent: Int = 85,
    val proteinTarget: Int = 120,
    val waterCurrent: Double = 1.6,
    val waterTarget: Double = 2.5,
    val activityCurrent: Int = 30,
    val activityTarget: Int = 60,
    val fiberToday: Int = 18,
    val caloriesRemaining: Int = 550,
    val fiberProgress: NutrientProgress = NutrientProgress("Calories", 1250.0, 1800.0, "kcal"),
    val fruitsVeggiesProgress: NutrientProgress = NutrientProgress("Protein", 85.0, 120.0, "g"),
    val wholeGrainsProgress: NutrientProgress = NutrientProgress("Water", 1.6, 2.5, "L"),
    val processedMeatProgress: NutrientProgress = NutrientProgress("Activity", 30.0, 60.0, "min"),
    val recentAnalysis: RecentFoodAnalysisData = RecentFoodAnalysisData(
        title = "Grilled Chicken Bowl",
        score = 85,
        alertMessage = "Great choice! High in protein and fiber.",
        description = "Consider adding more leafy greens tomorrow."
    ),
    val nutritionScore: Int = 85,
    val nutritionScoreStatus: String = "Great",
    val healthyStreakDays: Int = 7,
    val waterIntakeLiters: Double = 1.6,
    val waterTargetLiters: Double = 2.5,
    val meals: List<HomeMealItem> = listOf(
        HomeMealItem("1", "Breakfast", "Oatmeal with Fruits & Nuts", 350),
        HomeMealItem("2", "Lunch", "Grilled Chicken Salad", 450),
        HomeMealItem("3", "Dinner", "Salmon with Steamed Veggies", 520),
        HomeMealItem("4", "Snack", "Greek Yogurt with Berries", 0)
    ),
    val aiInsightText: String = "You're doing great! Keep your protein intake consistent and try a 20-minute evening walk to boost your metabolism and sleep quality.",
    val errorMessage: String? = null
)

class HomeViewModel(
    private val nutritionRepository: NutritionRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadHomeData()
        observeGoalChanges()
    }

    private fun observeGoalChanges() {
        viewModelScope.launch {
            GoalManager.selectedGoal.collect { goal ->
                applyGoalData(goal)
            }
        }
    }

    fun selectGoal(goal: NutritionGoal) {
        GoalManager.setGoal(goal)
    }

    private fun applyGoalData(goal: NutritionGoal) {
        val (fProg, fvProg, wgProg, pmProg, insight, score) = when (goal) {
            NutritionGoal.CANCER_AWARE -> Tuple6(
                NutrientProgress("Fiber", 28.0, 30.0, "g", isAchieved = true),
                NutrientProgress("Fruits & Vegetables", 5.2, 5.0, "cups", isAchieved = true),
                NutrientProgress("Whole Grains", 2.0, 3.0, "servings"),
                NutrientProgress("Processed Meat", 0.5, 1.0, "oz", isWarning = true),
                "Your fiber intake is improving! Adding more berries and leafy greens will further support your immunity.",
                82
            )
            NutritionGoal.WEIGHT_LOSS -> Tuple6(
                NutrientProgress("Daily Calories", 1450.0, 1800.0, "kcal", isAchieved = true),
                NutrientProgress("Protein Satiety", 95.0, 110.0, "g"),
                NutrientProgress("Dietary Fiber", 25.0, 30.0, "g"),
                NutrientProgress("Added Sugars", 12.0, 25.0, "g", isWarning = false),
                "Great caloric discipline today! High protein at lunch maintains optimal satiety and metabolic rate.",
                85
            )
            NutritionGoal.MUSCLE_GAIN -> Tuple6(
                NutrientProgress("Protein Intake", 140.0, 160.0, "g"),
                NutrientProgress("Daily Calories", 2450.0, 2700.0, "kcal"),
                NutrientProgress("Complex Carbs", 260.0, 300.0, "g"),
                NutrientProgress("Healthy Fats", 55.0, 70.0, "g"),
                "Excellent protein distribution! Consuming 25-30g protein within 1 hour post-workout maximizes muscle synthesis.",
                88
            )
            NutritionGoal.HEART_HEALTH -> Tuple6(
                NutrientProgress("Sodium Limit", 1200.0, 1500.0, "mg", isAchieved = true),
                NutrientProgress("Soluble Fiber", 12.0, 15.0, "g"),
                NutrientProgress("Omega-3 Fats", 2.2, 2.5, "g"),
                NutrientProgress("Saturated Fat", 11.0, 15.0, "g", isWarning = false),
                "Sodium intake is well managed today! Oats and omega-3 rich salmon help maintain cardiovascular health.",
                86
            )
            NutritionGoal.DIABETES_FRIENDLY -> Tuple6(
                NutrientProgress("Net Carbs", 85.0, 120.0, "g", isAchieved = true),
                NutrientProgress("Dietary Fiber", 29.0, 32.0, "g"),
                NutrientProgress("Glycemic Avg", 42.0, 50.0, "GI", isAchieved = true),
                NutrientProgress("Glucose Stability", 92.0, 100.0, "%", isAchieved = true),
                "Outstanding glycemic stability! Your balanced low-GI meals prevent blood sugar spikes throughout the day.",
                90
            )
        }

        val isWeightLoss = (goal == NutritionGoal.WEIGHT_LOSS)
        val name = if (isWeightLoss) "Gogo Ji" else "Alex"
        val greeting = if (isWeightLoss) "Good morning, Gogo Ji" else "Good Morning, Alex! 👋"
        val subtitle = if (isWeightLoss) "Gogo Ji, you're one step closer to your healthiest self." else "You're one step closer to your healthiest self."

        _uiState.update { current ->
            current.copy(
                userName = name,
                greetingMessage = greeting,
                subtitleMessage = subtitle,
                activeGoal = goal,
                currentGoalTitle = goal.title,
                currentGoalDescription = goal.description,
                fiberProgress = fProg,
                fruitsVeggiesProgress = fvProg,
                wholeGrainsProgress = wgProg,
                processedMeatProgress = pmProg,
                aiInsightText = insight,
                nutritionScore = score
            )
        }
    }

    fun loadHomeData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = false) }
        }
    }

    fun addWaterIntake(amountLiters: Double = 0.25) {
        _uiState.update { current ->
            val updated = (current.waterIntakeLiters + amountLiters).coerceAtMost(current.waterTargetLiters)
            current.copy(waterIntakeLiters = (updated * 10).toInt() / 10.0)
        }
    }
}

private data class Tuple6<A, B, C, D, E, F>(
    val a: A, val b: B, val c: C, val d: D, val e: E, val f: F
)

