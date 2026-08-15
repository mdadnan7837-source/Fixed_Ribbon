package com.example.ui.screens

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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Help
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.QuestionAnswer
import androidx.compose.material.icons.outlined.ReportProblem
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.Scale
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Support
import androidx.compose.material.icons.outlined.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.navigation.Screen

private val MedicalBlue = Color(0xFF2563EB)
private val HealthcareTeal = Color(0xFF0D9488)
private val AiLavender = Color(0xFFA855F7)
private val HealthyGreen = Color(0xFF22C55E)
private val MedicalAmber = Color(0xFFF59E0B)
private val ClinicalRed = Color(0xFFEF4444)
private val SoftWhite = Color(0xFFF8FAFC)
private val PrimaryText = Color(0xFF1F2937)
private val SecondaryText = Color(0xFF6B7280)
private val BorderColor = Color(0xFFE5E7EB)

@Composable
fun DataPrivacyControlsScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    var personalizedAi by remember { mutableStateOf(true) }
    var analytics by remember { mutableStateOf(true) }
    var notifications by remember { mutableStateOf(true) }
    var actionFeedback by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Data & Privacy Controls",
                subtitle = "Manage stored logs, exports & preferences",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("data_privacy_controls_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (actionFeedback.isNotEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFECFDF5),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = actionFeedback,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = HealthyGreen,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }

            // Section 1: Your Data
            item {
                Text(
                    text = "Your Data",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        DataRowItem(icon = Icons.Outlined.Restaurant, title = "Nutrition Data", detail = "Daily meals, calories & macros")
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        DataRowItem(icon = Icons.Outlined.History, title = "Food Analysis History", detail = "Scanned food photos & AI results")
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        DataRowItem(icon = Icons.Outlined.Scale, title = "Weight & Progress", detail = "Weight logs (LB) & trend charts")
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        DataRowItem(icon = Icons.Outlined.Person, title = "Profile Information", detail = "Gogo Ji's preferences & goal targets")
                    }
                }
            }

            // Section 2: Data Controls
            item {
                Text(
                    text = "Data Management",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        ActionRowItem(
                            icon = Icons.Outlined.Download,
                            iconTint = MedicalBlue,
                            title = "Download My Data",
                            subtitle = "Export JSON/CSV summary of your nutrition records",
                            onClick = { actionFeedback = "Demo: Data export prepared. Download started." },
                            testTag = "btn_download_data"
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        ActionRowItem(
                            icon = Icons.Outlined.Delete,
                            iconTint = MedicalAmber,
                            title = "Delete Food History",
                            subtitle = "Clear scanned food logs while preserving goal settings",
                            onClick = { actionFeedback = "Demo: Food analysis history cleared." },
                            testTag = "btn_delete_food_history"
                        )
                    }
                }
            }

            // Section 3: Privacy Settings
            item {
                Text(
                    text = "Privacy Settings",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        SwitchRowItem(
                            title = "Personalized AI",
                            subtitle = "Allow AI Coach to tailor suggestions based on meal history",
                            checked = personalizedAi,
                            onCheckedChange = { personalizedAi = it }
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        SwitchRowItem(
                            title = "Analytics",
                            subtitle = "Share anonymized app performance & usage diagnostic data",
                            checked = analytics,
                            onCheckedChange = { analytics = it }
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        SwitchRowItem(
                            title = "Notifications",
                            subtitle = "Receive daily meal reminders and milestone alerts",
                            checked = notifications,
                            onCheckedChange = { notifications = it }
                        )
                    }
                }
            }

            // Section 4: Danger Zone
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFEF2F2)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFCA5A5)),
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onNavigate(Screen.DeleteAccount.route) }
                        .testTag("btn_delete_account_danger")
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Delete,
                            contentDescription = null,
                            tint = ClinicalRed,
                            modifier = Modifier.size(22.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Delete My Account",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = ClinicalRed,
                                    fontSize = 15.sp
                                )
                            )
                            Text(
                                text = "Permanently remove profile, food logs and history",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF991B1B),
                                    fontSize = 12.sp
                                )
                            )
                        }
                        Icon(
                            imageVector = Icons.Outlined.ChevronRight,
                            contentDescription = null,
                            tint = ClinicalRed,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun DeleteAccountScreen(
    onBack: () -> Unit
) {
    var acknowledged by remember { mutableStateOf(false) }
    var isDeletedState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Delete Account",
                subtitle = "Permanent account removal confirmation",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("delete_account_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (isDeletedState) {
                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier.padding(20.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Check,
                                contentDescription = null,
                                tint = HealthyGreen,
                                modifier = Modifier.size(40.dp)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Demo: Account deletion completed.",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = HealthyGreen,
                                    fontSize = 16.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "This was a demonstration. No real data was permanently removed.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = SecondaryText,
                                    fontSize = 12.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = onBack,
                                colors = ButtonDefaults.buttonColors(containerColor = MedicalBlue),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Text(text = "Return to Profile", fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                }
            } else {
                item {
                    Surface(
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFFEF2F2),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFCA5A5)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Outlined.Warning,
                                    contentDescription = null,
                                    tint = ClinicalRed,
                                    modifier = Modifier.size(24.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Text(
                                    text = "Delete Account?",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = ClinicalRed,
                                        fontSize = 17.sp
                                    )
                                )
                            }
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = "Deleting your account may permanently remove your profile, nutrition history, progress data and other associated information.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFF991B1B),
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp
                                )
                            )
                        }
                    }
                }

                item {
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = "What will be removed:",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = PrimaryText,
                                    fontSize = 14.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            val itemsList = listOf(
                                "Profile name, email, and Weight Loss goals",
                                "Scanned food photo logs and nutritional scores",
                                "Weight tracking history in LB",
                                "AI Coach conversation history"
                            )
                            itemsList.forEach { text ->
                                Text(
                                    text = "• $text",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SecondaryText,
                                        fontSize = 12.sp,
                                        lineHeight = 16.sp
                                    ),
                                    modifier = Modifier.padding(vertical = 2.dp)
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { acknowledged = !acknowledged }
                            ) {
                                Checkbox(
                                    checked = acknowledged,
                                    onCheckedChange = { acknowledged = it },
                                    colors = CheckboxDefaults.colors(checkedColor = ClinicalRed),
                                    modifier = Modifier.testTag("chk_confirm_delete")
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "I understand this action may be permanent.",
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontWeight = FontWeight.SemiBold,
                                        color = PrimaryText,
                                        fontSize = 13.sp
                                    )
                                )
                            }
                        }
                    }
                }

                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        OutlinedButton(
                            onClick = onBack,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_cancel_delete"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Cancel", style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp))
                        }

                        Button(
                            onClick = { isDeletedState = true },
                            enabled = acknowledged,
                            colors = ButtonDefaults.buttonColors(containerColor = ClinicalRed),
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp)
                                .testTag("btn_confirm_delete_action"),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Delete Account", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HelpSupportScreen(
    onBack: () -> Unit
) {
    var feedbackSent by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Help & Support",
                subtitle = "FAQs, support contacts & feedback",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("help_support_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFEFF6FF),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = MedicalBlue,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = "Support Email: support@example.com",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MedicalBlue,
                                    fontSize = 13.sp
                                )
                            )
                            Text(
                                text = "DEMO CONTACT — Replace before release",
                                style = MaterialTheme.typography.bodySmall.copy(
                                    color = Color(0xFF1E40AF),
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }

            if (feedbackSent) {
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFECFDF5),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Demo: Thank you for your feedback!",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = HealthyGreen,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Frequently Asked Questions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        FaqExpandableItem(title = "Food Analysis Help", answer = "Point your camera clearly at your plate in good lighting for best AI meal recognition results.")
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        FaqExpandableItem(title = "Account Help", answer = "You can update profile preferences, health goals, and weight metrics (in LB) anytime in Settings.")
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        FaqExpandableItem(title = "Subscription Help", answer = "Premium unlocks advanced AI coaching, deep daily reports, and video recaps. Cancel anytime.")
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        FaqExpandableItem(title = "Privacy Questions", answer = "Your photos are processed strictly to calculate nutrition and are never sold to advertisers.")
                    }
                }
            }

            item {
                Text(
                    text = "Actions",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { feedbackSent = true },
                        colors = ButtonDefaults.buttonColors(containerColor = MedicalBlue),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_contact_support"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.Support, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Contact Support", style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp, fontWeight = FontWeight.Bold))
                    }

                    OutlinedButton(
                        onClick = { feedbackSent = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_send_feedback"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(imageVector = Icons.Outlined.ReportProblem, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = "Send Feedback", style = MaterialTheme.typography.titleMedium.copy(fontSize = 13.sp, fontWeight = FontWeight.Bold))
                    }
                }
            }
        }
    }
}

