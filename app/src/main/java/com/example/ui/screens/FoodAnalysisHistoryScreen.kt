package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.LocalFlorist
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Shield
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.ChipType
import com.example.model.FoodAnalysisItem
import com.example.model.StatusColorType
import com.example.navigation.Screen

private val PrimaryBlue = Color(0xFF2563EB)
private val HealthcareTeal = Color(0xFF0D9488)
private val AiLavender = Color(0xFFA855F7)
private val LightLavenderBg = Color(0xFFF3E8FF)
private val LavenderBorder = Color(0xFFE9D5FF)
private val HealthyGreen = Color(0xFF22C55E)
private val GreenBg = Color(0xFFF0FDF4)
private val GreenBorder = Color(0xFFBBF7D0)
private val MedicalAmber = Color(0xFFF59E0B)
private val AmberBg = Color(0xFFFFFBEB)
private val AmberBorder = Color(0xFFFDE68A)
private val ClinicalRed = Color(0xFFEF4444)
private val RedBg = Color(0xFFFEF2F2)
private val AppBackground = Color(0xFFF8FAFC)
private val PrimaryTextColor = Color(0xFF1F2937)
private val SecondaryTextColor = Color(0xFF6B7280)
private val CardBorderColor = Color(0xFFE5E7EB)

// Cancer-Aware-only accent colors (per official Cancer-Aware design spec).
// Applied only when activeGoal == CANCER_AWARE; every other goal keeps the
// existing blue-accented header/callout on this screen untouched.
private val CancerAccent = Color(0xFF7C3AED)
private val CancerLavenderBg = Color(0xFFF3E8FF)

