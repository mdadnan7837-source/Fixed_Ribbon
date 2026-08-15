package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChipType
import com.example.model.FoodAnalysisItem
import com.example.model.HealthInsightChip
import com.example.model.NutrientMetric
import com.example.model.StatusColorType
import com.example.navigation.Screen
import kotlinx.coroutines.delay

private val MedicalBlue = Color(0xFF2563EB)
private val HealthcareTeal = Color(0xFF0D9488)
private val AiLavender = Color(0xFFA855F7)
private val LightLavenderBg = Color(0xFFF3E8FF)
private val HealthyGreen = Color(0xFF22C55E)
private val GreenBg = Color(0xFFF0FDF4)
private val AppBackground = Color(0xFFF8FAFC)
private val PrimaryTextColor = Color(0xFF1F2937)
private val SecondaryTextColor = Color(0xFF6B7280)
private val CardBorderColor = Color(0xFFE5E7EB)

data class PresetFoodOption(
    val name: String,
    val emoji: String,
    val ingredients: String,
    val score: Int,
    val scoreStatus: String,
    val calories: String,
    val protein: String,
    val fiber: String,
    val carbs: String,
    val fat: String,
    val sodium: String,
    val insight: String
)

val samplePresetFoods = listOf(
    PresetFoodOption(
        name = "Quinoa Avocado Protein Bowl",
        emoji = "🥗",
        ingredients = "Quinoa, Grilled Chicken, Avocado, Spinach, Pumpkin Seeds, Lemon Dressing",
        score = 94,
        scoreStatus = "Excellent",
        calories = "480 kcal",
        protein = "38 g",
        fiber = "11 g",
        carbs = "42 g",
        fat = "16 g",
        sodium = "420 mg",
        insight = "Excellent meal for Gogo Ji! Rich in high-quality protein and gut-friendly fiber."
    ),
    PresetFoodOption(
        name = "Grilled Salmon & Asparagus",
        emoji = "🍱",
        ingredients = "Wild Salmon, Asparagus, Brown Rice, Olive Oil, Garlic",
        score = 91,
        scoreStatus = "Excellent",
        calories = "460 kcal",
        protein = "36 g",
        fiber = "7 g",
        carbs = "32 g",
        fat = "18 g",
        sodium = "380 mg",
        insight = "High in Omega-3 and protein. Perfect fit for Gogo Ji’s weight-loss plan."
    ),
    PresetFoodOption(
        name = "High-Protein Berry Smoothie Bowl",
        emoji = "🍧",
        ingredients = "Greek Yogurt, Whey Protein, Blueberries, Chia Seeds, Almond Butter",
        score = 89,
        scoreStatus = "Excellent",
        calories = "350 kcal",
        protein = "30 g",
        fiber = "9 g",
        carbs = "35 g",
        fat = "9 g",
        sodium = "180 mg",
        insight = "Great breakfast choice, Gogo Ji! Promotes satiety and long-lasting energy."
    )
)

