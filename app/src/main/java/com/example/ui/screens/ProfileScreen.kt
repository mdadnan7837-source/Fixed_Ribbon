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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.outlined.CalendarToday
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LocalFireDepartment
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.EmojiEvents
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.ShowChart
import androidx.compose.material.icons.outlined.TrackChanges
import androidx.compose.material.icons.outlined.VerifiedUser
import androidx.compose.material.icons.outlined.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.navigation.Screen
import com.example.ui.components.AppBottomBar

@Composable
fun ProfileScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit = {},
    viewModel: ProfileViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentRoute = Screen.Profile.route,
                onNavigate = onNavigate
            )
        },
        containerColor = Color(0xFFF9FAFB),
        modifier = Modifier.testTag("profile_screen")
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Header
            val isWeightLoss = uiState.user.name == "Gogo Ji" || uiState.activeGoal.contains("Weight Loss", ignoreCase = true)
            val profileTitle = if (isWeightLoss) "Gogo Ji’s Profile" else "Profile"
            val profileSubtitle = if (isWeightLoss) "Manage Gogo Ji’s account and preferences" else "Manage your account and preferences"
            ProfileHeader(
                title = profileTitle,
                subtitle = profileSubtitle
            )

            // Scrollable Settings & Content
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 1. User Summary Hero Card
                item {
                    UserSummaryHeroCard(
                        name = uiState.user.name,
                        email = uiState.user.email,
                        goal = uiState.activeGoal,
                        memberSince = uiState.memberSince,
                        streakDays = uiState.streakDays,
                        isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE.title
                    )
                }

                // 2. Stats Overview Card (4 Metrics)
                item {
                    StatsOverviewCard(
                        avgScore = uiState.avgScore,
                        daysTracked = uiState.daysTracked,
                        waterIntake = uiState.waterIntake,
                        achievements = uiState.achievementsCount
                    )
                }

                // 3. Active Goal Card
                item {
                    ActiveGoalCard(
                        goalTitle = uiState.activeGoal,
                        onEditGoalClick = { onNavigate(Screen.Goal.route) },
                        isCancerAware = uiState.activeGoal == com.example.model.NutritionGoal.CANCER_AWARE.title
                    )
                }

                // 4. Personal Information Group
                item {
                    SettingsGroupSection(
                        title = "Personal Information",
                        items = listOf(
                            SettingsRowData(
                                icon = Icons.Outlined.Person,
                                iconBg = Color(0xFFEFF6FF),
                                iconTint = Color(0xFF2563EB),
                                title = "Personal Details",
                                subtitle = "Update your name, age, height, weight",
                                onClick = {}
                            ),
                            SettingsRowData(
                                icon = Icons.Outlined.TrackChanges,
                                iconBg = Color(0xFFF3E8FF),
                                iconTint = Color(0xFF7C3AED),
                                title = "Health Goal",
                                subtitle = uiState.activeGoal,
                                onClick = { onNavigate(Screen.Goal.route) }
                            ),
                            SettingsRowData(
                                icon = Icons.Outlined.Shield,
                                iconBg = Color(0xFFCCFBF1),
                                iconTint = Color(0xFF0D9488),
                                title = "Health Information",
                                subtitle = "Medical conditions, allergies, medications",
                                onClick = {}
                            )
                        )
                    )
                }

                // 5. Preferences Group
                item {
                    SettingsGroupSection(
                        title = "Preferences",
                        items = listOf(
                            SettingsRowData(
                                icon = Icons.Outlined.Restaurant,
                                iconBg = Color(0xFFEFF6FF),
                                iconTint = Color(0xFF2563EB),
                                title = "Dietary Preferences",
                                subtitle = uiState.dietaryPreferences,
                                onClick = {}
                            ),
                            SettingsRowData(
                                icon = Icons.Outlined.Notifications,
                                iconBg = Color(0xFFEFF6FF),
                                iconTint = Color(0xFF2563EB),
                                title = "Notifications",
                                subtitle = "Manage reminders and alerts",
                                onClick = { onNavigate(Screen.PermNotification.route) }
                            ),
                            SettingsRowData(
                                icon = Icons.Outlined.DarkMode,
                                iconBg = Color(0xFFF3E8FF),
                                iconTint = Color(0xFF7C3AED),
                                title = "Appearance",
                                subtitle = "Theme, language, units",
                                onClick = {}
                            )
                        )
                    )
                }

                // 6. Support & More Group
                item {
                    SettingsGroupSection(
                        title = "Support & More",
                        items = listOf(
                            SettingsRowData(
                                icon = Icons.Outlined.HelpOutline,
                                iconBg = Color(0xFFCCFBF1),
                                iconTint = Color(0xFF0D9488),
                                title = "Help Center",
                                subtitle = "FAQs, guides and support",
                                onClick = { onNavigate(Screen.HelpSupport.route) }
                            ),
                            SettingsRowData(
                                icon = Icons.Outlined.Shield,
                                iconBg = Color(0xFFD1FAE5),
                                iconTint = Color(0xFF059669),
                                title = "Privacy & Security",
                                subtitle = "Manage your data and privacy",
                                onClick = { onNavigate(Screen.DataPrivacy.route) }
                            ),
                            SettingsRowData(
                                icon = Icons.Outlined.Info,
                                iconBg = Color(0xFFF3E8FF),
                                iconTint = Color(0xFF7C3AED),
                                title = "About",
                                subtitle = "App version ${uiState.appVersion}",
                                onClick = { onNavigate(Screen.AboutApp.route) }
                            )
                        )
                    )
                }

                // 7. Log Out Button
                item {
                    LogOutButton(
                        onLogoutClick = {
                            viewModel.logout {
                                onNavigate(Screen.Login.route)
                            }
                        }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

@Composable
private fun ProfileHeader(
    title: String,
    subtitle: String
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

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Notification Bell with Badge
            Box {
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                    modifier = Modifier.size(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Notifications",
                            tint = Color(0xFF374151),
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
                // Badge "3"
                Surface(
                    shape = CircleShape,
                    color = Color(0xFF2563EB),
                    modifier = Modifier
                        .size(18.dp)
                        .align(Alignment.TopEnd)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = "3",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 10.sp
                            )
                        )
                    }
                }
            }

            // Settings Gear Icon
            Surface(
                shape = CircleShape,
                color = Color.White,
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB)),
                modifier = Modifier.size(40.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Settings",
                        tint = Color(0xFF374151),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun UserSummaryHeroCard(
    name: String,
    email: String,
    goal: String,
    memberSince: String,
    streakDays: Int,
    isCancerAware: Boolean = false
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
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFAF5FF),
                            Color(0xFFF3E8FF),
                            Color(0xFFF5F3FF)
                        )
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .border(1.dp, Color(0xFFF3E8FF), RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Left avatar + User details column
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 3D Avatar Illustration with edit badge
                    Box(modifier = Modifier.size(72.dp)) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val w = size.width
                            val h = size.height

                            // Background circle glow
                            drawCircle(
                                color = Color(0xFFE9D5FF),
                                radius = w * 0.48f,
                                center = Offset(w * 0.5f, h * 0.5f)
                            )
                            // Hair / Head 3D stylized illustration
                            drawCircle(
                                color = Color(0xFF7E22CE),
                                radius = w * 0.38f,
                                center = Offset(w * 0.5f, h * 0.42f)
                            )
                            // Face skin
                            drawCircle(
                                color = Color(0xFFFED7AA),
                                radius = w * 0.28f,
                                center = Offset(w * 0.5f, h * 0.46f)
                            )
                            // Eyes
                            drawCircle(color = Color(0xFF1E293B), radius = 2.5.dp.toPx(), center = Offset(w * 0.42f, h * 0.44f))
                            drawCircle(color = Color(0xFF1E293B), radius = 2.5.dp.toPx(), center = Offset(w * 0.58f, h * 0.44f))
                            // Smile
                            drawArc(
                                color = Color(0xFF991B1B),
                                startAngle = 10f,
                                sweepAngle = 160f,
                                useCenter = false,
                                topLeft = Offset(w * 0.43f, h * 0.50f),
                                size = Size(w * 0.14f, h * 0.1f),
                                style = Stroke(width = 1.5.dp.toPx())
                            )
                            // Purple shirt
                            drawArc(
                                color = Color(0xFFA855F7),
                                startAngle = 0f,
                                sweepAngle = 180f,
                                useCenter = true,
                                topLeft = Offset(w * 0.18f, h * 0.62f),
                                size = Size(w * 0.64f, h * 0.36f)
                            )
                        }

                        // Small edit badge on bottom-right of avatar
                        Surface(
                            shape = CircleShape,
                            color = Color.White,
                            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFD8B4FE)),
                            modifier = Modifier
                                .size(24.dp)
                                .align(Alignment.BottomEnd)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.Edit,
                                    contentDescription = "Edit Avatar",
                                    tint = Color(0xFF7C3AED),
                                    modifier = Modifier.size(12.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column {
                        Text(
                            text = name,
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF111827)
                            )
                        )
                        Spacer(modifier = Modifier.height(2.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = email,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontSize = 12.sp,
                                    color = Color(0xFF4B5563)
                                ),
                                maxLines = if (isCancerAware) 1 else Int.MAX_VALUE,
                                overflow = if (isCancerAware) TextOverflow.Ellipsis else TextOverflow.Clip,
                                modifier = if (isCancerAware) Modifier.weight(1f, fill = false) else Modifier
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Outlined.VerifiedUser,
                                contentDescription = "Verified",
                                tint = Color(0xFF7C3AED),
                                modifier = Modifier.size(14.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        // Goal Badge Pill
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF3E8FF)
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                if (isCancerAware) {
                                    CancerAwareRibbonCanvas(modifier = Modifier.size(12.dp))
                                } else {
                                    Text(
                                        text = "🎗",
                                        fontSize = 11.sp
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = goal,
                                    style = MaterialTheme.typography.labelSmall.copy(
                                        color = Color(0xFF7C3AED),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 11.sp
                                    ),
                                    maxLines = if (isCancerAware) 1 else Int.MAX_VALUE,
                                    overflow = if (isCancerAware) TextOverflow.Ellipsis else TextOverflow.Clip,
                                    softWrap = !isCancerAware
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Right Side Stats: Member since & Streak
                Column(
                    horizontalAlignment = Alignment.End,
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    // Member since
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.CalendarToday,
                            contentDescription = null,
                            tint = Color(0xFF9333EA),
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Member Since",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    color = Color(0xFF6B7280)
                                )
                            )
                            Text(
                                text = memberSince,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 11.sp,
                                    color = Color(0xFF1F2937)
                                )
                            )
                        }
                    }

                    // Streak
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.LocalFireDepartment,
                            contentDescription = null,
                            tint = Color(0xFFF97316),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Column(horizontalAlignment = Alignment.End) {
                            Text(
                                text = "Current Streak",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    fontSize = 9.sp,
                                    color = Color(0xFF6B7280)
                                )
                            )
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "$streakDays ",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF7C3AED)
                                    )
                                )
                                Text(
                                    text = "days",
                                    style = MaterialTheme.typography.labelMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp,
                                        color = Color(0xFF1F2937)
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

// Cancer-Awareness ribbon: two crossed loops meeting at a point, drawn as filled
// paths so it reads as a real ribbon — matches the ribbon used on the Cancer-Aware
// Home page banner. Reused here (Profile) at whatever size the caller requests.
@Composable
private fun CancerAwareRibbonCanvas(
    modifier: Modifier = Modifier,
    color: Color = Color(0xFF7C3AED)
) {
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
            cubicTo(
                cx - loopW, cy - loopH * 0.9f,
                cx - loopW * 1.15f, cy + loopH * 0.55f,
                cx, cy
            )
            close()
        }
        val rightLoop = Path().apply {
            moveTo(cx, cy)
            cubicTo(
                cx + loopW, cy - loopH * 0.9f,
                cx + loopW * 1.15f, cy + loopH * 0.55f,
                cx, cy
            )
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

        drawPath(tail, color = color.copy(alpha = 0.85f))
        drawPath(leftLoop, color = color)
        drawPath(rightLoop, color = color.copy(alpha = 0.92f))
    }
}

