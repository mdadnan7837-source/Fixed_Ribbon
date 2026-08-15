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
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material.icons.outlined.Gavel
import androidx.compose.material.icons.outlined.HealthAndSafety
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MedicalInformation
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Shield
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

// Colors matching Design System
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
fun LegalPrivacyMenuScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Legal & Privacy",
                subtitle = "Terms, health disclaimers and data controls",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("legal_privacy_menu_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                DemoNoticeBanner(text = "DEMO CONTENT — Legal documents are placeholders for development")
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        LegalMenuItem(
                            icon = Icons.Outlined.Gavel,
                            iconBg = Color(0xFFEFF6FF),
                            iconTint = MedicalBlue,
                            title = "Terms & Conditions",
                            subtitle = "Usage rules, user rights and responsibilities",
                            onClick = { onNavigate(Screen.TermsConditions.route) },
                            testTag = "btn_terms_conditions"
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)

                        LegalMenuItem(
                            icon = Icons.Outlined.Policy,
                            iconBg = Color(0xFFECFDF5),
                            iconTint = HealthyGreen,
                            title = "Privacy Policy",
                            subtitle = "How we collect, use and protect your data",
                            onClick = { onNavigate(Screen.PrivacyPolicy.route) },
                            testTag = "btn_privacy_policy"
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)

                        LegalMenuItem(
                            icon = Icons.Outlined.HealthAndSafety,
                            iconBg = Color(0xFFFFFBEB),
                            iconTint = MedicalAmber,
                            title = "Medical / Health Disclaimer",
                            subtitle = "General nutrition guidance notice & safety",
                            onClick = { onNavigate(Screen.HealthDisclaimer.route) },
                            testTag = "btn_health_disclaimer"
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)

                        LegalMenuItem(
                            icon = Icons.Outlined.AutoAwesome,
                            iconBg = Color(0xFFF3E8FF),
                            iconTint = AiLavender,
                            title = "AI & Consent",
                            subtitle = "How AI analysis works and accuracy disclosure",
                            onClick = { onNavigate(Screen.AiConsent.route) },
                            testTag = "btn_ai_consent"
                        )
                        HorizontalDivider(modifier = Modifier.padding(start = 56.dp, end = 16.dp), color = BorderColor, thickness = 0.5.dp)

                        LegalMenuItem(
                            icon = Icons.Outlined.Lock,
                            iconBg = Color(0xFFCCFBF1),
                            iconTint = HealthcareTeal,
                            title = "Data & Privacy Controls",
                            subtitle = "Manage data retention, exports, and deletion",
                            onClick = { onNavigate(Screen.DataPrivacy.route) },
                            testTag = "btn_data_privacy_controls"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TermsConditionsScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Terms & Conditions",
                subtitle = "Last updated: August 2026",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("terms_conditions_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DemoNoticeBanner(text = "DEMO CONTENT — Replace before Play Store release")
            }

            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Demo Terms & Conditions",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText,
                                fontSize = 17.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "This page contains placeholder terms for the development version of AI Nutrition Coach. Final legal terms will be reviewed and updated prior to public distribution.",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = SecondaryText,
                                fontSize = 13.sp,
                                lineHeight = 18.sp
                            )
                        )
                    }
                }
            }

            val sections = listOf(
                "Acceptance of Terms" to "By downloading, accessing, or using AI Nutrition Coach, you agree to be bound by these Terms & Conditions. If you do not agree to all terms, please refrain from using the application.",
                "Use of the App" to "AI Nutrition Coach is designed to assist users in tracking meals, monitoring nutritional goals, and receiving personalized AI-driven wellness suggestions. The app is for personal, non-commercial use only.",
                "User Responsibilities" to "You are responsible for maintaining the accuracy of your profile information, food intake logs, and securing your account credentials.",
                "AI-Generated Information" to "AI Nutrition Coach utilizes machine learning models to analyze food images and generate nutritional estimates. AI output is for informational purposes only and should be verified for critical health decisions.",
                "Food & Nutrition Information" to "Calorie counts, macronutrient breakdowns, and micronutrient values are approximate estimates derived from standardized databases and computer vision algorithms.",
                "Account Responsibilities" to "You may not share your account login or attempt to bypass security features. Misuse of the service may result in temporary or permanent suspension.",
                "Premium Services" to "Subscription features provide access to advanced AI coaching and in-depth video summaries. Billing schedules and cancellation rules apply as described in the Premium terms.",
                "Intellectual Property" to "All visual designs, branding, algorithms, and software code within AI Nutrition Coach are protected under copyright and intellectual property laws.",
                "Limitation of Liability" to "AI Nutrition Coach and its creators shall not be held liable for any dietary mistakes, health complications, or technical outages resulting from the use of this app.",
                "Changes to Terms" to "We reserve the right to modify these terms at any time. Continued use of the app after updates constitutes acceptance of the new terms.",
                "Contact Information" to "For inquiries regarding these terms, please contact: support@example.com (DEMO CONTACT — Replace before release)."
            )

            items(sections.size) { index ->
                val (title, content) = sections[index]
                LegalSectionCard(number = "${index + 1}", title = title, content = content)
            }
        }
    }
}