@Composable
fun AiProcessingScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: FoodAnalysisViewModel
) {
    var selectedPreset by remember { mutableStateOf(samplePresetFoods.first()) }
    var currentStepIndex by remember { mutableIntStateOf(0) }
    var isProcessingComplete by remember { mutableStateOf(false) }

    // Pulse transition for AI scanning glow
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    // Simulate AI step-by-step analysis
    LaunchedEffect(selectedPreset) {
        currentStepIndex = 0
        isProcessingComplete = false
        delay(600)
        currentStepIndex = 1 // Food detected
        delay(700)
        currentStepIndex = 2 // Ingredients identified
        delay(800)
        currentStepIndex = 3 // Calculating nutrition
        delay(800)
        currentStepIndex = 4 // Generating insights
        isProcessingComplete = true
    }

    fun completeAndNavigate() {
        val newFoodItem = FoodAnalysisItem(
            id = System.currentTimeMillis().toString(),
            foodName = selectedPreset.name,
            ingredients = selectedPreset.ingredients,
            scanTimestamp = "Scanned Today at 12:45 PM",
            score = selectedPreset.score,
            scoreStatus = selectedPreset.scoreStatus,
            scoreColorHex = 0xFF22C55E,
            emoji = selectedPreset.emoji,
            healthInsights = listOf(
                HealthInsightChip("High in Protein", ChipType.BLUE_TAG),
                HealthInsightChip("High in Fiber", ChipType.GREEN_LEAF),
                HealthInsightChip("Low Sodium", ChipType.TEAL_SHIELD),
                HealthInsightChip("Heart Healthy", ChipType.HEART_RED)
            ),
            servingLabel = "per 1 serving",
            nutrientMetrics = listOf(
                NutrientMetric("Calories", selectedPreset.calories, "Good", StatusColorType.GREEN),
                NutrientMetric("Protein", selectedPreset.protein, "High", StatusColorType.GREEN),
                NutrientMetric("Fiber", selectedPreset.fiber, "High", StatusColorType.GREEN),
                NutrientMetric("Carbohydrates", selectedPreset.carbs, "Moderate", StatusColorType.ORANGE),
                NutrientMetric("Total Fat", selectedPreset.fat, "Moderate", StatusColorType.ORANGE),
                NutrientMetric("Sodium", selectedPreset.sodium, "Low", StatusColorType.GREEN)
            ),
            aiNutritionInsight = selectedPreset.insight,
            aiRecommendation = "Pair with fresh water and green leafy salad for optimal metabolic balance."
        )

        // Automatically add to Temporary Food Analysis History
        viewModel.addScannedFood(newFoodItem)
        onNavigate(Screen.FoodAnalysisResult.route)
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier.size(36.dp).testTag("btn_ai_processing_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryTextColor
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "AI Meal Scanner",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PrimaryTextColor
                        )
                    )
                    Text(
                        text = "Analyzing nutrition for Gogo Ji",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = SecondaryTextColor
                        )
                    )
                }
            }
        },
        containerColor = AppBackground,
        modifier = Modifier.testTag("ai_processing_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // Animated Food Image Frame with AI Scanning Glow
                Box(
                    modifier = Modifier
                        .size(200.dp)
                        .scale(if (isProcessingComplete) 1f else pulseScale)
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            Brush.linearGradient(
                                listOf(MedicalBlue.copy(alpha = 0.15f), HealthcareTeal.copy(alpha = 0.15f), AiLavender.copy(alpha = 0.15f))
                            )
                        )
                        .border(
                            width = 2.dp,
                            brush = Brush.sweepGradient(
                                listOf(MedicalBlue, HealthcareTeal, AiLavender, MedicalBlue)
                            ),
                            shape = RoundedCornerShape(24.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = selectedPreset.emoji, fontSize = 90.sp)

                    // Scanner overlay grid line
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Brush.horizontalGradient(listOf(MedicalBlue, HealthcareTeal, AiLavender)))
                            .align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = if (isProcessingComplete) "Analysis Complete!" else "Analyzing your meal…",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "AI is identifying ingredients and calculating nutrition.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SecondaryTextColor,
                        fontSize = 13.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(24.dp))

                // AI Processing Checklist Steps Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        ProcessingStepRow(
                            label = "Food detected",
                            isDone = currentStepIndex >= 1,
                            isActive = currentStepIndex == 0,
                            iconColor = HealthyGreen
                        )

                        ProcessingStepRow(
                            label = "Ingredients identified",
                            isDone = currentStepIndex >= 2,
                            isActive = currentStepIndex == 1,
                            iconColor = HealthcareTeal
                        )

                        ProcessingStepRow(
                            label = "Calculating nutrition",
                            isDone = currentStepIndex >= 3,
                            isActive = currentStepIndex == 2,
                            iconColor = MedicalBlue
                        )

                        ProcessingStepRow(
                            label = "Generating insights",
                            isDone = currentStepIndex >= 4,
                            isActive = currentStepIndex == 3,
                            iconColor = AiLavender
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Preset Meal Selector for Prototyping
                Text(
                    text = "Try Scanning Different Meals:",
                    style = MaterialTheme.typography.labelMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = SecondaryTextColor,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(samplePresetFoods) { preset ->
                        val isSelected = preset == selectedPreset
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = if (isSelected) LightLavenderBg else Color.White,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) AiLavender else CardBorderColor
                            ),
                            modifier = Modifier.clickable {
                                selectedPreset = preset
                            }
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = preset.emoji, fontSize = 16.sp)
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = preset.name,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) AiLavender else PrimaryTextColor,
                                        fontSize = 11.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Bottom Action Button
            Button(
                onClick = { completeAndNavigate() },
                enabled = isProcessingComplete,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MedicalBlue,
                    disabledContainerColor = MedicalBlue.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .testTag("btn_view_analysis_result")
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (!isProcessingComplete) {
                        CircularProgressIndicator(
                            color = Color.White,
                            strokeWidth = 2.5.dp,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Analyzing Meal...",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "View Food Analysis Result",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ProcessingStepRow(
    label: String,
    isDone: Boolean,
    isActive: Boolean,
    iconColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
                shape = CircleShape,
                color = if (isDone) iconColor.copy(alpha = 0.15f) else Color(0xFFF3F4F6),
                modifier = Modifier.size(28.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isDone) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = iconColor,
                            modifier = Modifier.size(18.dp)
                        )
                    } else if (isActive) {
                        CircularProgressIndicator(
                            color = iconColor,
                            strokeWidth = 2.dp,
                            modifier = Modifier.size(14.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .clip(CircleShape)
                                .background(SecondaryTextColor.copy(alpha = 0.4f))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = if (isDone || isActive) FontWeight.Bold else FontWeight.Normal,
                    color = if (isDone || isActive) PrimaryTextColor else SecondaryTextColor,
                    fontSize = 14.sp
                )
            )
        }

        if (isDone) {
            Text(
                text = "✓ Done",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = HealthyGreen,
                    fontSize = 12.sp
                )
            )
        }
    }
}
