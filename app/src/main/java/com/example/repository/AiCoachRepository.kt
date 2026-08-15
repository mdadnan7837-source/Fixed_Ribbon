package com.example.repository

import com.example.model.ChatMessage
import com.example.model.CoachInsight
import com.example.model.TodayOverviewItem
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface AiCoachRepository {
    fun getChatHistory(): Flow<Resource<List<ChatMessage>>>
    fun sendMessage(userMessage: String): Flow<Resource<ChatMessage>>
    fun clearChat(): Flow<Resource<Unit>>
    fun getTodayOverview(): Flow<Resource<List<TodayOverviewItem>>>
    fun getCoachInsights(): Flow<Resource<CoachInsight>>
}
