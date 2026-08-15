package com.example.ui.screens

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Insights
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
import com.example.navigation.Screen
import com.example.repository.DailyNutritionReportData
import com.example.repository.PermanentHistoryManager
import com.example.repository.TemporaryFoodHistoryManager
import kotlinx.coroutines.delay

private val MedicalBlue = Color(0xFF2563EB)
private val HealthcareTeal = Color(0xFF0D9488)
private val AiLavender = Color(0xFFA855F7)
private val HealthyGreen = Color(0xFF22C55E)
private val AppBackground = Color(0xFFF8FAFC)
private val PrimaryTextColor = Color(0xFF1F2937)
private val SecondaryTextColor = Color(0xFF6B7280)
private val CardBorderColor = Color(0xFFE5E7EB)

@Composable
fun EodProcessingScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    var currentStepIndex by remember { mutableIntStateOf(0) }
    var isProcessingComplete by remember { mutableStateOf(false) }

    val infiniteTransition = rememberInfiniteTransition(label = "eod_pulse")
    val pulseScale by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(900, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulseScale"
    )

    LaunchedEffect(Unit) {
        currentStepIndex = 0
        delay(600)
        currentStepIndex = 1 // Meals analyzed
        delay(700)
        currentStepIndex = 2 // Macros & calories compiled
        delay(800)
        currentStepIndex = 3 // Water & activity calculated
        delay(800)
        currentStepIndex = 4 // Weight-loss progress synthesized
        isProcessingComplete = true

        // AUTOMATICALLY SAVE TO PERMANENT HISTORY & CLEAR TEMPORARY HISTORY
        val report = DailyNutritionReportData(
            id = "report_" + System.currentTimeMillis(),
            dateTitle = "Today, Aug 15, 2025",
            score = 82,
            scoreLabel = "Good Day",
            userName = "Gogo Ji"
        )
        PermanentHistoryManager.saveDailyReport(report)
        TemporaryFoodHistoryManager.clearTemporaryHistory()
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
                    modifier = Modifier.size(36.dp).testTag("btn_eod_back")
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
                        text = "End-of-Day AI Processing",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = PrimaryTextColor
                        )
                    )
                    Text(
                        text = "Synthesizing Gogo Ji’s daily nutrition & weight loss",
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            color = SecondaryTextColor
                        )
                    )
                }
            }
        },
        containerColor = AppBackground,
        modifier = Modifier.testTag("eod_processing_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // AI Neural Ring Processing Graphic
                Box(
                    modifier = Modifier
                        .size(180.dp)
                        .scale(if (isProcessingComplete) 1f else pulseScale)
                        .clip(CircleShape)
                        .background(
                            Brush.sweepGradient(
                                listOf(MedicalBlue, HealthcareTeal, AiLavender, MedicalBlue)
                            )
                        )
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(
                                imageVector = Icons.Outlined.AutoAwesome,
                                contentDescription = null,
                                tint = AiLavender,
                                modifier = Modifier.size(48.dp)
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = if (isProcessingComplete) "100%" else "${currentStepIndex * 25}%",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp,
                                    color = PrimaryTextColor
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = if (isProcessingComplete) "Your Daily Nutrition Report is Ready!" else "Analyzing Today's Progress…",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryTextColor,
                        fontSize = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "AI is evaluating all meals, macros, water intake, activity, and weight-loss trajectory for Gogo Ji.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = SecondaryTextColor,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(28.dp))

                // Steps Breakdown Card
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, CardBorderColor),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        EodStepRow(
                            label = "Today's meals & nutrition scores analyzed",
                            isDone = currentStepIndex >= 1,
                            isActive = currentStepIndex == 0,
                            color = MedicalBlue
                        )
                        EodStepRow(
                            label = "Calories, protein & fiber totals compiled",
                            isDone = currentStepIndex >= 2,
                            isActive = currentStepIndex == 1,
                            color = HealthcareTeal
                        )
                        EodStepRow(
                            label = "Water intake & activity metrics verified",
                            isDone = currentStepIndex >= 3,
                            isActive = currentStepIndex == 2,
                            color = MedicalBlue
                        )
                        EodStepRow(
                            label = "Weight-loss insight & AI video recap synthesized",
                            isDone = currentStepIndex >= 4,
                            isActive = currentStepIndex == 3,
                            color = AiLavender
                        )
                    }
                }
            }

            // Bottom Navigation Button
            Button(
                onClick = { onNavigate(Screen.DailyNutritionReport.route) },
                enabled = isProcessingComplete,
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MedicalBlue,
                    disabledContainerColor = MedicalBlue.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp)
                    .testTag("btn_view_daily_report")
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
                            text = "Compiling Report...",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color.White,
                                fontSize = 15.sp
                            )
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Outlined.Assessment,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "View Daily Nutrition Report",
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
private fun EodStepRow(
    label: String,
    isDone: Boolean,
    isActive: Boolean,
    color: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Surface(
                shape = CircleShape,
                color = if (isDone) color.copy(alpha = 0.15f) else Color(0xFFF3F4F6),
                modifier = Modifier.size(28.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isDone) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = HealthyGreen,
                            modifier = Modifier.size(18.dp)
                        )
                    } else if (isActive) {
                        CircularProgressIndicator(
                            color = color,
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
                    fontSize = 13.sp
                )
            )
        }

        if (isDone) {
            Text(
                text = "✓ Ready",
                style = MaterialTheme.typography.labelSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = HealthyGreen,
                    fontSize = 11.sp
                )
            )
        }
    }
}
