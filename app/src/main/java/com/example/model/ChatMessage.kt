package com.example.model

enum class MessageRole {
    USER,
    ASSISTANT,
    SYSTEM
}

data class ChatMessage(
    val id: String,
    val role: MessageRole,
    val content: String,
    val timestamp: String,
    val isLoading: Boolean = false
)

data class TodayOverviewItem(
    val id: String,
    val name: String,
    val currentDisplay: String,
    val targetDisplay: String,
    val statusText: String,
    val current: Double,
    val target: Double,
    val isWarning: Boolean = false
)

data class CoachInsight(
    val title: String,
    val body: String
)
