package com.example.ui.screens

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
import androidx.compose.material.icons.outlined.CameraAlt
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Mic
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.PhotoLibrary
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val MedicalBlue = Color(0xFF2563EB)
private val HealthyGreen = Color(0xFF22C55E)
private val SoftWhite = Color(0xFFF8FAFC)
private val PrimaryText = Color(0xFF1F2937)
private val SecondaryText = Color(0xFF6B7280)

@Composable
fun NotificationPermissionScreen(
    onBack: () -> Unit
) {
    PermissionExplanationBase(
        title = "Stay on Track",
        message = "Allow notifications for meal reminders, progress updates and AI nutrition insights.",
        icon = Icons.Outlined.Notifications,
        iconBg = Color(0xFFEFF6FF),
        iconTint = MedicalBlue,
        actionLabel = "Allow Notifications",
        testTagPrefix = "perm_notification",
        onBack = onBack
    )
}

@Composable
fun CameraPermissionScreen(
    onBack: () -> Unit
) {
    PermissionExplanationBase(
        title = "Camera Access",
        message = "AI Nutrition Coach uses your camera to scan and analyze food.",
        privacyNote = "Your camera is only accessed when you choose to scan food.",
        icon = Icons.Outlined.CameraAlt,
        iconBg = Color(0xFFF3E8FF),
        iconTint = Color(0xFFA855F7),
        actionLabel = "Allow Camera",
        testTagPrefix = "perm_camera",
        onBack = onBack
    )
}

@Composable
fun MicrophonePermissionScreen(
    onBack: () -> Unit
) {
    PermissionExplanationBase(
        title = "Microphone Access",
        message = "Use your voice to ask the AI Coach questions and quickly add food information.",
        icon = Icons.Outlined.Mic,
        iconBg = Color(0xFFCCFBF1),
        iconTint = Color(0xFF0D9488),
        actionLabel = "Allow Microphone",
        testTagPrefix = "perm_mic",
        onBack = onBack
    )
}

@Composable
fun PhotoPermissionScreen(
    onBack: () -> Unit
) {
    PermissionExplanationBase(
        title = "Photo Access",
        message = "Allow access to photos so you can choose a food image from your gallery for nutrition analysis.",
        icon = Icons.Outlined.PhotoLibrary,
        iconBg = Color(0xFFECFDF5),
        iconTint = HealthyGreen,
        actionLabel = "Allow Photos",
        testTagPrefix = "perm_photos",
        onBack = onBack
    )
}

@Composable
private fun PermissionExplanationBase(
    title: String,
    message: String,
    privacyNote: String? = null,
    icon: ImageVector,
    iconBg: Color,
    iconTint: Color,
    actionLabel: String,
    testTagPrefix: String,
    onBack: () -> Unit
) {
    var isGrantedState by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Surface(color = Color.White, shadowElevation = 1.dp, modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onBack, modifier = Modifier.testTag("btn_back")) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = PrimaryText)
                    }
                    Text(
                        text = "Permission Explanation",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = PrimaryText)
                    )
                }
            }
        },
        containerColor = SoftWhite
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (isGrantedState) {
                Card(
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFECFDF5)),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Surface(shape = CircleShape, color = HealthyGreen, modifier = Modifier.size(56.dp)) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(imageVector = Icons.Outlined.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(28.dp))
                            }
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                        Text(
                            text = "Permission Configured",
                            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = HealthyGreen, fontSize = 18.sp)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Demo pre-permission state captured.",
                            style = MaterialTheme.typography.bodyMedium.copy(color = SecondaryText, fontSize = 13.sp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onBack,
                            colors = ButtonDefaults.buttonColors(containerColor = MedicalBlue),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(text = "Continue", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            } else {
                Surface(
                    shape = CircleShape,
                    color = iconBg,
                    modifier = Modifier.size(80.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = PrimaryText,
                        fontSize = 24.sp
                    ),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = SecondaryText,
                        fontSize = 15.sp,
                        lineHeight = 22.sp
                    ),
                    textAlign = TextAlign.Center
                )

                if (privacyNote != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = Color(0xFFEFF6FF),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Icon(imageVector = Icons.Outlined.Shield, contentDescription = null, tint = MedicalBlue, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = privacyNote,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    color = MedicalBlue,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { isGrantedState = true },
                    colors = ButtonDefaults.buttonColors(containerColor = MedicalBlue),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("${testTagPrefix}_allow"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = actionLabel,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onBack,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .testTag("${testTagPrefix}_not_now"),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Not Now",
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = SecondaryText,
                            fontSize = 15.sp
                        )
                    )
                }
            }
        }
    }
}
