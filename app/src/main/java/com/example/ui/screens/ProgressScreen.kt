package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigation.Screen
import com.example.ui.components.AppBottomBar

private val PrimaryTextColor = Color(0xFF1E293B)
private val SecondaryTextColor = Color(0xFF64748B)
private val AppBackground = Color(0xFFF8FAFC)
private val CardBorderColor = Color(0xFFE2E8F0)

private val FiberGreen = Color(0xFF16A34A)
private val FruitsTeal = Color(0xFF0284C7)
private val WholeGrainAmber = Color(0xFFD97706)
private val ProcessedMeatRed = Color(0xFFDC2626)
private val WaterBlue = Color(0xFF0284C7)
private val PurpleBadge = Color(0xFF9333EA)

private val BlueBrand = Color(0xFF2563EB)

@Composable
fun ProgressScreen(
    viewModel: ProgressViewModel,
    onNavigate: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = Screen.Progress.route,
                onNavigate = onNavigate
            )
        },
        containerColor = AppBackground,
        modifier = Modifier.testTag("progress_screen")
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // 1. Header
                ProgressHeaderSection(
                    timeFilter = uiState.timeFilter,
                    activeGoal = uiState.activeGoal,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            item {
                // 2. KPI Summary Card
                KpiSummaryCardSection(
                    summary = uiState.kpiSummary,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            item {
                // 3. Nutrition Score Chart Card
                NutritionScoreChartCardSection(
                    filterLabel = uiState.chartFilter,
                    points = uiState.chartPoints,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            item {
                // 4. Nutrient Trends Section
                NutrientTrendsSection(
                    trends = uiState.nutrientTrends,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            item {
                // 5. Goal Progress Section
                GoalProgressSection(
                    goals = uiState.goalProgressList,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            item {
                // 6. Achievements Section
                AchievementsSection(
                    achievements = uiState.achievements,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun ProgressHeaderSection(
    timeFilter: String,
    activeGoal: com.example.model.NutritionGoal,
    isCancerAware: Boolean = false
) {
    val isWeightLoss = (activeGoal == com.example.model.NutritionGoal.WEIGHT_LOSS)
    val headerTitle = if (isWeightLoss) "Gogo Ji’s Progress" else "Progress"
    val headerSubtitle = if (isWeightLoss) "Track Gogo Ji’s health journey and weight loss" else "Track your health journey and improvements"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("progress_header"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = if (isCancerAware) {
                Modifier
                    .weight(1f)
                    .padding(end = 12.dp)
            } else {
                Modifier
            }
        ) {
            Text(
                text = headerTitle,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextColor,
                    fontSize = 26.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = headerSubtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SecondaryTextColor,
                    fontSize = 13.sp
                ),
                maxLines = if (isCancerAware) 2 else Int.MAX_VALUE
            )
        }

        Surface(
            shape = RoundedCornerShape(12.dp),
            color = Color.White,
            border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
            modifier = Modifier
                .clickable { }
                .then(if (isCancerAware) Modifier.widthIn(min = 108.dp) else Modifier)
                .testTag("btn_time_filter")
        ) {
            Row(
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.CalendarToday,
                    contentDescription = null,
                    tint = BlueBrand,
                    modifier = Modifier.size(16.dp)
                )
                Text(
                    text = timeFilter,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = BlueBrand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = if (isCancerAware) Modifier.weight(1f, fill = false) else Modifier
                )
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = null,
                    tint = BlueBrand,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun KpiSummaryCardSection(summary: KpiSummary, isCancerAware: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("card_kpi_summary"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        if (isCancerAware) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) { AverageScoreMetric(summary) }
                    Box(modifier = Modifier.weight(1f)) { TotalImprovementMetric(summary) }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) { ConsistencyMetric(summary) }
                    Box(modifier = Modifier.weight(1f)) { GoalProgressMetric(summary) }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(modifier = Modifier.weight(1f)) { AverageScoreMetric(summary) }
                Box(modifier = Modifier.weight(1f)) { TotalImprovementMetric(summary) }
                Box(modifier = Modifier.weight(1f)) { ConsistencyMetric(summary) }
                Box(modifier = Modifier.weight(1f)) { GoalProgressMetric(summary) }
            }
        }
    }
}

@Composable
private fun AverageScoreMetric(summary: KpiSummary) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Average Score",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = PrimaryTextColor,
                fontSize = 11.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${summary.averageScore}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BlueBrand,
                    fontSize = 24.sp
                )
            )
            Text(
                text = " /100",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryTextColor,
                    fontSize = 10.sp
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFFEFF6FF)
        ) {
            Text(
                text = summary.scoreStatus,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = BlueBrand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun TotalImprovementMetric(summary: KpiSummary) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Total Improvement",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = PrimaryTextColor,
                fontSize = 11.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = summary.totalImprovement,
            style = MaterialTheme.typography.headlineMedium.copy(
                fontWeight = FontWeight.Bold,
                color = FiberGreen,
                fontSize = 24.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = summary.improvementSubtitle,
            style = MaterialTheme.typography.bodySmall.copy(
                color = SecondaryTextColor,
                fontSize = 10.sp
            )
        )
    }
}

@Composable
private fun ConsistencyMetric(summary: KpiSummary) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Consistency",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = PrimaryTextColor,
                fontSize = 11.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${summary.consistencyDays}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextColor,
                    fontSize = 24.sp
                )
            )
            Text(
                text = " Days",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = SecondaryTextColor,
                    fontSize = 10.sp
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Surface(
            shape = RoundedCornerShape(6.dp),
            color = Color(0xFFF0FDF4)
        ) {
            Text(
                text = summary.consistencyStatus,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = FiberGreen,
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp
                ),
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
            )
        }
    }
}

@Composable
private fun GoalProgressMetric(summary: KpiSummary) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(
            text = "Goal Progress",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = PrimaryTextColor,
                fontSize = 11.sp
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.Bottom) {
            Text(
                text = "${summary.goalProgressPercent}",
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BlueBrand,
                    fontSize = 24.sp
                )
            )
            Text(
                text = "%",
                style = MaterialTheme.typography.labelSmall.copy(
                    color = BlueBrand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                modifier = Modifier.padding(bottom = 2.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        LinearProgressIndicator(
            progress = { summary.goalProgressPercent / 100f },
            modifier = Modifier
                .fillMaxWidth()
                .height(6.dp)
                .clip(CircleShape),
            color = BlueBrand,
            trackColor = Color(0xFFE2E8F0)
        )
    }
}

@Composable
private fun NutritionScoreChartCardSection(
    filterLabel: String,
    points: List<ChartDataPoint>,
    isCancerAware: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("card_score_chart"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Nutrition Score Over Time",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = if (isCancerAware) 16.sp else 15.sp
                    )
                )

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                    modifier = Modifier.clickable { }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            text = filterLabel,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SecondaryTextColor,
                                fontSize = 11.sp
                            )
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            tint = SecondaryTextColor,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(if (isCancerAware) 20.dp else 16.dp))

            // Main Score Chart Canvas with Axis Labels
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isCancerAware) 200.dp else 180.dp)
            ) {
                ScoreLineChartCanvas(points = points)
            }
        }
    }
}

