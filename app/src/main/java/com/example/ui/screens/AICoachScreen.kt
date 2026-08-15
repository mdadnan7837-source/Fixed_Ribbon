package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.outlined.ChatBubbleOutline
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.Grain
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.ChatMessage
import com.example.model.CoachInsight
import com.example.model.MessageRole
import com.example.model.TodayOverviewItem
import com.example.navigation.Screen
import com.example.ui.components.AppBottomBar

// Cancer-Aware-only accent colors (per official Cancer-Aware design spec).
// Applied only when activeGoal == CANCER_AWARE; every other goal keeps the
// existing blue/teal AI Coach styling untouched.
private val CancerAccent = Color(0xFF7C3AED)
private val CancerAccentLight = Color(0xFFF3E8FF)
private val CancerBubbleBg = Color(0xFFF3E8FF)
private val CancerBubbleText = Color(0xFF7C3AED)

@Composable
fun AICoachScreen(
    viewModel: AICoachViewModel,
    onNavigate: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsState()
    val chatListState = rememberLazyListState()
    val isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE

    LaunchedEffect(uiState.chatMessages.size) {
        if (uiState.chatMessages.isNotEmpty()) {
            chatListState.animateScrollToItem(uiState.chatMessages.size - 1)
        }
    }

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = Screen.AICoach.route,
                onNavigate = onNavigate
            )
        },
        containerColor = Color(0xFFF9FAFB),
        modifier = modifier.testTag("ai_coach_screen")
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            AICoachHeader(
                title = "AI Coach",
                subtitle = "Your personal nutrition expert",
                isCancerAware = isCancerAware
            )

            // Content Scroll Area
            LazyColumn(
                state = chatListState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. AI Coach Greeting Banner Card
                item {
                    CoachGreetingBannerCard(
                        userName = uiState.userName,
                        isCancerAware = isCancerAware,
                        onQuickActionClick = { prompt -> viewModel.onQuickPromptClicked(prompt) }
                    )
                }

                // 2. Today's Overview
                item {
                    TodayOverviewSection(
                        items = uiState.todayOverview,
                        onViewDetailsClick = { onNavigate(Screen.Progress.route) }
                    )
                }

                // 3. Coach Insights
                item {
                    uiState.coachInsight?.let { insight ->
                        CoachInsightsCard(insight = insight)
                    }
                }

                // 4. Chat Header & Messages
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp, bottom = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Chat with AI Coach",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF111827)
                            )
                        )
                        TextButton(
                            onClick = { viewModel.onClearChat() },
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Text(
                                text = "Clear Chat",
                                style = MaterialTheme.typography.labelLarge.copy(
                                    color = Color(0xFF2563EB),
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                items(uiState.chatMessages, key = { it.id }) { msg ->
                    ChatMessageBubble(message = msg, isCancerAware = isCancerAware)
                }

                // 5. Quick Prompts
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    ) {
                        QuickPromptChips(
                            prompts = uiState.quickPrompts,
                            onPromptClick = { prompt -> viewModel.onQuickPromptClicked(prompt) }
                        )
                    }
                }
            }

            // 6. Interactive Chat Input Bar
            ChatInputField(
                inputText = uiState.inputText,
                onTextChanged = { viewModel.onInputTextChanged(it) },
                onSendClick = { viewModel.onSendMessage() },
                isSending = uiState.isSending,
                isCancerAware = isCancerAware
            )
        }
    }
}

@Composable
private fun AICoachHeader(
    title: String,
    subtitle: String,
    isCancerAware: Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.headlineMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 26.sp,
                    color = Color(0xFF111827)
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 13.sp,
                    color = Color(0xFF6B7280)
                )
            )
        }

        Surface(
            shape = CircleShape,
            color = Color.Transparent,
            border = androidx.compose.foundation.BorderStroke(
                1.5.dp,
                if (isCancerAware) CancerAccent else Color(0xFF2563EB)
            ),
            modifier = Modifier.size(32.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "i",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isCancerAware) CancerAccent else Color(0xFF2563EB),
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}