@Composable
fun AboutAppScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "About AI Nutrition Coach",
                subtitle = "App information and licenses",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("about_app_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = Color(0xFFF3E8FF),
                            modifier = Modifier.size(60.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.AutoAwesome,
                                    contentDescription = null,
                                    tint = AiLavender,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "AI Nutrition Coach",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Personalized AI-powered nutrition guidance.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SecondaryText,
                                fontSize = 13.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Surface(
                            shape = RoundedCornerShape(20.dp),
                            color = Color(0xFFEFF6FF)
                        ) {
                            Text(
                                text = "Version 1.0.0 (Demo)",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = MedicalBlue,
                                    fontSize = 12.sp
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                            )
                        }
                    }
                }
            }

            val aboutItems = listOf(
                "About the App" to "AI Nutrition Coach turns daily food scanning and meal logging into intelligent, actionable health insights tailored specifically to your weight loss goals.",
                "Features" to "• Instant Food Scan & Macro Estimation\n• Daily Fiber & Water Tracking\n• End-of-Day AI Video Summaries\n• Interactive AI Coach Conversations",
                "AI Nutrition Technology" to "Powered by advanced Gemini computer vision models and personalized clinical heuristics.",
                "Acknowledgements" to "Special thanks to open-source Kotlin, Jetpack Compose, and Material 3 communities.",
                "Licenses" to "Licensed under Apache License 2.0. Open source components available upon request."
            )

            items(aboutItems.size) { index ->
                val (title, content) = aboutItems[index]
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = title,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = content,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SecondaryText,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }

            item {
                Text(
                    text = "Legal Links",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        LegalLinkItem(title = "Terms & Conditions", onClick = { onNavigate(Screen.TermsConditions.route) })
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        LegalLinkItem(title = "Privacy Policy", onClick = { onNavigate(Screen.PrivacyPolicy.route) })
                        HorizontalDivider(modifier = Modifier.padding(start = 16.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)
                        LegalLinkItem(title = "Health Disclaimer", onClick = { onNavigate(Screen.HealthDisclaimer.route) })
                    }
                }
            }
        }
    }
}