@Composable
private fun ScoreLineChartCanvas(points: List<ChartDataPoint>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (points.isEmpty()) return@Canvas

        val leftPadding = 30.dp.toPx()
        val bottomPadding = 24.dp.toPx()
        val topPadding = 24.dp.toPx()

        val chartWidth = size.width - leftPadding
        val chartHeight = size.height - bottomPadding - topPadding

        val ySteps = listOf(100, 75, 50, 25, 0)
        ySteps.forEach { yValue ->
            val yPos = topPadding + (1f - yValue / 100f) * chartHeight
            drawLine(
                color = Color(0xFFF1F5F9),
                start = Offset(leftPadding, yPos),
                end = Offset(size.width, yPos),
                strokeWidth = 1.dp.toPx()
            )
        }

        val stepX = chartWidth / (points.size - 1)
        val offsets = points.mapIndexed { index, pt ->
            val x = leftPadding + index * stepX
            val y = topPadding + (1f - pt.score / 100f) * chartHeight
            Offset(x, y)
        }

        // Fill path beneath curve
        val fillPath = Path().apply {
            moveTo(offsets.first().x, size.height - bottomPadding)
            lineTo(offsets.first().x, offsets.first().y)
            for (i in 0 until offsets.size - 1) {
                val p1 = offsets[i]
                val p2 = offsets[i + 1]
                val cx1 = p1.x + (p2.x - p1.x) / 2f
                val cy1 = p1.y
                val cx2 = p1.x + (p2.x - p1.x) / 2f
                val cy2 = p2.y
                cubicTo(cx1, cy1, cx2, cy2, p2.x, p2.y)
            }
            lineTo(offsets.last().x, size.height - bottomPadding)
            close()
        }

        drawPath(
            path = fillPath,
            brush = Brush.verticalGradient(
                colors = listOf(
                    BlueBrand.copy(alpha = 0.15f),
                    BlueBrand.copy(alpha = 0.01f)
                )
            )
        )

        // Curve stroke
        val strokePath = Path().apply {
            moveTo(offsets.first().x, offsets.first().y)
            for (i in 0 until offsets.size - 1) {
                val p1 = offsets[i]
                val p2 = offsets[i + 1]
                val cx1 = p1.x + (p2.x - p1.x) / 2f
                val cy1 = p1.y
                val cx2 = p1.x + (p2.x - p1.x) / 2f
                val cy2 = p2.y
                cubicTo(cx1, cy1, cx2, cy2, p2.x, p2.y)
            }
        }

        drawPath(
            path = strokePath,
            color = BlueBrand,
            style = Stroke(width = 2.5.dp.toPx(), cap = StrokeCap.Round)
        )

        // Points
        offsets.forEachIndexed { index, offset ->
            val pt = points[index]
            drawCircle(
                color = BlueBrand,
                radius = 4.dp.toPx(),
                center = offset
            )
            drawCircle(
                color = Color.White,
                radius = 2.dp.toPx(),
                center = offset
            )

            // Highlighting last point bubble 87
            if (pt.isHighlighted) {
                drawCircle(
                    color = BlueBrand,
                    radius = 6.dp.toPx(),
                    center = offset
                )
                drawCircle(
                    color = Color.White,
                    radius = 2.5.dp.toPx(),
                    center = offset
                )
            }
        }
    }

    // HTML-like overlays for values & dates
    Box(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .padding(start = 28.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            points.forEach { pt ->
                Text(
                    text = pt.dateLabel,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = SecondaryTextColor
                    )
                )
            }
        }

        // Value text badges
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 28.dp, top = 2.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            points.forEach { pt ->
                if (pt.isHighlighted) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = BlueBrand
                    ) {
                        Text(
                            text = "${pt.score}",
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            ),
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                        )
                    }
                } else {
                    Text(
                        text = "${pt.score}",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = PrimaryTextColor
                        )
                    )
                }
            }
        }
    }
}

