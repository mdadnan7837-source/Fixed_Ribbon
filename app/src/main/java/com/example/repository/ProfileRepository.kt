package com.example.repository

import com.example.model.User
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProfileRepository {
    fun getUserProfile(): Flow<Resource<User>>
    fun updateProfile(user: User): Flow<Resource<User>>
}