// Cancer-Awareness ribbon: two crossed loops meeting at a point — matches the
// ribbon used on the Cancer-Aware Home banner, Profile badge, History header,
// and Food Analysis Result header, reused here at header-icon size.
@Composable
private fun CancerAwareRibbonCanvasHistoryFood(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w * 0.5f
        val cy = h * 0.42f
        val loopW = w * 0.30f
        val loopH = h * 0.34f
        val tailLen = h * 0.30f
        val tailW = w * 0.11f

        val leftLoop = Path().apply {
            moveTo(cx, cy)
            cubicTo(cx - loopW, cy - loopH * 0.9f, cx - loopW * 1.15f, cy + loopH * 0.55f, cx, cy)
            close()
        }
        val rightLoop = Path().apply {
            moveTo(cx, cy)
            cubicTo(cx + loopW, cy - loopH * 0.9f, cx + loopW * 1.15f, cy + loopH * 0.55f, cx, cy)
            close()
        }
        val tail = Path().apply {
            moveTo(cx - tailW * 0.5f, cy)
            lineTo(cx + tailW * 0.5f, cy)
            lineTo(cx + tailW * 0.35f, cy + tailLen)
            lineTo(cx, cy + tailLen - tailW * 0.4f)
            lineTo(cx - tailW * 0.35f, cy + tailLen)
            close()
        }

        drawPath(tail, color = CancerAccent.copy(alpha = 0.85f))
        drawPath(leftLoop, color = CancerAccent)
        drawPath(rightLoop, color = CancerAccent.copy(alpha = 0.92f))
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodAnalysisHistoryScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: FoodAnalysisViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val foodList = uiState.items
    val isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBack,
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("btn_history_back")
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = PrimaryTextColor
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        val isWeightLoss = (uiState.activeGoal == com.example.model.NutritionGoal.WEIGHT_LOSS)
                        val historyTitle = if (isWeightLoss) "Gogo Ji’s Food History" else "Food Analysis History"
                        val historySubtitle = "Temporary Daily Storage"

                        Text(
                            text = historyTitle,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = PrimaryTextColor
                            )
                        )
                        if (isCancerAware) {
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = CircleShape,
                                color = CancerLavenderBg,
                                modifier = Modifier.size(18.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    CancerAwareRibbonCanvasHistoryFood(modifier = Modifier.size(11.dp))
                                }
                            }
                            Spacer(modifier = Modifier.height(2.dp))
                        }
                        Text(
                            text = historySubtitle,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = PrimaryBlue,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                    }

                    IconButton(
                        onClick = { /* Calendar Picker */ },
                        modifier = Modifier
                            .size(36.dp)
                            .testTag("btn_calendar")
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = "Calendar",
                            tint = PrimaryTextColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        },
        containerColor = AppBackground,
        modifier = Modifier.testTag("food_analysis_history_screen")
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Temporary History Explanatory Banner
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFEFF6FF)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue.copy(alpha = 0.2f)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Info,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "This is your temporary daily food storage. Scanned meals are automatically added here. At the end of the day, these analyses are saved to Permanent History.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = PrimaryTextColor,
                                lineHeight = 15.sp
                            )
                        )
                    }
                }
            }

            // End-of-Day AI Processing Callout
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = if (isCancerAware) CancerAccent else PrimaryBlue,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.EodProcessing.route) }
                        .testTag("btn_end_of_day_report")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.AutoAwesome,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(22.dp)
                            )
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "End-of-Day AI Processing",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color.White,
                                        fontSize = 15.sp
                                    )
                                )
                                Text(
                                    text = "Generate Daily Report & AI Video Recap",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = Color.White.copy(alpha = 0.85f),
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }

                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = Color.White.copy(alpha = 0.2f)
                        ) {
                            Text(
                                text = "Start ▶",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    fontSize = 11.sp
                                ),
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            // Top Date & Stats Bar
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CalendarToday,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "Today",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryTextColor
                            )
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = PrimaryBlue.copy(alpha = 0.1f)
                    ) {
                        Text(
                            text = "${foodList.size} Foods Analyzed",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = PrimaryBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // List of Scanned Foods
            if (foodList.isEmpty()) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp).fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(text = "🥗", fontSize = 48.sp)
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = "No Scanned Foods Today",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryTextColor
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Tap 'Scan Food' on the home screen to analyze your meal.",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SecondaryTextColor,
                                    fontSize = 12.sp,
                                    textAlign = TextAlign.Center
                                )
                            )
                        }
                    }
                }
            } else {
                items(foodList, key = { it.id }) { foodItem ->
                    FoodAnalysisHistoryCard(
                        foodItem = foodItem,
                        onViewFullNutrition = {
                            viewModel.selectFoodItem(foodItem)
                            onNavigate(Screen.FoodAnalysisResult.route)
                        },
                        onDelete = {
                            viewModel.deleteFoodItem(foodItem.id)
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FoodAnalysisHistoryCard(
    foodItem: FoodAnalysisItem,
    onViewFullNutrition: () -> Unit,
    onDelete: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Food Summary Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Food Image Box
                Box(
                    modifier = Modifier
                        .size(90.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFFEF3C7)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = foodItem.emoji, fontSize = 42.sp)
                }

                Spacer(modifier = Modifier.width(14.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = foodItem.foodName,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,
                                    color = PrimaryTextColor
                                )
                            )
                            Spacer(modifier = Modifier.height(3.dp))
                            Text(
                                text = foodItem.ingredients,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 11.sp,
                                    color = SecondaryTextColor,
                                    lineHeight = 15.sp
                                ),
                                maxLines = 2
                            )
                        }

                        Spacer(modifier = Modifier.width(8.dp))

                        // Score Ring
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            val scoreColor = Color(foodItem.scoreColorHex)
                            Box(
                                modifier = Modifier.size(54.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    progress = { (foodItem.score / 100f).coerceIn(0f, 1f) },
                                    modifier = Modifier.fillMaxSize(),
                                    color = scoreColor,
                                    trackColor = scoreColor.copy(alpha = 0.15f),
                                    strokeWidth = 4.5.dp,
                                    strokeCap = StrokeCap.Round
                                )
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = "${foodItem.score}",
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 17.sp,
                                            color = PrimaryTextColor
                                        )
                                    )
                                    Text(
                                        text = "/100",
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 8.sp,
                                            color = SecondaryTextColor
                                        )
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            Surface(
                                shape = RoundedCornerShape(4.dp),
                                color = scoreColor
                            ) {
                                Text(
                                    text = foodItem.scoreStatus,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 9.sp
                                    ),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = null,
                            tint = SecondaryTextColor,
                            modifier = Modifier.size(13.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = foodItem.scanTimestamp,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = SecondaryTextColor
                            )
                        )
                    }
                }
            }

            // Health Insights Tags Row
            if (foodItem.healthInsights.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    foodItem.healthInsights.forEach { chip ->
                        val (bgCol, textCol, icon) = when (chip.type) {
                            ChipType.GREEN_LEAF -> Triple(GreenBg, HealthyGreen, Icons.Outlined.LocalFlorist)
                            ChipType.TEAL_SHIELD -> Triple(Color(0xFFF0FDFA), HealthcareTeal, Icons.Outlined.Shield)
                            ChipType.GREEN_CHECK -> Triple(GreenBg, HealthyGreen, Icons.Outlined.LocalFlorist)
                            ChipType.RED_FIRE -> Triple(RedBg, ClinicalRed, Icons.Default.LocalFireDepartment)
                            ChipType.ORANGE_DROP -> Triple(AmberBg, MedicalAmber, Icons.Default.WaterDrop)
                            ChipType.RED_WARNING -> Triple(RedBg, ClinicalRed, Icons.Default.Warning)
                            ChipType.PURPLE_CLOCK -> Triple(LightLavenderBg, AiLavender, Icons.Outlined.AccessTime)
                            ChipType.BLUE_TAG -> Triple(Color(0xFFEFF6FF), PrimaryBlue, Icons.Outlined.Shield)
                            ChipType.HEART_RED -> Triple(RedBg, ClinicalRed, Icons.Default.Favorite)
                        }

                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = bgCol,
                            border = androidx.compose.foundation.BorderStroke(1.dp, textCol.copy(alpha = 0.2f))
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 9.dp, vertical = 5.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = null,
                                    tint = textCol,
                                    modifier = Modifier.size(13.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = chip.text,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = textCol
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // Nutrient Breakdown Grid Cards Row
            if (foodItem.nutrientMetrics.isNotEmpty()) {
                FlowRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp),
                    maxItemsInEachRow = 6
                ) {
                    foodItem.nutrientMetrics.forEach { metric ->
                        Card(
                            shape = RoundedCornerShape(10.dp),
                            colors = CardDefaults.cardColors(containerColor = AppBackground),
                            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                            modifier = Modifier.weight(1f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp, horizontal = 4.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(2.dp)
                            ) {
                                Text(
                                    text = metric.label,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        fontSize = 9.sp,
                                        color = SecondaryTextColor
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                                Text(
                                    text = metric.value,
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp,
                                        color = PrimaryTextColor
                                    ),
                                    textAlign = TextAlign.Center,
                                    maxLines = 1
                                )
                                if (metric.status != null) {
                                    val (bg, txt) = when (metric.statusColorType) {
                                        StatusColorType.GREEN -> Pair(Color(0xFFDCFCE7), HealthyGreen)
                                        StatusColorType.ORANGE -> Pair(AmberBg, MedicalAmber)
                                        StatusColorType.RED -> Pair(RedBg, ClinicalRed)
                                        StatusColorType.BLUE -> Pair(Color(0xFFEFF6FF), PrimaryBlue)
                                    }
                                    Surface(
                                        shape = RoundedCornerShape(4.dp),
                                        color = bg
                                    ) {
                                        Text(
                                            text = metric.status,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = txt,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 8.sp
                                            ),
                                            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // AI Nutrition Insight Card
            if (foodItem.aiNutritionInsight != null) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = GreenBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, GreenBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = HealthyGreen.copy(alpha = 0.15f),
                            modifier = Modifier.size(32.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.LocalFlorist,
                                    contentDescription = null,
                                    tint = HealthyGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = foodItem.aiNutritionInsight,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = PrimaryTextColor,
                                lineHeight = 15.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // AI Recommendation Card
            if (foodItem.aiRecommendation != null) {
                Card(
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = LightLavenderBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LavenderBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = AiLavender,
                            modifier = Modifier.size(30.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.Psychology,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = foodItem.aiRecommendation,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 11.sp,
                                color = PrimaryTextColor,
                                lineHeight = 15.sp
                            ),
                            modifier = Modifier.weight(1f)
                        )
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = AiLavender,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            // Bottom Action Row (View Full Analysis & Delete Option)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = PrimaryBlue,
                    modifier = Modifier.clickable { onViewFullNutrition() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 7.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "View Full Analysis",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 12.sp
                            )
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onDelete,
                        modifier = Modifier.size(36.dp).testTag("btn_delete_food_item")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Analysis",
                            tint = ClinicalRed,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }
    }
}