@Composable
fun PrivacyPolicyScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Privacy Policy",
                subtitle = "How we protect and manage your data",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("privacy_policy_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DemoNoticeBanner(text = "Demo Privacy Policy — Final privacy policy will be added before public release.")
            }

            val privacySections = listOf(
                "Information We Collect" to "We collect information you provide directly to us when setting up your profile, logging food, taking photos, or contacting support.",
                "Account Information" to "Includes your name (e.g. Gogo Ji), email address, health goals (e.g. Weight Loss), and profile preferences.",
                "Nutrition & Food Data" to "Logs of your daily meals, calorie targets, macro ratios, fiber intake, and meal time logs.",
                "Weight & Progress Data" to "Weight logs (in LB), body measurements, and historical nutrition progress curves.",
                "Photos" to "Food photos taken with your camera or uploaded from your gallery strictly for AI image recognition and nutrition extraction.",
                "Voice Input" to "Audio snippets recorded when asking the AI Coach questions or using hands-free meal logging.",
                "Device Information" to "Operating system version, device model, app performance logs, and anonymized diagnostic reports.",
                "How We Use Information" to "To provide personalized nutrition feedback, calculate daily score metrics, train localized models, and improve user experience.",
                "Data Storage" to "Your personal nutrition logs are encrypted locally using Room persistence and transmitted securely over SSL/TLS.",
                "Data Sharing" to "We do not sell your personal health or nutrition data to third-party advertisers or data brokers.",
                "Third-Party Services" to "Third-party AI providers (e.g., Gemini API) receive meal images and prompts solely to generate nutrition insights.",
                "Data Retention" to "Nutrition records are retained as long as your account remains active. You can request data deletion at any time.",
                "User Rights" to "You have the right to access, export, modify, or permanently delete all stored health and nutrition records.",
                "Account Deletion" to "Account deletion removes your profile, meal history, and AI records within 30 days of request.",
                "Contact Us" to "For privacy inquiries, contact privacy@example.com (DEMO CONTACT — Replace before release)."
            )

            items(privacySections.size) { index ->
                val (title, content) = privacySections[index]
                LegalSectionCard(number = "${index + 1}", title = title, content = content)
            }
        }
    }
}

@Composable
fun HealthDisclaimerScreen(
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "Health Disclaimer",
                subtitle = "Important medical notice & safety information",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("health_disclaimer_screen")
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                DemoNoticeBanner(text = "DEMO DISCLAIMER — Final legal/medical wording will be reviewed before release.")
            }

            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFFBEB),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.Top
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Warning,
                            contentDescription = null,
                            tint = MedicalAmber,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Medical Disclaimer Notice",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF92400E),
                                    fontSize = 15.sp
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "AI Nutrition Coach provides general nutrition and wellness information. It is not a substitute for professional medical advice, diagnosis, or treatment.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = Color(0xFFB45309),
                                    fontSize = 13.sp,
                                    lineHeight = 18.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
            }

            val healthSections = listOf(
                "General Information" to "The contents of this app, including AI text, food breakdown scores, calorie suggestions, and video recaps, are created for general educational and dietary tracking purposes.",
                "Not Medical Advice" to "Nothing contained in AI Nutrition Coach should be interpreted as medical advice, clinical diagnosis, or a prescribed diet regimen for medical conditions.",
                "Individual Health Conditions" to "Users with pre-existing medical conditions—such as diabetes, eating disorders, kidney disease, heart conditions, or pregnancy—must consult a registered dietitian or medical doctor before making significant dietary changes.",
                "Medications & Treatment" to "Always verify potential nutrient-drug interactions with your healthcare provider or pharmacist, especially when altering intake of specific vitamins, potassium, or sodium.",
                "When to Contact a Healthcare Professional" to "If you experience weakness, dizziness, severe stomach distress, or unexpected extreme weight loss, discontinue dietary restrictions and consult a doctor immediately.",
                "Emergency Situations" to "In cases of medical emergencies or severe allergic reactions, dial 911 or your local emergency services immediately. Do not rely on AI Nutrition Coach for urgent health situations."
            )

            items(healthSections.size) { index ->
                val (title, content) = healthSections[index]
                LegalSectionCard(number = "${index + 1}", title = title, content = content)
            }
        }
    }
}