@Composable
fun PremiumSubscriptionScreen(
    onBack: () -> Unit
) {
    var selectedPlan by remember { mutableStateOf("yearly") }
    var actionMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Premium Upgrade",
                subtitle = "Unlock AI coaching & deep reports",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("premium_subscription_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5FF)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE9D5FF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(
                            shape = CircleShape,
                            color = AiLavender,
                            modifier = Modifier.size(56.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(
                                    imageVector = Icons.Outlined.Star,
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(28.dp)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "AI Nutrition Coach Premium",
                            style = MaterialTheme.typography.titleLarge.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText,
                                fontSize = 20.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Elevate Gogo Ji's Weight Loss Journey",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = AiLavender,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp
                            )
                        )
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color(0xFFEFF6FF),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Outlined.Info, contentDescription = null, tint = MedicalBlue, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Final pricing and subscription terms will be configured before release.",
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MedicalBlue,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            if (actionMessage.isNotEmpty()) {
                item {
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFECFDF5),
                        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFA7F3D0)),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = actionMessage,
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = HealthyGreen,
                                fontSize = 13.sp
                            ),
                            modifier = Modifier.padding(14.dp)
                        )
                    }
                }
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Premium Features Included:",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        val features = listOf(
                            "Advanced AI Coaching" to "Unlimited voice & chat questions with AI Coach",
                            "Detailed Nutrition Insights" to "Deep micronutrient breakdown & fiber curves",
                            "Daily AI Reports" to "End-of-day audio recaps & video generation",
                            "Personalized Recommendations" to "Tailored meal suggestions for Weight Loss",
                            "Premium Progress Insights" to "Exportable trend reports & goal projections"
                        )

                        features.forEach { (title, sub) ->
                            Row(
                                modifier = Modifier.padding(vertical = 6.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = null,
                                    tint = AiLavender,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(10.dp))
                                Column {
                                    Text(
                                        text = title,
                                        style = MaterialTheme.typography.titleMedium.copy(
                                            fontWeight = FontWeight.Bold,
                                            color = PrimaryText,
                                            fontSize = 13.sp
                                        )
                                    )
                                    Text(
                                        text = sub,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            color = SecondaryText,
                                            fontSize = 11.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            }

            item {
                Text(
                    text = "Select Plan (DEMO Pricing)",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 15.sp
                    )
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    PlanCard(
                        title = "Monthly",
                        price = "$9.99 / mo",
                        subtitle = "Billed monthly",
                        isSelected = selectedPlan == "monthly",
                        onClick = { selectedPlan = "monthly" },
                        badge = "DEMO",
                        modifier = Modifier.weight(1f)
                    )

                    PlanCard(
                        title = "Yearly",
                        price = "$79.99 / yr",
                        subtitle = "Save 33%",
                        isSelected = selectedPlan == "yearly",
                        onClick = { selectedPlan = "yearly" },
                        badge = "BEST VALUE",
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            item {
                val displayPrice = if (selectedPlan == "yearly") "$79.99/yr" else "$9.99/mo"
                Button(
                    onClick = { actionMessage = "Demo: Premium subscription activated successfully!" },
                    colors = ButtonDefaults.buttonColors(containerColor = AiLavender),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("btn_subscribe_now"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Subscribe Now — Demo ($displayPrice)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "Subscription Terms",
                        style = MaterialTheme.typography.labelSmall.copy(color = MedicalBlue, fontWeight = FontWeight.Bold),
                        modifier = Modifier.clickable { actionMessage = "Demo: Terms: Auto-renews unless cancelled 24h before end of period." }
                    )
                    Text(
                        text = "Restore Purchases",
                        style = MaterialTheme.typography.labelSmall.copy(color = MedicalBlue, fontWeight = FontWeight.Bold),
                        modifier = Modifier.clickable { actionMessage = "Demo: Purchases restored successfully!" }
                    )
                }
            }
        }
    }
}

// Subcomponents
@Composable
private fun DataRowItem(icon: ImageVector, title: String, detail: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MedicalBlue, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryText, fontSize = 14.sp))
            Text(text = detail, style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText, fontSize = 12.sp))
        }
    }
}

