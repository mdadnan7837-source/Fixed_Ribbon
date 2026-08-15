package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.AiInsightDetail
import com.example.model.ChipType
import com.example.model.FocusIconType
import com.example.model.FoodAnalysisData
import com.example.model.NutrientMetric
import com.example.model.StatusColorType
import com.example.navigation.Screen

// Shared Color Palette for Unified Design System
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
private val RedBorder = Color(0xFFFECACA)
private val AppBackground = Color(0xFFF8FAFC)
private val PrimaryTextColor = Color(0xFF1F2937)
private val SecondaryTextColor = Color(0xFF6B7280)
private val CardBorderColor = Color(0xFFE5E7EB)

// Cancer-Aware-only accent colors (per official Cancer-Aware design spec).
// Applied only when activeGoal == CANCER_AWARE; every other goal keeps the
// existing shared green/lavender styling on this screen untouched.
private val CancerAccent = Color(0xFF7C3AED)
private val CancerLavenderBg = Color(0xFFF3E8FF)
private val CancerLavenderBorder = Color(0xFFE9D5FF)

// Cancer-Awareness ribbon: two crossed loops meeting at a point — matches the
// ribbon used on the Cancer-Aware Home banner, Profile badge, and History
// header, reused here at header-icon size.
@Composable
private fun CancerAwareRibbonCanvasResult(modifier: Modifier = Modifier) {
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
fun FoodAnalysisResultScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit,
    viewModel: FoodAnalysisViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val data = uiState.data
    val isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(
                    onClick = onBack,
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("btn_back")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = PrimaryTextColor
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Food Analysis Result",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PrimaryTextColor
                        )
                    )
                    if (isCancerAware) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Surface(
                            shape = CircleShape,
                            color = CancerLavenderBg,
                            modifier = Modifier.size(22.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                CancerAwareRibbonCanvasResult(modifier = Modifier.size(13.dp))
                            }
                        }
                    }
                }

                IconButton(
                    onClick = { /* Share */ },
                    modifier = Modifier
                        .size(36.dp)
                        .testTag("btn_share")
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Share",
                        tint = PrimaryTextColor,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        },
        containerColor = AppBackground,
        modifier = Modifier.testTag("food_analysis_result_screen")
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 14.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Food Summary Card (Image, Score Ring, Name, Ingredients, Scan time)
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Food Image Box
                        Box(
                            modifier = Modifier
                                .size(115.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(Color(0xFFFEF3C7)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "🍲", fontSize = 52.sp)
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = data.foodName,
                                        style = MaterialTheme.typography.titleLarge.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 20.sp,
                                            color = PrimaryTextColor
                                        )
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = data.ingredients,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 11.sp,
                                            color = SecondaryTextColor,
                                            lineHeight = 15.sp
                                        ),
                                        maxLines = 3
                                    )
                                }

                                Spacer(modifier = Modifier.width(8.dp))

                                // Score Ring Circle
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Box(
                                        modifier = Modifier.size(64.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        CircularProgressIndicator(
                                            progress = { (data.score / 100f).coerceIn(0f, 1f) },
                                            modifier = Modifier.fillMaxSize(),
                                            color = HealthyGreen,
                                            trackColor = HealthyGreen.copy(alpha = 0.15f),
                                            strokeWidth = 5.dp,
                                            strokeCap = StrokeCap.Round
                                        )
                                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                            Text(
                                                text = "${data.score}",
                                                style = MaterialTheme.typography.titleLarge.copy(
                                                    fontWeight = FontWeight.Bold,
                                                    fontSize = 21.sp,
                                                    color = PrimaryTextColor
                                                )
                                            )
                                            Text(
                                                text = "/100",
                                                style = MaterialTheme.typography.labelSmall.copy(
                                                    fontSize = 9.sp,
                                                    color = SecondaryTextColor
                                                )
                                            )
                                        }
                                    }

                                    Spacer(modifier = Modifier.height(4.dp))

                                    Surface(
                                        shape = RoundedCornerShape(6.dp),
                                        color = HealthyGreen
                                    ) {
                                        Text(
                                            text = data.scoreStatus,
                                            style = MaterialTheme.typography.labelSmall.copy(
                                                color = Color.White,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 10.sp
                                            ),
                                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }

                            Spacer(modifier = Modifier.height(10.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.AccessTime,
                                    contentDescription = null,
                                    tint = SecondaryTextColor,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = data.scanTimestamp,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        fontSize = 11.sp,
                                        color = SecondaryTextColor
                                    )
                                )
                            }
                        }
                    }
                }
            }

            // 2. Goal-Specific Insight Banner (Weight Loss Focus)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = if (isCancerAware) CancerLavenderBg else GreenBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, if (isCancerAware) CancerLavenderBorder else GreenBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = if (isCancerAware) CancerAccent.copy(alpha = 0.15f) else HealthyGreen.copy(alpha = 0.15f),
                            modifier = Modifier.size(42.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                if (isCancerAware) {
                                    CancerAwareRibbonCanvasResult(modifier = Modifier.size(24.dp))
                                } else {
                                    Icon(
                                        imageVector = Icons.Outlined.Shield,
                                        contentDescription = null,
                                        tint = HealthyGreen,
                                        modifier = Modifier.size(24.dp)
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            val isWeightLoss = (uiState.activeGoal == com.example.model.NutritionGoal.WEIGHT_LOSS)
                            val titleText = if (isWeightLoss) "Great Choice, Gogo Ji!" else data.greatChoiceTitle
                            Text(
                                text = titleText,
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = if (isCancerAware) CancerAccent else Color(0xFF15803D)
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = data.greatChoiceSubtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = PrimaryTextColor,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }

            // 3. Health Insights Tags
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Health Insights",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = PrimaryTextColor
                        )
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        data.healthInsights.forEach { chip ->
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
                                shape = RoundedCornerShape(20.dp),
                                color = bgCol,
                                border = androidx.compose.foundation.BorderStroke(1.dp, textCol.copy(alpha = 0.25f))
                            ) {
                                Row(
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 7.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        imageVector = icon,
                                        contentDescription = null,
                                        tint = textCol,
                                        modifier = Modifier.size(15.dp)
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Text(
                                        text = chip.text,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
                                            color = textCol
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 4. Nutrient Breakdown (per 1 serving) - 10 Grid Cards
            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Nutrient Breakdown (per 1 serving)",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryTextColor
                            )
                        )
                        Text(
                            text = "View Full Nutrition",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue,
                                fontSize = 12.sp
                            )
                        )
                    }

                    // 5 Columns x 2 Rows Grid layout using FlowRow or Rows
                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        maxItemsInEachRow = 5
                    ) {
                        data.nutrientMetrics.forEach { metric ->
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                                modifier = Modifier.weight(1f)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 10.dp, horizontal = 4.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.spacedBy(3.dp)
                                ) {
                                    Text(
                                        text = metric.label,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontSize = 10.sp,
                                            color = SecondaryTextColor
                                        ),
                                        textAlign = TextAlign.Center,
                                        maxLines = 1
                                    )
                                    Text(
                                        text = metric.value,
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 12.sp,
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
                                                    fontSize = 9.sp
                                                ),
                                                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // 5. Warning Banner (High Sodium)
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = AmberBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, AmberBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(14.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = MedicalAmber,
                            modifier = Modifier
                                .size(22.dp)
                                .padding(top = 2.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = data.highSodiumTitle,
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = MedicalAmber
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = data.highSodiumWarning,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = PrimaryTextColor,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }
                }
            }

            // 6. AI Nutrition Insights List
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Text(
                            text = "AI Nutrition Insights",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = PrimaryTextColor
                            )
                        )

                        data.aiInsightsList.forEach { insight ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.Top
                            ) {
                                Surface(
                                    shape = CircleShape,
                                    color = HealthyGreen.copy(alpha = 0.12f),
                                    modifier = Modifier.size(32.dp)
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(
                                            imageVector = if (insight.iconType == FocusIconType.CYAN_SHIELD) Icons.Outlined.Shield else Icons.Outlined.LocalFlorist,
                                            contentDescription = null,
                                            tint = HealthyGreen,
                                            modifier = Modifier.size(18.dp)
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = insight.title,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = PrimaryTextColor
                                            )
                                        )
                                        Text(
                                            text = insight.value,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 13.sp,
                                                color = HealthyGreen
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = insight.description,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 11.sp,
                                            color = SecondaryTextColor,
                                            lineHeight = 15.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // 7. AI Recommendation Box (Lavender Accent)
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = LightLavenderBg),
                    border = androidx.compose.foundation.BorderStroke(1.dp, LavenderBorder),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = AiLavender,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.Psychology,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "AI Recommendation",
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp,
                                    color = AiLavender
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = data.aiRecommendation,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = PrimaryTextColor,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = null,
                            tint = AiLavender,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Bottom Navigation to Temporary Food Analysis History
            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, PrimaryBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.FoodAnalysisHistory.route) }
                        .testTag("btn_view_temp_history")
                ) {
                    Row(
                        modifier = Modifier.padding(vertical = 14.dp, horizontal = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AccessTime,
                            contentDescription = null,
                            tint = PrimaryBlue,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "View Temporary Food History",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryBlue,
                                fontSize = 14.sp
                            )
                        )
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