@Composable
private fun StatsOverviewCard(
    avgScore: Int,
    daysTracked: Int,
    waterIntake: String,
    achievements: Int
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Avg. Score
            StatColumnItem(
                icon = Icons.Outlined.Shield,
                iconBg = Color(0xFFCCFBF1),
                iconTint = Color(0xFF0D9488),
                value = "$avgScore",
                title = "Avg. Nutrition Score",
                subtitle = "This Week",
                subtitleColor = Color(0xFF0D9488),
                modifier = Modifier.weight(1f)
            )

            // 2. Days Tracked
            StatColumnItem(
                icon = Icons.Outlined.ShowChart,
                iconBg = Color(0xFFE0F2FE),
                iconTint = Color(0xFF2563EB),
                value = "$daysTracked",
                title = "Days Tracked",
                subtitle = "This Month",
                subtitleColor = Color(0xFF2563EB),
                modifier = Modifier.weight(1f)
            )

            // 3. Water Intake
            StatColumnItem(
                icon = Icons.Outlined.WaterDrop,
                iconBg = Color(0xFFCCFBF1),
                iconTint = Color(0xFF0D9488),
                value = waterIntake,
                title = "Water Intake",
                subtitle = "Daily Average",
                subtitleColor = Color(0xFF0D9488),
                modifier = Modifier.weight(1f)
            )

            // 4. Achievements
            StatColumnItem(
                icon = Icons.Outlined.EmojiEvents,
                iconBg = Color(0xFFF3E8FF),
                iconTint = Color(0xFF7C3AED),
                value = "$achievements",
                title = "Achievements",
                subtitle = "Unlocked",
                subtitleColor = Color(0xFF7C3AED),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
private fun StatColumnItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    value: String,
    title: String,
    subtitle: String,
    subtitleColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            shape = CircleShape,
            color = iconBg,
            modifier = Modifier.size(36.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color(0xFF111827)
            )
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Medium,
                fontSize = 10.sp,
                color = Color(0xFF4B5563)
            ),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            lineHeight = 12.sp,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = subtitle,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 10.sp,
                color = subtitleColor
            )
        )
    }
}