@Composable
private fun NutrientTrendsSection(trends: List<NutrientTrendItem>, isCancerAware: Boolean = false) {
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
                text = "Nutrient Trends",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextColor,
                    fontSize = 16.sp
                )
            )
            Text(
                text = "View All",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = BlueBrand,
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp
                ),
                modifier = Modifier.clickable { }
            )
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(if (isCancerAware) 14.dp else 12.dp),
            contentPadding = if (isCancerAware)
                PaddingValues(end = 28.dp)
            else
                PaddingValues(end = 4.dp)
        ) {
            items(trends, key = { it.id }) { item ->
                NutrientTrendCard(item = item, isCancerAware = isCancerAware)
            }
        }
    }
}

@Composable
private fun NutrientTrendCard(item: NutrientTrendItem, isCancerAware: Boolean = false) {
    val themeColor = when (item.id) {
        "fiber" -> FiberGreen
        "fruits" -> FruitsTeal
        "grains" -> WholeGrainAmber
        "meat" -> ProcessedMeatRed
        else -> WaterBlue
    }

    Card(
        modifier = Modifier
            .width(if (isCancerAware) 130.dp else 115.dp)
            .height(if (isCancerAware) 196.dp else 172.dp)
            .testTag("card_trend_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(if (isCancerAware) 14.dp else 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(if (isCancerAware) 8.dp else 6.dp)
        ) {
            // Icon Badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(themeColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                when (item.id) {
                    "fiber" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                    "fruits" -> GrapesIcon(tint = themeColor, modifier = Modifier.size(18.dp))
                    "grains" -> Icon(Icons.Outlined.Grain, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                    "meat" -> MeatIcon(tint = themeColor, modifier = Modifier.size(18.dp))
                    else -> Icon(Icons.Outlined.WaterDrop, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                }
            }

            // Fixed-height title area so 1-line and 2-line names don't
            // change the card's proportions or push the sparkline around.
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.name,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        lineHeight = 14.sp,
                        color = PrimaryTextColor
                    ),
                    textAlign = TextAlign.Center,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = item.avgValue,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 12.sp,
                    color = themeColor
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = if (item.isIncrease) "↑ ${item.changePercent}" else "↓ ${item.changePercent}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = if (item.isIncrease) FiberGreen else ProcessedMeatRed
                    ),
                    maxLines = 1
                )
            }

            Text(
                text = "vs last week",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = SecondaryTextColor
                ),
                maxLines = 1
            )

            Spacer(modifier = Modifier.weight(1f))

            // Sparkline canvas
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(if (isCancerAware) 24.dp else 20.dp)
            ) {
                SparklineCanvas(scores = item.sparklineScores, color = themeColor)
            }
        }
    }
}