@Composable
fun AiConsentScreen(
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    var check1 by remember { mutableStateOf(true) }
    var check2 by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopHeaderBar(
                title = "AI & Consent",
                subtitle = "How AI powers your nutrition analysis",
                onBack = onBack
            )
        },
        containerColor = SoftWhite,
        modifier = Modifier.testTag("ai_consent_screen")
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFAF5FF)),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFF3E8FF)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Surface(
                                shape = CircleShape,
                                color = Color(0xFFF3E8FF),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Outlined.AutoAwesome,
                                        contentDescription = null,
                                        tint = AiLavender,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(12.dp))
                            Column {
                                Text(
                                    text = "AI Technology Disclosure",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = PrimaryText,
                                        fontSize = 16.sp
                                    )
                                )
                                Text(
                                    text = "Personalized intelligent nutrition assistance",
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = SecondaryText,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "AI Nutrition Coach utilizes artificial intelligence to:",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                fontWeight = FontWeight.SemiBold,
                                color = PrimaryText,
                                fontSize = 13.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val capabilities = listOf(
                            "Analyze food photos to identify ingredients & portion sizes",
                            "Estimate calories, fiber, protein, and water metrics",
                            "Generate nutrition insights tailored to your Weight Loss goal",
                            "Provide personalized daily meal & activity suggestions",
                            "Create daily audio/video summaries of your nutritional score",
                            "Answer nutrition questions through the interactive AI Coach"
                        )

                        capabilities.forEach { text ->
                            Row(
                                modifier = Modifier.padding(vertical = 3.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Check,
                                    contentDescription = null,
                                    tint = AiLavender,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = text,
                                    style = MaterialTheme.typography.bodySmall.copy(
                                        color = PrimaryText,
                                        fontSize = 12.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }

            item {
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color(0xFFFFFBEB),
                    border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Info,
                            contentDescription = null,
                            tint = MedicalAmber,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "AI-generated results may contain errors. Always verify important health or nutrition decisions with a qualified professional.",
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = Color(0xFF92400E),
                                fontSize = 12.sp,
                                lineHeight = 16.sp
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
                            text = "Consent Acknowledgments",
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = PrimaryText,
                                fontSize = 15.sp
                            )
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { check1 = !check1 }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = check1,
                                onCheckedChange = { check1 = it },
                                colors = CheckboxDefaults.colors(checkedColor = AiLavender),
                                modifier = Modifier.testTag("chk_ai_accuracy")
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "I understand that AI-generated nutrition information may not always be accurate.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryText,
                                    fontSize = 13.sp,
                                    lineHeight = 17.sp
                                )
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { check2 = !check2 }
                                .padding(vertical = 4.dp)
                        ) {
                            Checkbox(
                                checked = check2,
                                onCheckedChange = { check2 = it },
                                colors = CheckboxDefaults.colors(checkedColor = AiLavender),
                                modifier = Modifier.testTag("chk_ai_medical")
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "I understand that the app does not provide medical diagnosis or treatment.",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = PrimaryText,
                                    fontSize = 13.sp,
                                    lineHeight = 17.sp
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
                            .testTag("btn_ai_consent_cancel"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Cancel", style = MaterialTheme.typography.titleMedium.copy(fontSize = 14.sp))
                    }

                    Button(
                        onClick = onBack,
                        enabled = check1 && check2,
                        colors = ButtonDefaults.buttonColors(containerColor = AiLavender),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .testTag("btn_ai_consent_continue"),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(text = "Continue", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp))
                    }
                }
            }
        }
    }
}

// Reuseable Components
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

@Composable
private fun DemoNoticeBanner(text: String) {
    Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color(0xFFEFF6FF),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFBFDBFE)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Outlined.Info,
                contentDescription = null,
                tint = MedicalBlue,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = MedicalBlue,
                    fontSize = 11.sp
                )
            )
        }
    }
}

@Composable
private fun LegalMenuItem(
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    testTag: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(14.dp)
            .testTag(testTag),
        verticalAlignment = Alignment.CenterVertically
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

        Spacer(modifier = Modifier.width(12.dp))

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = PrimaryText,
                    fontSize = 14.sp
                )
            )
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = SecondaryText,
                    fontSize = 12.sp
                )
            )
        }

        Icon(
            imageVector = Icons.Outlined.ChevronRight,
            contentDescription = null,
            tint = Color(0xFF9CA3AF),
            modifier = Modifier.size(18.dp)
        )
    }
}

@Composable
private fun LegalSectionCard(
    number: String,
    title: String,
    content: String
) {
    Card(
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.5.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = Color(0xFFEFF6FF),
                    modifier = Modifier.size(24.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text = number,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = MedicalBlue,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 14.sp
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
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