@Composable
private fun CoachGreetingBannerCard(
    userName: String,
    isCancerAware: Boolean = false,
    onQuickActionClick: (String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = if (isCancerAware) {
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFFAF5FF),
                                Color(0xFFF3E8FF),
                                Color(0xFFF5F3FF)
                            )
                        )
                    } else {
                        Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFEBF7FC),
                                Color(0xFFE0F2FE),
                                Color(0xFFF0FDF4)
                            )
                        )
                    },
                    shape = RoundedCornerShape(20.dp)
                )
                .padding(16.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp)
                    ) {
                        Text(
                            text = if (userName == "Gogo Ji") "Hi Gogo Ji 👋" else "Hi, $userName! 👋",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp,
                                color = Color(0xFF0F172A)
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "I'm your AI Nutrition Coach.\nHow can I help you today?",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontSize = 13.sp,
                                lineHeight = 18.sp,
                                color = Color(0xFF334155)
                            )
                        )
                    }

                    // Robot Avatar Graphic
                    RobotGraphicIllustration(modifier = Modifier.size(80.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Quick action buttons row
                val scrollState = rememberScrollState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(scrollState),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    QuickActionButton(
                        icon = Icons.Outlined.Eco,
                        label = "Nutrition Advice",
                        color = Color(0xFF059669),
                        onClick = { onQuickActionClick("Nutrition Advice") }
                    )
                    QuickActionButton(
                        icon = Icons.Outlined.Restaurant,
                        label = "Meal Ideas",
                        color = Color(0xFF2563EB),
                        onClick = { onQuickActionClick("Meal Ideas") }
                    )
                    QuickActionButton(
                        icon = Icons.Outlined.Shield,
                        label = "Health Insights",
                        color = Color(0xFF0D9488),
                        onClick = { onQuickActionClick("Health Insights") }
                    )
                    QuickActionButton(
                        icon = Icons.Outlined.ChatBubbleOutline,
                        label = "Ask Anything",
                        color = Color(0xFF7C3AED),
                        onClick = { onQuickActionClick("Ask Anything") }
                    )
                }
            }
        }
    }
}

@Composable
private fun QuickActionButton(
    icon: ImageVector,
    label: String,
    color: Color,
    onClick: () -> Unit
) {
    Surface(
        shape = CircleShape,
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(16.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = color
                )
            )
        }
    }
}

@Composable
private fun RobotGraphicIllustration(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Glow / speech bubble in background
            drawCircle(
                color = Color(0xFF38BDF8).copy(alpha = 0.15f),
                radius = w * 0.45f,
                center = Offset(w * 0.5f, h * 0.5f)
            )

            // Robot head
            drawRoundRect(
                color = Color(0xFF0284C7),
                topLeft = Offset(w * 0.25f, h * 0.2f),
                size = Size(w * 0.5f, h * 0.38f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(16.dp.toPx())
            )
            // Visor
            drawRoundRect(
                color = Color(0xFF0F172A),
                topLeft = Offset(w * 0.3f, h * 0.26f),
                size = Size(w * 0.4f, h * 0.2f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(10.dp.toPx())
            )
            // Eyes
            drawCircle(
                color = Color(0xFF38BDF8),
                radius = 3.5.dp.toPx(),
                center = Offset(w * 0.42f, h * 0.35f)
            )
            drawCircle(
                color = Color(0xFF38BDF8),
                radius = 3.5.dp.toPx(),
                center = Offset(w * 0.58f, h * 0.35f)
            )

            // Robot Body
            drawRoundRect(
                color = Color(0xFF0369A1),
                topLeft = Offset(w * 0.22f, h * 0.62f),
                size = Size(w * 0.56f, h * 0.32f),
                cornerRadius = androidx.compose.ui.geometry.CornerRadius(14.dp.toPx())
            )

            // Heart / Emblem on chest
            drawCircle(
                color = Color(0xFF0D9488),
                radius = 7.dp.toPx(),
                center = Offset(w * 0.5f, h * 0.78f)
            )
        }
    }
}