@Composable
private fun SparklineCanvas(scores: List<Float>, color: Color) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (scores.isEmpty()) return@Canvas
        val maxVal = scores.maxOrNull() ?: 10f
        val minVal = scores.minOrNull() ?: 0f
        val range = (maxVal - minVal).coerceAtLeast(0.1f)

        val stepX = size.width / (scores.size - 1)
        val points = scores.mapIndexed { index, score ->
            val x = index * stepX
            val y = size.height - ((score - minVal) / range * (size.height - 4.dp.toPx())) - 2.dp.toPx()
            Offset(x, y)
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 1 until points.size) {
                lineTo(points[i].x, points[i].y)
            }
        }

        drawPath(
            path = path,
            color = color,
            style = Stroke(width = 1.8.dp.toPx(), cap = StrokeCap.Round)
        )

        drawCircle(color = color, radius = 2.5.dp.toPx(), center = points.last())
    }
}

@Composable
private fun GoalProgressSection(goals: List<GoalProgressItem>, isCancerAware: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("card_goal_progress"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(if (isCancerAware) 18.dp else 14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Goal Progress",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "Edit Goals",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = BlueBrand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.clickable { }
                )
            }

            goals.forEach { goal ->
                if (isCancerAware) {
                    GoalProgressRowItemCancerAware(goal = goal)
                } else {
                    GoalProgressRowItem(goal = goal)
                }
            }
        }
    }
}