@Composable
private fun ActiveGoalCard(
    goalTitle: String,
    onEditGoalClick: () -> Unit,
    isCancerAware: Boolean = false
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Color(0xFFFAF5FF),
                            Color(0xFFF3E8FF)
                        )
                    ),
                    shape = RoundedCornerShape(16.dp)
                )
                .border(1.dp, Color(0xFFF3E8FF), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Awareness Ribbon graphic frame
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEDE9FE),
                    modifier = Modifier.size(60.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (isCancerAware) {
                            CancerAwareRibbonCanvas(modifier = Modifier.size(36.dp))
                        } else {
                            Canvas(modifier = Modifier.size(36.dp)) {
                                val w = size.width
                                val h = size.height

                                // Draw ribbon loop
                                val path = Path().apply {
                                    moveTo(w * 0.5f, h * 0.15f)
                                    cubicTo(w * 0.1f, h * 0.35f, w * 0.2f, h * 0.75f, w * 0.25f, h * 0.85f)
                                    cubicTo(w * 0.35f, h * 0.65f, w * 0.65f, h * 0.65f, w * 0.75f, h * 0.85f)
                                    cubicTo(w * 0.8f, h * 0.75f, w * 0.9f, h * 0.35f, w * 0.5f, h * 0.15f)
                                    close()
                                }
                                drawPath(path, color = Color(0xFFA855F7))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Your Goal",
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF7C3AED),
                            fontSize = 11.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = goalTitle,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            lineHeight = if (isCancerAware) 18.sp else MaterialTheme.typography.titleMedium.lineHeight,
                            color = Color(0xFF111827)
                        ),
                        maxLines = if (isCancerAware) 2 else Int.MAX_VALUE,
                        overflow = if (isCancerAware) TextOverflow.Ellipsis else TextOverflow.Clip
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = if (isCancerAware) {
                            "You're building healthy eating habits focused on fiber, fruits & vegetables, whole grains, and limiting processed meat."
                        } else {
                            "You're building healthy habits that support your health and well-being."
                        },
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            color = Color(0xFF4B5563)
                        )
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Outlined Edit Goal Button
                Surface(
                    shape = CircleShape,
                    color = Color.White,
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFC084FC)),
                    modifier = Modifier.clickable { onEditGoalClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = null,
                            tint = Color(0xFF7C3AED),
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Edit Goal",
                            style = MaterialTheme.typography.labelMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF7C3AED),
                                fontSize = 12.sp
                            )
                        )
                    }
                }
            }
        }
    }
}

