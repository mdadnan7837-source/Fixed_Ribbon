package com.example.repository

import com.example.model.AuthState
import com.example.model.User
import com.example.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class FakeAuthRepository : BaseRepository(), AuthRepository {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Unauthenticated)

    // Demo user specified in requirements
    private val demoUser = User(
        id = "user_sarah_101",
        name = "Sarah",
        email = "sarah@example.com",
        age = 28,
        weightKg = 62.0,
        heightCm = 168.0,
        gender = "Female",
        primaryGoal = "Healthy Lifestyle",
        isGuest = false
    )

    override fun getAuthState(): Flow<AuthState> = _authState.asStateFlow()

    override fun checkSession(): Flow<Resource<Boolean>> = safeApiCall {
        delay(800) // Simulate splash session check
        val isAuthenticated = _authState.value is AuthState.Authenticated
        isAuthenticated
    }

    override fun login(email: String, password: String): Flow<Resource<User>> = safeApiCall {
        delay(1000) // Simulate network call
        if (email.trim().lowercase() == "sarah@example.com" && password == "password123") {
            _authState.value = AuthState.Authenticated(demoUser)
            demoUser
        } else if (email.contains("@") && password.length >= 6) {
            // Allow login for testing any valid format email/password
            val loggedInUser = demoUser.copy(email = email, name = email.substringBefore("@").replaceFirstChar { it.uppercase() })
            _authState.value = AuthState.Authenticated(loggedInUser)
            loggedInUser
        } else {
            throw IllegalArgumentException("Invalid email or password. Try sarah@example.com / password123")
        }
    }

    override fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        age: Int,
        heightCm: Double,
        weightKg: Double,
        primaryGoal: String
    ): Flow<Resource<User>> = safeApiCall {
        delay(1200) // Simulate registration
        val newUser = User(
            id = "user_${System.currentTimeMillis()}",
            name = name.ifBlank { "User" },
            email = email,
            age = age,
            heightCm = heightCm,
            weightKg = weightKg,
            gender = gender,
            primaryGoal = primaryGoal,
            isGuest = false
        )
        _authState.value = AuthState.Authenticated(newUser)
        newUser
    }

    override fun forgotPassword(email: String): Flow<Resource<String>> = safeApiCall {
        delay(1000) // Simulate reset link sending
        if (email.contains("@")) {
            "A password reset link has been sent to $email"
        } else {
            throw IllegalArgumentException("Please enter a valid email address.")
        }
    }

    override fun logout(): Flow<Resource<Unit>> = safeApiCall {
        _authState.value = AuthState.Unauthenticated
    }

    override fun loginWithGoogle(): Flow<Resource<User>> = safeApiCall {
        delay(1000)
        val googleUser = demoUser.copy(
            id = "user_google_1",
            name = "Sarah (Google)",
            email = "sarah.google@example.com"
        )
        _authState.value = AuthState.Authenticated(googleUser)
        googleUser
    }

    override fun loginWithApple(): Flow<Resource<User>> = safeApiCall {
        delay(1000)
        val appleUser = demoUser.copy(
            id = "user_apple_1",
            name = "Sarah (Apple)",
            email = "sarah.apple@example.com"
        )
        _authState.value = AuthState.Authenticated(appleUser)
        appleUser
    }

    override fun loginAsGuest(): Flow<Resource<User>> = safeApiCall {
        delay(800)
        val guestUser = User(
            id = "user_guest",
            name = "Guest User",
            email = "guest@ainutrition.app",
            isGuest = true
        )
        _authState.value = AuthState.Authenticated(guestUser)
        guestUser
    }
}

