package com.example.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.repository.FakeAuthRepository
import com.example.repository.FakeNutritionRepository
import com.example.ui.screens.AICoachScreen
import com.example.ui.screens.AICoachViewModel
import com.example.ui.screens.AiProcessingScreen
import com.example.ui.screens.AboutAppScreen
import com.example.ui.screens.AiConsentScreen
import com.example.ui.screens.CameraPermissionScreen
import com.example.ui.screens.DailyAiVideoScreen
import com.example.ui.screens.DailyNutritionReportScreen
import com.example.ui.screens.DataPrivacyControlsScreen
import com.example.ui.screens.DeleteAccountScreen
import com.example.ui.screens.EodProcessingScreen
import com.example.ui.screens.FoodAnalysisEntryScreen
import com.example.ui.screens.FoodAnalysisHistoryScreen
import com.example.ui.screens.FoodAnalysisResultScreen
import com.example.ui.screens.FoodAnalysisViewModel
import com.example.ui.screens.ForgotPasswordScreen
import com.example.ui.screens.ForgotPasswordViewModel
import com.example.ui.screens.GoalScreen
import com.example.ui.screens.HealthDisclaimerScreen
import com.example.ui.screens.HelpSupportScreen
import com.example.ui.screens.HistoryScreen
import com.example.ui.screens.HistoryViewModel
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.HomeViewModel
import com.example.ui.screens.LegalPrivacyMenuScreen
import com.example.ui.screens.LoginScreen
import com.example.ui.screens.LoginViewModel
import com.example.ui.screens.MicrophonePermissionScreen
import com.example.ui.screens.NotificationPermissionScreen
import com.example.ui.screens.PhotoPermissionScreen
import com.example.ui.screens.PremiumSubscriptionScreen
import com.example.ui.screens.PrivacyPolicyScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ProfileViewModel
import com.example.ui.screens.ProgressScreen
import com.example.ui.screens.ProgressViewModel
import com.example.ui.screens.RegisterScreen
import com.example.ui.screens.RegisterViewModel
import com.example.ui.screens.SplashScreen
import com.example.ui.screens.SplashViewModel
import com.example.ui.screens.TermsConditionsScreen

@Composable
fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = Screen.Splash.route
) {
    val authRepository = remember { FakeAuthRepository() }
    val nutritionRepository = remember { FakeNutritionRepository() }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(Screen.Splash.route) {
            val viewModel: SplashViewModel = viewModel { SplashViewModel(authRepository) }
            SplashScreen(
                viewModel = viewModel,
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(Screen.Login.route) {
            val viewModel: LoginViewModel = viewModel { LoginViewModel(authRepository) }
            LoginScreen(
                viewModel = viewModel,
                onNavigate = { route ->
                    if (route == Screen.Home.route) {
                        navController.navigate(route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(route)
                    }
                }
            )
        }

        composable(Screen.Register.route) {
            val viewModel: RegisterViewModel = viewModel { RegisterViewModel(authRepository) }
            RegisterScreen(
                viewModel = viewModel,
                onNavigate = { route ->
                    if (route == Screen.Home.route) {
                        navController.navigate(route) {
                            popUpTo(Screen.Login.route) { inclusive = true }
                        }
                    } else {
                        navController.navigate(route)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.ForgotPassword.route) {
            val viewModel: ForgotPasswordViewModel = viewModel { ForgotPasswordViewModel(authRepository) }
            ForgotPasswordScreen(
                viewModel = viewModel,
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Home.route) {
            val viewModel: HomeViewModel = viewModel { HomeViewModel(nutritionRepository) }
            HomeScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.History.route) {
            val viewModel: HistoryViewModel = viewModel()
            HistoryScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Goal.route) {
            GoalScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Progress.route) {
            val viewModel: ProgressViewModel = viewModel()
            ProgressScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.AICoach.route) {
            val viewModel: AICoachViewModel = viewModel()
            AICoachScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) }
            )
        }

        composable(Screen.Profile.route) {
            val viewModel: ProfileViewModel = viewModel { ProfileViewModel(authRepository = authRepository) }
            ProfileScreen(
                viewModel = viewModel,
                onNavigate = { route ->
                    if (route == Screen.Login.route) {
                        navController.navigate(route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    } else {
                        navController.navigate(route)
                    }
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FoodAnalysis.route) {
            FoodAnalysisEntryScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FoodAnalysisResult.route) {
            val viewModel: FoodAnalysisViewModel = viewModel()
            FoodAnalysisResultScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.FoodAnalysisHistory.route) {
            val viewModel: FoodAnalysisViewModel = viewModel()
            FoodAnalysisHistoryScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AiProcessing.route) {
            val viewModel: FoodAnalysisViewModel = viewModel()
            AiProcessingScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.EodProcessing.route) {
            EodProcessingScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DailyNutritionReport.route) {
            DailyNutritionReportScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DailyAiVideo.route) {
            DailyAiVideoScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.LegalPrivacy.route) {
            LegalPrivacyMenuScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.TermsConditions.route) {
            TermsConditionsScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PrivacyPolicy.route) {
            PrivacyPolicyScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.HealthDisclaimer.route) {
            HealthDisclaimerScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AiConsent.route) {
            AiConsentScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DataPrivacy.route) {
            DataPrivacyControlsScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.DeleteAccount.route) {
            DeleteAccountScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.HelpSupport.route) {
            HelpSupportScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.AboutApp.route) {
            AboutAppScreen(
                onNavigate = { route -> navController.navigate(route) },
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.Premium.route) {
            PremiumSubscriptionScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PermNotification.route) {
            NotificationPermissionScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PermCamera.route) {
            CameraPermissionScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PermMicrophone.route) {
            MicrophonePermissionScreen(
                onBack = { navController.popBackStack() }
            )
        }

        composable(Screen.PermPhotos.route) {
            PhotoPermissionScreen(
                onBack = { navController.popBackStack() }
            )
        }
    }
}
