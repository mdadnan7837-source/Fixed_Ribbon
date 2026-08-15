package com.example.ui.screens

import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

// Color Palette aligning strictly with Approved Design Tokens
private val PrimaryTextColor = Color(0xFF1E293B)
private val SecondaryTextColor = Color(0xFF64748B)
private val AppBackground = Color(0xFFF8FAFC)
private val CardBorderColor = Color(0xFFE2E8F0)

private val FiberGreen = Color(0xFF16A34A)
private val FruitsTeal = Color(0xFF0284C7)
private val WholeGrainAmber = Color(0xFFD97706)
private val ProcessedMeatRed = Color(0xFFDC2626)

private val BlueBrand = Color(0xFF2563EB)

// Cancer-Aware-only accent colors (per official Cancer-Aware design spec).
// Applied only when activeGoal == CANCER_AWARE; every other goal keeps the
// existing blue-accented header/summary card untouched.
private val CancerRibbonColor = Color(0xFF7C3AED)
private val CancerCardGradientStart = Color(0xFFFAF5FF)
private val CancerCardGradientEnd = Color(0xFFF3E8FF)
private val CancerCardBorder = Color(0xFFF3E8FF)

enum class NutrientType {
    FIBER,
    FRUITS_VEGGIES,
    WHOLE_GRAINS,
    PROCESSED_MEAT
}

@Composable
fun HistoryScreen(
    viewModel: HistoryViewModel,
    onNavigate: (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = Screen.History.route,
                onNavigate = onNavigate
            )
        },
        containerColor = AppBackground,
        modifier = Modifier.testTag("history_screen")
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                // 1. Header Section
                HistoryHeaderSection(activeGoal = uiState.activeGoal)
            }

            item {
                // 2. Weekly Summary Card
                WeeklySummaryCardSection(
                    summary = uiState.weeklySummary,
                    isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE
                )
            }

            // 3. Daily History Cards
            items(uiState.historyRecords, key = { it.id }) { record ->
                DailyHistoryCard(
                    record = record,
                    onClick = {
                        viewModel.selectReport(record)
                        onNavigate(Screen.DailyNutritionReport.route)
                    }
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun HistoryHeaderSection(activeGoal: com.example.model.NutritionGoal) {
    val isWeightLoss = (activeGoal == com.example.model.NutritionGoal.WEIGHT_LOSS)
    val isCancerAware = (activeGoal == com.example.model.NutritionGoal.CANCER_AWARE)
    val titleText = if (isWeightLoss) "Gogo Ji’s History" else "History"
    val subtitleText = if (isWeightLoss) "Gogo Ji’s daily nutrition journey" else "Your daily nutrition journey"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("history_header"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = titleText,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = 26.sp
                    )
                )
                if (isCancerAware) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFFF3E8FF),
                        modifier = Modifier.size(24.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            CancerAwareRibbonCanvasHistory(modifier = Modifier.size(14.dp))
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitleText,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = SecondaryTextColor,
                    fontSize = 13.sp
                )
            )
        }

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Calendar picker */ }
                    .testTag("btn_history_calendar")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = "Calendar",
                        tint = BlueBrand,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                modifier = Modifier
                    .size(40.dp)
                    .clickable { /* Filter menu */ }
                    .testTag("btn_history_filter")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.FilterList,
                        contentDescription = "Filter",
                        tint = BlueBrand,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

// Cancer-Awareness ribbon: two crossed loops meeting at a point — matches the
// ribbon used on the Cancer-Aware Home banner and Profile badge, reused here
// at header-icon size.
@Composable
private fun CancerAwareRibbonCanvasHistory(modifier: Modifier = Modifier) {
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

        drawPath(tail, color = CancerRibbonColor.copy(alpha = 0.85f))
        drawPath(leftLoop, color = CancerRibbonColor)
        drawPath(rightLoop, color = CancerRibbonColor.copy(alpha = 0.92f))
    }
}