private data class SettingsRowData(
    val icon: ImageVector,
    val iconBg: Color,
    val iconTint: Color,
    val title: String,
    val subtitle: String,
    val onClick: () -> Unit
)

@Composable
private fun SettingsGroupSection(
    title: String,
    items: List<SettingsRowData>
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
                color = Color(0xFF111827)
            ),
            modifier = Modifier.padding(start = 4.dp, bottom = 8.dp)
        )

        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                items.forEachIndexed { index, item ->
                    SettingsRow(
                        data = item,
                        showDivider = index < items.size - 1
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingsRow(
    data: SettingsRowData,
    showDivider: Boolean
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { data.onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icon
            Surface(
                shape = CircleShape,
                color = data.iconBg,
                modifier = Modifier.size(36.dp)
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        imageVector = data.icon,
                        contentDescription = null,
                        tint = data.iconTint,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Title & Subtitle
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = data.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp,
                        color = Color(0xFF111827)
                    )
                )
                if (data.subtitle.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = data.subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontSize = 12.sp,
                            color = Color(0xFF6B7280)
                        )
                    )
                }
            }

            // Chevron
            Icon(
                imageVector = Icons.Outlined.ChevronRight,
                contentDescription = null,
                tint = Color(0xFF9CA3AF),
                modifier = Modifier.size(18.dp)
            )
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 64.dp, end = 16.dp),
                thickness = 0.5.dp,
                color = Color(0xFFF3F4F6)
            )
        }
    }
}

@Composable
private fun LogOutButton(
    onLogoutClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFFEF2F2),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFCA5A5)),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onLogoutClick() }
            .testTag("btn_logout")
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 14.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                contentDescription = "Log Out",
                tint = Color(0xFFDC2626),
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Log Out",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = Color(0xFFDC2626)
                )
            )
        }
    }
}
