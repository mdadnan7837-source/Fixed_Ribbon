package com.example.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.navigation.Screen
import com.example.ui.components.AppButton
import com.example.ui.components.AppPasswordTextField
import com.example.ui.components.AppTextField
import com.example.ui.components.AppTopBar
import com.example.ui.theme.LocalSpacing

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun RegisterScreen(
    viewModel: RegisterViewModel,
    onNavigate: (String) -> Unit,
    onBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val snackbarHostState = remember { SnackbarHostState() }

    val genders = listOf("Female", "Male", "Non-Binary", "Other")
    val goals = listOf("Cancer-Aware", "Weight Loss", "Muscle Gain", "Heart Health", "Diabetes-Friendly")

    LaunchedEffect(uiState.isSuccess) {
        if (uiState.isSuccess) {
            onNavigate(Screen.Home.route)
        }
    }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { error ->
            snackbarHostState.showSnackbar(error)
            viewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                title = "Create Account",
                onBackClick = onBack,
                testTag = "register_top_bar"
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier
            .fillMaxSize()
            .testTag("register_screen")
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = spacing.large)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(spacing.small))

            Text(
                text = "Join AI Nutrition Coach",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Personalize your experience to reach your goals",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(spacing.large))

            // Full Name
            AppTextField(
                value = uiState.name,
                onValueChange = { viewModel.onNameChanged(it) },
                label = "Full Name",
                errorText = uiState.nameError,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                testTag = "input_register_name"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            // Email
            AppTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEmailChanged(it) },
                label = "Email Address",
                errorText = uiState.emailError,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Email,
                    imeAction = ImeAction.Next
                ),
                testTag = "input_register_email"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            // Password
            AppPasswordTextField(
                value = uiState.password,
                onValueChange = { viewModel.onPasswordChanged(it) },
                label = "Password",
                errorText = uiState.passwordError,
                testTag = "input_register_password"
            )

            Spacer(modifier = Modifier.height(spacing.medium))

            // Confirm Password
            AppPasswordTextField(
                value = uiState.confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChanged(it) },
                label = "Confirm Password",
                errorText = uiState.confirmPasswordError,
                testTag = "input_register_confirm_password"
            )

            Spacer(modifier = Modifier.height(spacing.large))

            // Gender Selection
            Text(
                text = "Gender",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                genders.forEach { gender ->
                    FilterChip(
                        selected = uiState.gender == gender,
                        onClick = { viewModel.onGenderChanged(gender) },
                        label = { Text(gender) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            // Age, Height, Weight in Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(spacing.small)
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    AppTextField(
                        value = uiState.age,
                        onValueChange = { viewModel.onAgeChanged(it) },
                        label = "Age",
                        errorText = uiState.ageError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        testTag = "input_register_age"
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    AppTextField(
                        value = uiState.height,
                        onValueChange = { viewModel.onHeightChanged(it) },
                        label = "Height (cm)",
                        errorText = uiState.heightError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Next
                        ),
                        testTag = "input_register_height"
                    )
                }
                Column(modifier = Modifier.weight(1f)) {
                    AppTextField(
                        value = uiState.weight,
                        onValueChange = { viewModel.onWeightChanged(it) },
                        label = "Weight (lb)",
                        errorText = uiState.weightError,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        testTag = "input_register_weight"
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.large))

            // Primary Goal
            Text(
                text = "Primary Goal",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(spacing.extraSmall))
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(spacing.small),
                modifier = Modifier.fillMaxWidth()
            ) {
                goals.forEach { goal ->
                    FilterChip(
                        selected = uiState.primaryGoal == goal,
                        onClick = { viewModel.onPrimaryGoalChanged(goal) },
                        label = { Text(goal) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            // Terms & Conditions Checkbox
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = uiState.termsAccepted,
                    onCheckedChange = { viewModel.onTermsAcceptedChanged(it) },
                    modifier = Modifier.testTag("checkbox_terms")
                )
                Text(
                    text = "I agree to the Terms & Conditions and Privacy Policy",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
            if (!uiState.termsError.isNullOrEmpty()) {
                Text(
                    text = uiState.termsError!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(start = spacing.large)
                )
            }

            Spacer(modifier = Modifier.height(spacing.large))

            // Submit Button
            if (uiState.isLoading) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator(modifier = Modifier.testTag("register_loading_indicator"))
                }
            } else {
                AppButton(
                    text = "Create Account",
                    onClick = { viewModel.register() },
                    testTag = "btn_submit_register"
                )
            }

            Spacer(modifier = Modifier.height(spacing.medium))

            // Already have account? Login
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Already have an account?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                TextButton(
                    onClick = { onNavigate(Screen.Login.route) },
                    modifier = Modifier.testTag("btn_nav_login_from_register")
                ) {
                    Text(
                        text = "Log In",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(spacing.large))
        }
    }
}
