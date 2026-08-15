package com.example.model

enum class ChipType {
    GREEN_LEAF,
    TEAL_SHIELD,
    GREEN_CHECK,
    RED_FIRE,
    ORANGE_DROP,
    RED_WARNING,
    PURPLE_CLOCK,
    BLUE_TAG,
    HEART_RED
}

data class HealthInsightChip(
    val text: String,
    val type: ChipType
)

data class NutrientMetric(
    val label: String,
    val value: String,
    val status: String? = null, // "High", "Low", "Moderate", "Good", "Excellent"
    val statusColorType: StatusColorType = StatusColorType.GREEN
)

enum class StatusColorType {
    GREEN,
    ORANGE,
    RED,
    BLUE
}

data class AiInsightDetail(
    val title: String,
    val description: String,
    val value: String,
    val iconType: FocusIconType = FocusIconType.GREEN_LEAF
)

enum class FocusIconType {
    CYAN_SHIELD,
    PURPLE_LEAF,
    GREEN_LEAF
}

data class FoodAnalysisItem(
    val id: String,
    val foodName: String,
    val ingredients: String,
    val scanTimestamp: String,
    val score: Int,
    val scoreStatus: String,
    val scoreColorHex: Long = 0xFF22C55E,
    val emoji: String,
    val healthInsights: List<HealthInsightChip> = emptyList(),
    val servingLabel: String = "per 1 serving",
    val nutrientMetrics: List<NutrientMetric> = emptyList(),
    val aiNutritionInsight: String? = null,
    val aiRecommendation: String? = null,
    val highSodiumWarning: String? = null
)