@Composable
private fun GoalProgressRowItemCancerAware(goal: GoalProgressItem) {
    val barColor = when (goal.id) {
        "g1" -> FiberGreen
        "g2" -> FruitsTeal
        "g3" -> WholeGrainAmber
        else -> ProcessedMeatRed
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("goal_row_${goal.id}"),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .background(barColor.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            when (goal.id) {
                "g1" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = barColor, modifier = Modifier.size(20.dp))
                "g2" -> GrapesIcon(tint = barColor, modifier = Modifier.size(20.dp))
                "g3" -> Icon(Icons.Outlined.Grain, contentDescription = null, tint = barColor, modifier = Modifier.size(20.dp))
                else -> MeatIcon(tint = barColor, modifier = Modifier.size(20.dp))
            }
        }

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                text = goal.title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextColor,
                    fontSize = 13.sp
                ),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = goal.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SecondaryTextColor,
                    fontSize = 11.sp
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.height(4.dp))

            val formattedCurrent = if (goal.current % 1.0 == 0.0) goal.current.toInt() else goal.current
            val formattedTarget = goal.target.toInt()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(6.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFE2E8F0))
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(fraction = (goal.percent / 100f).coerceAtMost(1f))
                            .background(barColor, CircleShape)
                    )
                }

                Text(
                    text = "$formattedCurrent / $formattedTarget ${goal.unit}",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = 10.sp
                    ),
                    maxLines = 1,
                    softWrap = false
                )

                Text(
                    text = "${goal.percent}%",
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = barColor,
                        fontSize = 11.sp
                    ),
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
private fun GoalProgressRowItem(goal: GoalProgressItem) {
    val barColor = when (goal.id) {
        "g1" -> FiberGreen
        "g2" -> FruitsTeal
        "g3" -> WholeGrainAmber
        else -> ProcessedMeatRed
    }

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .background(barColor.copy(alpha = 0.12f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            when (goal.id) {
                "g1" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = barColor, modifier = Modifier.size(18.dp))
                "g2" -> GrapesIcon(tint = barColor, modifier = Modifier.size(18.dp))
                "g3" -> Icon(Icons.Outlined.Grain, contentDescription = null, tint = barColor, modifier = Modifier.size(18.dp))
                else -> MeatIcon(tint = barColor, modifier = Modifier.size(18.dp))
            }
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = goal.title,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextColor,
                    fontSize = 12.sp
                )
            )
            Text(
                text = goal.subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SecondaryTextColor,
                    fontSize = 10.sp
                )
            )
        }

        Box(
            modifier = Modifier
                .width(100.dp)
                .height(6.dp)
                .clip(CircleShape)
                .background(Color(0xFFE2E8F0))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(fraction = (goal.percent / 100f).coerceAtMost(1f))
                    .background(barColor, CircleShape)
            )
        }

        val formattedCurrent = if (goal.current % 1.0 == 0.0) goal.current.toInt() else goal.current
        val formattedTarget = goal.target.toInt()

        Text(
            text = "$formattedCurrent / $formattedTarget ${goal.unit}",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = PrimaryTextColor,
                fontSize = 10.sp
            )
        )

        Text(
            text = "${goal.percent}%",
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                color = barColor,
                fontSize = 11.sp
            ),
            modifier = Modifier.width(32.dp),
            textAlign = TextAlign.End
        )
    }
}

@Composable
private fun AchievementsSection(achievements: List<AchievementItem>, isCancerAware: Boolean = false) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        if (isCancerAware) {
            Text(
                text = "Achievements",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryTextColor,
                    fontSize = 16.sp
                )
            )
        } else {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Achievements",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = 16.sp
                    )
                )
                Text(
                    text = "View All",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = BlueBrand,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.clickable { }
                )
            }
        }

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(if (isCancerAware) 14.dp else 12.dp),
            contentPadding = if (isCancerAware) PaddingValues(end = 28.dp) else PaddingValues(end = 4.dp)
        ) {
            items(achievements, key = { it.id }) { item ->
                if (isCancerAware) {
                    AchievementCardItemCancerAware(item = item)
                } else {
                    AchievementCardItem(item = item)
                }
            }
        }
    }
}