@Composable
private fun WeeklySummaryCardSection(summary: WeeklySummary, isCancerAware: Boolean = false) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("card_weekly_summary"),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = if (isCancerAware) {
                        Brush.verticalGradient(colors = listOf(CancerCardGradientStart, CancerCardGradientEnd))
                    } else {
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF8FAFC),
                                Color(0xFFEFF6FF)
                            )
                        )
                    }
                )
                .border(
                    1.dp,
                    if (isCancerAware) CancerCardBorder else Color(0xFFDBEAFE),
                    RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Col 1: Average Score
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Average Score",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryTextColor,
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Row(verticalAlignment = Alignment.Bottom) {
                        Text(
                            text = "${summary.averageScore}",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = BlueBrand,
                                fontSize = 28.sp
                            )
                        )
                        Text(
                            text = " /100",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = SecondaryTextColor,
                                fontSize = 11.sp
                            ),
                            modifier = Modifier.padding(bottom = 3.dp)
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
                                fontSize = 11.sp
                            ),
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                        )
                    }
                }

                // Col 2: Best Day
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Best Day",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryTextColor,
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${summary.bestDayScore}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = FiberGreen,
                            fontSize = 28.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = summary.bestDayDate,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SecondaryTextColor,
                            fontSize = 11.sp
                        )
                    )
                }

                // Col 3: Total Days
                Column(horizontalAlignment = Alignment.Start) {
                    Text(
                        text = "Total Days",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.SemiBold,
                            color = PrimaryTextColor,
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${summary.totalDays}",
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = PrimaryTextColor,
                            fontSize = 28.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = summary.timePeriodLabel,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SecondaryTextColor,
                            fontSize = 11.sp
                        )
                    )
                }

                // Col 4: Trend Line Chart
                Box(
                    modifier = Modifier
                        .size(width = 80.dp, height = 48.dp)
                        .testTag("chart_weekly_trend")
                ) {
                    WeeklyTrendChartCanvas(scores = summary.trendScores)
                }
            }
        }
    }
}

@Composable
private fun WeeklyTrendChartCanvas(scores: List<Float>) {
    Canvas(modifier = Modifier.fillMaxSize()) {
        if (scores.isEmpty()) return@Canvas

        val maxVal = 100f
        val minVal = 50f
        val range = maxVal - minVal

        val stepX = size.width / (scores.size - 1)
        val points = scores.mapIndexed { index, score ->
            val x = index * stepX
            val y = size.height - ((score - minVal) / range * (size.height - 10.dp.toPx())) - 5.dp.toPx()
            androidx.compose.ui.geometry.Offset(x, y)
        }

        val path = Path().apply {
            moveTo(points.first().x, points.first().y)
            for (i in 0 until points.size - 1) {
                val p1 = points[i]
                val p2 = points[i + 1]
                val cx1 = p1.x + (p2.x - p1.x) / 2f
                val cy1 = p1.y
                val cx2 = p1.x + (p2.x - p1.x) / 2f
                val cy2 = p2.y
                cubicTo(cx1, cy1, cx2, cy2, p2.x, p2.y)
            }
        }

        drawPath(
            path = path,
            color = BlueBrand,
            style = Stroke(width = 2.dp.toPx(), cap = StrokeCap.Round)
        )

        points.forEach { point ->
            drawCircle(
                color = BlueBrand,
                radius = 3.2.dp.toPx(),
                center = point
            )
            drawCircle(
                color = Color.White,
                radius = 1.2.dp.toPx(),
                center = point
            )
        }
    }
}