@Composable
private fun TodayOverviewSection(
    items: List<TodayOverviewItem>,
    onViewDetailsClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
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
                    text = "Today's Overview",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color(0xFF111827)
                    )
                )

                Row(
                    modifier = Modifier.clickable { onViewDetailsClick() },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "View Details",
                        style = MaterialTheme.typography.labelMedium.copy(
                            color = Color(0xFF2563EB),
                            fontWeight = FontWeight.Medium
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        contentDescription = null,
                        tint = Color(0xFF2563EB),
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // 5 Nutrient overview rings row
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(items, key = { it.id }) { item ->
                    NutrientRingItemCard(item = item)
                }
            }
        }
    }
}

@Composable
private fun NutrientRingItemCard(item: TodayOverviewItem) {
    val themeColor = when (item.id) {
        "score" -> Color(0xFF2563EB)
        "fiber" -> Color(0xFF16A34A)
        "fruits" -> Color(0xFF0D9488)
        "grains" -> Color(0xFFD97706)
        "meat" -> Color(0xFFDC2626)
        else -> Color(0xFF2563EB)
    }

    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFF9FAFB),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3F4F6)),
        modifier = Modifier.width(108.dp)
    ) {
        Column(
            modifier = Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = item.name,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = Color(0xFF374151)
                ),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Ring gauge
            Box(
                modifier = Modifier.size(56.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(modifier = Modifier.fillMaxSize()) {
                    val strokeWidth = 5.dp.toPx()
                    val progress = (item.current / item.target).coerceIn(0.0, 1.0).toFloat()

                    // Background track
                    drawArc(
                        color = themeColor.copy(alpha = 0.15f),
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                    // Progress arc
                    drawArc(
                        color = themeColor,
                        startAngle = -90f,
                        sweepAngle = 360f * progress,
                        useCenter = false,
                        style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                    )
                }

                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = item.currentDisplay,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color(0xFF111827)
                        )
                    )
                    Text(
                        text = item.targetDisplay,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 9.sp,
                            color = Color(0xFF9CA3AF)
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = item.statusText,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.sp,
                    color = themeColor
                )
            )
        }
    }
}

@Composable
private fun CoachInsightsCard(insight: CoachInsight) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Coach Insights",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF111827)
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color(0xFFF0FDF4),
                                Color(0xFFECFDF5)
                            )
                        ),
                        shape = RoundedCornerShape(14.dp)
                    )
                    .border(1.dp, Color(0xFFA7F3D0), RoundedCornerShape(14.dp))
                    .padding(12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Shield Icon Badge
                    Surface(
                        shape = RoundedCornerShape(10.dp),
                        color = Color(0xFFCCFBF1),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                imageVector = Icons.Outlined.Shield,
                                contentDescription = null,
                                tint = Color(0xFF0D9488),
                                modifier = Modifier.size(22.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = insight.title,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = Color(0xFF0F766E)
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = insight.body,
                            style = MaterialTheme.typography.bodySmall.copy(
                                fontSize = 12.sp,
                                lineHeight = 16.sp,
                                color = Color(0xFF374151)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    // Food Graphic / Salad Bowl
                    SaladBowlIllustration(modifier = Modifier.size(54.dp))
                }
            }
        }
    }
}

@Composable
private fun SaladBowlIllustration(modifier: Modifier = Modifier) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        // Bowl base
        val bowlPath = Path().apply {
            moveTo(w * 0.15f, h * 0.5f)
            cubicTo(
                w * 0.2f, h * 0.9f,
                w * 0.8f, h * 0.9f,
                w * 0.85f, h * 0.5f
            )
            close()
        }
        drawPath(bowlPath, color = Color(0xFFE2E8F0))

        // Greens / Vegetables in bowl
        drawCircle(color = Color(0xFF22C55E), radius = w * 0.22f, center = Offset(w * 0.35f, h * 0.45f))
        drawCircle(color = Color(0xFF16A34A), radius = w * 0.24f, center = Offset(w * 0.6f, h * 0.42f))
        drawCircle(color = Color(0xFFEF4444), radius = w * 0.08f, center = Offset(w * 0.48f, h * 0.38f)) // Tomato
        drawCircle(color = Color(0xFFF59E0B), radius = w * 0.07f, center = Offset(w * 0.32f, h * 0.36f)) // Carrot
    }
}