@Composable
private fun AchievementCardItem(item: AchievementItem) {
    val themeColor = when (item.type) {
        "FIBER" -> FiberGreen
        "VEGGIE" -> FruitsTeal
        "GRAIN" -> WholeGrainAmber
        "HYDRATION" -> WaterBlue
        else -> PurpleBadge
    }

    Card(
        modifier = Modifier
            .width(115.dp)
            .testTag("card_achievement_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // Shield Shield Icon Container
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .background(themeColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.fillMaxSize(0.8f)
                )

                when (item.type) {
                    "FIBER" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = themeColor, modifier = Modifier.size(16.dp))
                    "VEGGIE" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = themeColor, modifier = Modifier.size(16.dp))
                    "GRAIN" -> Icon(Icons.Outlined.Grain, contentDescription = null, tint = themeColor, modifier = Modifier.size(16.dp))
                    "HYDRATION" -> Icon(Icons.Outlined.WaterDrop, contentDescription = null, tint = themeColor, modifier = Modifier.size(16.dp))
                    else -> Icon(Icons.Outlined.Star, contentDescription = null, tint = themeColor, modifier = Modifier.size(16.dp))
                }
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = PrimaryTextColor
                ),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.streakText,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = SecondaryTextColor
                ),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun AchievementCardItemCancerAware(item: AchievementItem) {
    val themeColor = when (item.type) {
        "FIBER" -> FiberGreen
        "VEGGIE" -> FruitsTeal
        "GRAIN" -> WholeGrainAmber
        "HYDRATION" -> WaterBlue
        else -> PurpleBadge
    }

    Card(
        modifier = Modifier
            .width(140.dp)
            .testTag("card_achievement_cancer_${item.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Shield Icon Container
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(themeColor.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Shield,
                    contentDescription = null,
                    tint = themeColor,
                    modifier = Modifier.fillMaxSize(0.8f)
                )

                when (item.type) {
                    "FIBER" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                    "VEGGIE" -> Icon(Icons.Outlined.Eco, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                    "GRAIN" -> Icon(Icons.Outlined.Grain, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                    "HYDRATION" -> Icon(Icons.Outlined.WaterDrop, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                    else -> Icon(Icons.Outlined.Star, contentDescription = null, tint = themeColor, modifier = Modifier.size(18.dp))
                }
            }

            Text(
                text = item.title,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp,
                    color = PrimaryTextColor
                ),
                textAlign = TextAlign.Center,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.streakText,
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = SecondaryTextColor
                ),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun GrapesIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val r = w * 0.15f

        drawLine(
            color = tint,
            start = Offset(w * 0.5f, h * 0.12f),
            end = Offset(w * 0.5f, h * 0.28f),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )

        drawCircle(color = tint, radius = r, center = Offset(w * 0.35f, h * 0.42f))
        drawCircle(color = tint, radius = r, center = Offset(w * 0.65f, h * 0.42f))
        drawCircle(color = tint, radius = r, center = Offset(w * 0.28f, h * 0.65f))
        drawCircle(color = tint, radius = r, center = Offset(w * 0.50f, h * 0.65f))
        drawCircle(color = tint, radius = r, center = Offset(w * 0.72f, h * 0.65f))
        drawCircle(color = tint, radius = r, center = Offset(w * 0.50f, h * 0.85f))
    }
}

@Composable
private fun MeatIcon(tint: Color, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        val path = Path().apply {
            moveTo(w * 0.25f, h * 0.35f)
            cubicTo(w * 0.25f, h * 0.2f, w * 0.75f, h * 0.2f, w * 0.82f, h * 0.42f)
            cubicTo(w * 0.9f, h * 0.65f, w * 0.65f, h * 0.85f, w * 0.42f, h * 0.82f)
            cubicTo(w * 0.2f, h * 0.8f, w * 0.15f, h * 0.5f, w * 0.25f, h * 0.35f)
            close()
        }

        drawPath(path = path, color = tint)

        drawCircle(
            color = Color.White,
            radius = w * 0.12f,
            center = Offset(w * 0.45f, h * 0.52f)
        )
    }
}
