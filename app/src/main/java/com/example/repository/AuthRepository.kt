package com.example.repository

import com.example.model.AuthState
import com.example.model.User
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAuthState(): Flow<AuthState>
    fun checkSession(): Flow<Resource<Boolean>>
    fun login(email: String, password: String): Flow<Resource<User>>
    fun register(
        name: String,
        email: String,
        password: String,
        gender: String,
        age: Int,
        heightCm: Double,
        weightKg: Double,
        primaryGoal: String
    ): Flow<Resource<User>>
    fun forgotPassword(email: String): Flow<Resource<String>>
    fun logout(): Flow<Resource<Unit>>
    fun loginWithGoogle(): Flow<Resource<User>>
    fun loginWithApple(): Flow<Resource<User>>
    fun loginAsGuest(): Flow<Resource<User>>
}