@Composable
private fun DailyHistoryCard(
    record: DailyHistoryRecord,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .testTag("card_history_${record.id}"),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // Header Row of Daily Card
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarToday,
                        contentDescription = null,
                        tint = BlueBrand,
                        modifier = Modifier.size(18.dp)
                    )
                    Column {
                        Text(
                            text = record.title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryTextColor,
                                fontSize = 15.sp
                            )
                        )
                        if (record.dateSubtitle.isNotEmpty()) {
                            Text(
                                text = record.dateSubtitle,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = SecondaryTextColor,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    val isHighScore = record.score >= 90
                    val scoreBg = if (isHighScore) Color(0xFFF0FDF4) else Color(0xFFEFF6FF)
                    val scoreTextColor = if (isHighScore) FiberGreen else BlueBrand

                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = scoreBg
                    ) {
                        Text(
                            text = "${record.score}/100",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = scoreTextColor,
                                fontSize = 12.sp
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
                        )
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Details",
                        tint = SecondaryTextColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // 4 Nutrient Rings Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                HistoryNutrientGauge(
                    progress = record.fiber,
                    type = NutrientType.FIBER,
                    modifier = Modifier.weight(1f)
                )
                HistoryNutrientGauge(
                    progress = record.fruitsVeggies,
                    type = NutrientType.FRUITS_VEGGIES,
                    modifier = Modifier.weight(1f)
                )
                HistoryNutrientGauge(
                    progress = record.wholeGrains,
                    type = NutrientType.WHOLE_GRAINS,
                    modifier = Modifier.weight(1f)
                )
                HistoryNutrientGauge(
                    progress = record.processedMeat,
                    type = NutrientType.PROCESSED_MEAT,
                    modifier = Modifier.weight(1f)
                )
            }

            // AI Daily Summary Box
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Summary detail */ },
                shape = RoundedCornerShape(12.dp),
                color = Color(0xFFF0FDF4),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFDCFCE7))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.Top,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AutoAwesome,
                            contentDescription = "AI Summary",
                            tint = Color(0xFF0D9488),
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 2.dp)
                        )
                        Column {
                            Text(
                                text = "AI Daily Summary",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF0F766E),
                                    fontSize = 12.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = record.aiSummary,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF134E4A),
                                    fontSize = 11.sp,
                                    lineHeight = 16.sp
                                )
                            )
                        }
                    }

                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                        contentDescription = "Open Summary",
                        tint = Color(0xFF0F766E),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun HistoryNutrientGauge(
    progress: DayNutrientProgress,
    type: NutrientType,
    modifier: Modifier = Modifier
) {
    val isZeroProcessedMeatAchieved = type == NutrientType.PROCESSED_MEAT && progress.current == 0.0 && progress.isAchieved

    val effectiveColor = when {
        isZeroProcessedMeatAchieved -> FiberGreen
        type == NutrientType.FIBER -> FiberGreen
        type == NutrientType.FRUITS_VEGGIES -> FruitsTeal
        type == NutrientType.WHOLE_GRAINS -> WholeGrainAmber
        type == NutrientType.PROCESSED_MEAT -> ProcessedMeatRed
        else -> FiberGreen
    }

    val progressFraction = (progress.current / progress.target).toFloat().coerceIn(0f, 1f)

    Column(
        modifier = modifier.testTag("gauge_${type.name.lowercase()}"),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier.size(48.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { progressFraction },
                modifier = Modifier.fillMaxSize(),
                color = effectiveColor,
                trackColor = effectiveColor.copy(alpha = 0.15f),
                strokeWidth = 3.5.dp,
                strokeCap = StrokeCap.Round
            )

            // Custom Icon per Nutrient Type
            when (type) {
                NutrientType.FIBER -> {
                    Icon(
                        imageVector = Icons.Outlined.Eco,
                        contentDescription = "Fiber",
                        tint = effectiveColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                NutrientType.FRUITS_VEGGIES -> {
                    GrapesIcon(
                        tint = effectiveColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                NutrientType.WHOLE_GRAINS -> {
                    Icon(
                        imageVector = Icons.Outlined.Grain,
                        contentDescription = "Whole Grains",
                        tint = effectiveColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                NutrientType.PROCESSED_MEAT -> {
                    if (isZeroProcessedMeatAchieved) {
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = "Processed Meat",
                            tint = effectiveColor,
                            modifier = Modifier.size(18.dp)
                        )
                    } else {
                        MeatIcon(
                            tint = effectiveColor,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }

            // Status Badge
            if (progress.isAchieved) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(14.dp)
                        .background(effectiveColor, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Achieved",
                        tint = Color.White,
                        modifier = Modifier.size(9.dp)
                    )
                }
            } else if (progress.isWarning) {
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(14.dp)
                        .background(ProcessedMeatRed, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "!",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp
                    )
                }
            }
        }

        Text(
            text = progress.name,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = PrimaryTextColor
            ),
            textAlign = TextAlign.Center,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )

        val valueText = "${if (progress.current % 1.0 == 0.0) progress.current.toInt() else progress.current}"
        val targetText = "/ ${progress.target.toInt()}${progress.unit}"

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = valueText,
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = effectiveColor
                )
            )
            Text(
                text = " $targetText",
                style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = SecondaryTextColor
                )
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

        // Stem
        drawLine(
            color = tint,
            start = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.12f),
            end = androidx.compose.ui.geometry.Offset(w * 0.5f, h * 0.28f),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Grape circles
        drawCircle(color = tint, radius = r, center = androidx.compose.ui.geometry.Offset(w * 0.35f, h * 0.42f))
        drawCircle(color = tint, radius = r, center = androidx.compose.ui.geometry.Offset(w * 0.65f, h * 0.42f))
        drawCircle(color = tint, radius = r, center = androidx.compose.ui.geometry.Offset(w * 0.28f, h * 0.65f))
        drawCircle(color = tint, radius = r, center = androidx.compose.ui.geometry.Offset(w * 0.50f, h * 0.65f))
        drawCircle(color = tint, radius = r, center = androidx.compose.ui.geometry.Offset(w * 0.72f, h * 0.65f))
        drawCircle(color = tint, radius = r, center = androidx.compose.ui.geometry.Offset(w * 0.50f, h * 0.85f))
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

        // Bone center
        drawCircle(
            color = Color.White,
            radius = w * 0.12f,
            center = androidx.compose.ui.geometry.Offset(w * 0.45f, h * 0.52f)
        )
    }
}