@Composable
private fun ActionRowItem(icon: ImageVector, iconTint: Color, title: String, subtitle: String, onClick: () -> Unit, testTag: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = iconTint, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryText, fontSize = 14.sp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText, fontSize = 12.sp))
        }
        Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null, tint = Color(0xFF9CA3AF), modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun SwitchRowItem(title: String, subtitle: String, checked: Boolean, onCheckedChange: (Boolean) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryText, fontSize = 14.sp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText, fontSize = 12.sp))
        }
        Spacer(modifier = Modifier.width(10.dp))
        Switch(
            checked = checked,
            onCheckedChange = onCheckedChange,
            colors = SwitchDefaults.colors(checkedThumbColor = Color.White, checkedTrackColor = MedicalBlue)
        )
    }
}

@Composable
private fun FaqExpandableItem(title: String, answer: String) {
    var expanded by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded }
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryText, fontSize = 14.sp))
            Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null, tint = SecondaryText, modifier = Modifier.size(18.dp))
        }
        if (expanded) {
            Spacer(modifier = Modifier.height(6.dp))
            Text(text = answer, style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText, fontSize = 12.sp, lineHeight = 16.sp))
        }
    }
}

@Composable
private fun LegalLinkItem(title: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold, color = MedicalBlue, fontSize = 14.sp))
        Icon(imageVector = Icons.Outlined.ChevronRight, contentDescription = null, tint = MedicalBlue, modifier = Modifier.size(18.dp))
    }
}

@Composable
private fun PlanCard(
    title: String,
    price: String,
    subtitle: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    badge: String,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = if (isSelected) Color(0xFFFAF5FF) else Color.White),
        border = androidx.compose.foundation.BorderStroke(2.dp, if (isSelected) AiLavender else BorderColor),
        modifier = modifier.clickable { onClick() }
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Surface(
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) AiLavender else Color(0xFFF3F4F6)
            ) {
                Text(
                    text = badge,
                    style = MaterialTheme.typography.labelSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (isSelected) Color.White else SecondaryText,
                        fontSize = 10.sp
                    ),
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = title, style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryText, fontSize = 14.sp))
            Text(text = price, style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = AiLavender, fontSize = 18.sp))
            Text(text = subtitle, style = MaterialTheme.typography.bodySmall.copy(color = SecondaryText, fontSize = 11.sp))
        }
    }
}

@Composable
private fun TopHeaderBar(
    title: String,
    subtitle: String,
    onBack: () -> Unit
) {
    Surface(
        color = Color.White,
        shadowElevation = 1.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back")) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    tint = PrimaryText
                )
            }
            Spacer(modifier = Modifier.width(4.dp))
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 18.sp
                    )
                )
                if (subtitle.isNotEmpty()) {
                    Text(
                        text = subtitle,
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = SecondaryText,
                            fontSize = 12.sp
                        )
                    )
                }
            }
        }
    }
}