val defaultFoodAnalysisList = listOf(
    FoodAnalysisItem(
        id = "1",
        foodName = "Grilled Chicken Bowl",
        ingredients = "Chicken, Brown Rice, Broccoli, Avocado, Tomato",
        scanTimestamp = "Scanned at 9:41 AM",
        score = 88,
        scoreStatus = "Excellent",
        scoreColorHex = 0xFF22C55E,
        emoji = "🥗",
        healthInsights = listOf(
            HealthInsightChip("High in Protein", ChipType.BLUE_TAG),
            HealthInsightChip("High in Fiber", ChipType.GREEN_LEAF),
            HealthInsightChip("Balanced", ChipType.BLUE_TAG),
            HealthInsightChip("Heart Healthy", ChipType.HEART_RED)
        ),
        servingLabel = "per 1 serving",
        nutrientMetrics = listOf(
            NutrientMetric("Calories", "520 kcal", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Protein", "42 g", "High", StatusColorType.GREEN),
            NutrientMetric("Fiber", "9 g", "High", StatusColorType.GREEN),
            NutrientMetric("Total Fat", "18 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Saturated Fat", "4 g", "Low", StatusColorType.GREEN),
            NutrientMetric("Carbohydrates", "45 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Sodium", "620 mg", "Moderate", StatusColorType.ORANGE)
        ),
        aiNutritionInsight = "Great choice! This meal is high in protein and fiber and supports your weight-loss goal.",
        aiRecommendation = "Pair this meal with vegetables and water for a more balanced meal."
    ),
    FoodAnalysisItem(
        id = "2",
        foodName = "Salmon & Vegetables",
        ingredients = "Salmon, Quinoa, Asparagus, Bell Peppers, Olive Oil",
        scanTimestamp = "Scanned at 8:15 AM",
        score = 92,
        scoreStatus = "Excellent",
        scoreColorHex = 0xFF22C55E,
        emoji = "🍱",
        healthInsights = listOf(
            HealthInsightChip("High in Protein", ChipType.BLUE_TAG),
            HealthInsightChip("Rich in Omega-3", ChipType.TEAL_SHIELD),
            HealthInsightChip("High in Fiber", ChipType.GREEN_LEAF),
            HealthInsightChip("Heart Healthy", ChipType.HEART_RED)
        ),
        servingLabel = "per 1 serving",
        nutrientMetrics = listOf(
            NutrientMetric("Calories", "450 kcal", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Protein", "38 g", "High", StatusColorType.GREEN),
            NutrientMetric("Fiber", "8 g", "High", StatusColorType.GREEN),
            NutrientMetric("Total Fat", "16 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Saturated Fat", "3 g", "Low", StatusColorType.GREEN),
            NutrientMetric("Carbohydrates", "30 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Sodium", "480 mg", "Low", StatusColorType.GREEN)
        ),
        aiNutritionInsight = "Excellent source of protein and omega-3 fatty acids. Supports heart health and muscle recovery.",
        aiRecommendation = "Add a side of leafy greens or a light soup for extra fiber and nutrients."
    ),
    FoodAnalysisItem(
        id = "3",
        foodName = "Oatmeal with Berries",
        ingredients = "Oats, Blueberries, Strawberries, Chia Seeds, Almonds",
        scanTimestamp = "Scanned at 7:45 AM",
        score = 90,
        scoreStatus = "Excellent",
        scoreColorHex = 0xFF22C55E,
        emoji = "🥣",
        healthInsights = listOf(
            HealthInsightChip("High in Fiber", ChipType.GREEN_LEAF),
            HealthInsightChip("Whole Grains", ChipType.ORANGE_DROP),
            HealthInsightChip("Low in Saturated Fat", ChipType.TEAL_SHIELD),
            HealthInsightChip("Antioxidant Rich", ChipType.BLUE_TAG)
        ),
        servingLabel = "per 1 serving",
        nutrientMetrics = listOf(
            NutrientMetric("Calories", "420 kcal", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Protein", "12 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Fiber", "11 g", "High", StatusColorType.GREEN),
            NutrientMetric("Total Fat", "8 g", "Low", StatusColorType.GREEN),
            NutrientMetric("Saturated Fat", "1.5 g", "Low", StatusColorType.GREEN),
            NutrientMetric("Carbohydrates", "68 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Sodium", "120 mg", "Low", StatusColorType.GREEN)
        ),
        aiNutritionInsight = "Great source of fiber and antioxidants. Helps keep you full and supports healthy digestion.",
        aiRecommendation = "Add a scoop of protein powder or Greek yogurt for extra protein."
    ),
    FoodAnalysisItem(
        id = "4",
        foodName = "Pizza",
        ingredients = "Refined Flour, Cheese, Tomato Sauce, Pepperoni, Olive Oil",
        scanTimestamp = "Scanned at 6:30 PM",
        score = 55,
        scoreStatus = "Average",
        scoreColorHex = 0xFFF59E0B,
        emoji = "🍕",
        healthInsights = listOf(
            HealthInsightChip("Higher Calories", ChipType.RED_FIRE),
            HealthInsightChip("Higher Saturated Fat", ChipType.RED_FIRE),
            HealthInsightChip("Higher Sodium", ChipType.RED_WARNING),
            HealthInsightChip("Low Fiber", ChipType.GREEN_LEAF)
        ),
        servingLabel = "per 1 serving",
        nutrientMetrics = listOf(
            NutrientMetric("Calories", "320 kcal", "High", StatusColorType.RED),
            NutrientMetric("Protein", "12 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Fiber", "2.1 g", "Low", StatusColorType.RED),
            NutrientMetric("Total Fat", "14 g", "High", StatusColorType.RED),
            NutrientMetric("Saturated Fat", "6 g", "High", StatusColorType.RED),
            NutrientMetric("Carbohydrates", "38 g", "Moderate", StatusColorType.ORANGE),
            NutrientMetric("Sodium", "980 mg", "High", StatusColorType.RED)
        ),
        aiNutritionInsight = "This meal is higher in calories, saturated fat and sodium. Enjoy in moderation.",
        aiRecommendation = "Try whole-grain crust, more vegetables and lean protein for a healthier option."
    )
)

data class FoodAnalysisData(
    val foodName: String = "Lentil Soup",
    val ingredients: String = "Lentils, Carrots, Celery, Spinach, Onion, Tomato, Garlic, Herbs, Olive Oil",
    val scanTimestamp: String = "Scanned Today, 9:41 AM",
    val score: Int = 92,
    val scoreStatus: String = "Excellent",
    val greatChoiceTitle: String = "Great Choice!",
    val greatChoiceSubtitle: String = "This meal provides a strong balance of protein, fiber and essential nutrients to support your nutrition goals.",
    val healthInsights: List<HealthInsightChip> = listOf(
        HealthInsightChip("High in Fiber", ChipType.GREEN_LEAF),
        HealthInsightChip("High in Plant Protein", ChipType.GREEN_LEAF),
        HealthInsightChip("Rich in Antioxidants", ChipType.TEAL_SHIELD),
        HealthInsightChip("Low in Saturated Fat", ChipType.TEAL_SHIELD),
        HealthInsightChip("Heart Healthy", ChipType.HEART_RED),
        HealthInsightChip("Anti-Inflammatory", ChipType.GREEN_LEAF)
    ),
    val nutrientMetrics: List<NutrientMetric> = listOf(
        NutrientMetric("Calories", "245 kcal", "Good", StatusColorType.GREEN),
        NutrientMetric("Protein", "14.2 g", "Good", StatusColorType.GREEN),
        NutrientMetric("Fiber", "9.6 g", "Excellent", StatusColorType.GREEN),
        NutrientMetric("Total Fat", "6.1 g", "Good", StatusColorType.GREEN),
        NutrientMetric("Saturated Fat", "0.7 g", "Low", StatusColorType.GREEN),
        NutrientMetric("Carbohydrates", "32.5 g", "Good", StatusColorType.GREEN),
        NutrientMetric("Sodium", "890 mg", "High", StatusColorType.RED),
        NutrientMetric("Added Sugar", "1.2 g", "Low", StatusColorType.GREEN),
        NutrientMetric("Cholesterol", "0 mg", "Excellent", StatusColorType.GREEN),
        NutrientMetric("Potassium", "680 mg", "Good", StatusColorType.GREEN)
    ),
    val highSodiumTitle: String = "High Sodium",
    val highSodiumWarning: String = "This meal contains a high amount of sodium. Consider using less salt or choosing a lower-sodium alternative.",
    val aiInsightsList: List<AiInsightDetail> = listOf(
        AiInsightDetail(
            title = "High in Fiber",
            description = "Supports healthy digestion, helps you feel fuller for longer, and aids in maintaining a healthy weight.",
            value = "9.6 g",
            iconType = FocusIconType.GREEN_LEAF
        ),
        AiInsightDetail(
            title = "Good Source of Plant Protein",
            description = "Helps build and repair muscle while supporting satiety and weight management.",
            value = "14.2 g",
            iconType = FocusIconType.GREEN_LEAF
        ),
        AiInsightDetail(
            title = "Rich in Antioxidants",
            description = "Helps protect cells from oxidative stress and supports overall wellness.",
            value = "Excellent",
            iconType = FocusIconType.CYAN_SHIELD
        )
    ),
    val aiRecommendation: String = "Pair this soup with a side of quinoa or whole-grain bread to add complex carbs and keep you satisfied longer."
)


