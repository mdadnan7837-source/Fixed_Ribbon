package com.example.repository

import com.example.model.User
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeProfileRepository : BaseRepository(), ProfileRepository {

    private var currentUser = User(
        id = "user_101",
        name = "Alex Johnson",
        email = "alex@example.com",
        age = 28,
        weightKg = 72.5,
        heightCm = 178.0,
        gender = "Male"
    )

    override fun getUserProfile(): Flow<Resource<User>> = safeApiCall {
        currentUser
    }

    override fun updateProfile(user: User): Flow<Resource<User>> = safeApiCall {
        currentUser = user
        currentUser
    }
}