@Composable
private fun ChatMessageBubble(message: ChatMessage, isCancerAware: Boolean = false) {
    val isUser = message.role == MessageRole.USER

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Row(
            modifier = Modifier.widthIn(max = 320.dp),
            horizontalArrangement = if (isUser) Arrangement.End else Arrangement.Start,
            verticalAlignment = Alignment.Top
        ) {
            if (!isUser) {
                // Robot Avatar
                Surface(
                    shape = CircleShape,
                    color = if (isCancerAware) CancerAccentLight else Color(0xFFE0F2FE),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.Eco,
                            contentDescription = "AI Coach",
                            tint = if (isCancerAware) CancerAccent else Color(0xFF0284C7),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.width(8.dp))
            }

            Column(horizontalAlignment = if (isUser) Alignment.End else Alignment.Start) {
                Surface(
                    shape = if (isUser) {
                        RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                    } else {
                        RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
                    },
                    color = if (isUser) {
                        if (isCancerAware) CancerBubbleBg else Color(0xFFEFF6FF)
                    } else {
                        Color(0xFFF3F4F6)
                    },
                    modifier = Modifier.padding(vertical = 2.dp)
                ) {
                    Text(
                        text = message.content,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontSize = 13.sp,
                            lineHeight = 19.sp,
                            color = if (isUser) {
                                if (isCancerAware) CancerBubbleText else Color(0xFF1D4ED8)
                            } else {
                                Color(0xFF1F2937)
                            }
                        ),
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp)
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = message.timestamp,
                    style = MaterialTheme.typography.bodySmall.copy(
                        fontSize = 10.sp,
                        color = Color(0xFF9CA3AF)
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
            }

            if (isUser) {
                Spacer(modifier = Modifier.width(8.dp))
                // User Avatar
                Surface(
                    shape = CircleShape,
                    color = if (isCancerAware) CancerAccent else Color(0xFF2563EB),
                    modifier = Modifier.size(34.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "User",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun QuickPromptChips(
    prompts: List<String>,
    onPromptClick: (String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(prompts) { prompt ->
            Surface(
                shape = CircleShape,
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFF99F6E4)),
                modifier = Modifier.clickable { onPromptClick(prompt) }
            ) {
                Text(
                    text = prompt,
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = Color(0xFF0D9488),
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp
                    ),
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp)
                )
            }
        }
    }
}

@Composable
private fun ChatInputField(
    inputText: String,
    onTextChanged: (String) -> Unit,
    onSendClick: () -> Unit,
    isSending: Boolean,
    isCancerAware: Boolean = false
) {
    Surface(
        color = Color.White,
        shadowElevation = 8.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = inputText,
                onValueChange = onTextChanged,
                placeholder = {
                    Text(
                        text = "Type your message...",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF9CA3AF),
                            fontSize = 14.sp
                        )
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .testTag("chat_input_field"),
                shape = CircleShape,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = if (isCancerAware) CancerAccent else Color(0xFF2563EB),
                    unfocusedBorderColor = Color(0xFFE5E7EB),
                    focusedContainerColor = Color(0xFFF9FAFB),
                    unfocusedContainerColor = Color(0xFFF9FAFB)
                ),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                keyboardActions = KeyboardActions(onSend = { onSendClick() })
            )

            Spacer(modifier = Modifier.width(10.dp))

            Surface(
                shape = CircleShape,
                color = if (inputText.isNotBlank()) {
                    if (isCancerAware) CancerAccent else Color(0xFF0284C7)
                } else {
                    if (isCancerAware) Color(0xFFD8B4FE) else Color(0xFF93C5FD)
                },
                modifier = Modifier
                    .size(44.dp)
                    .clickable(enabled = inputText.isNotBlank() && !isSending) { onSendClick() }
                    .testTag("chat_send_button")
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = "Send",
                        tint = Color.White,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}
