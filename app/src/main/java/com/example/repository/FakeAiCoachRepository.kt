package com.example.repository

import com.example.model.ChatMessage
import com.example.model.CoachInsight
import com.example.model.MessageRole
import com.example.model.TodayOverviewItem
import com.example.util.Resource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class FakeAiCoachRepository : AiCoachRepository {

    private val initialMessages = mutableListOf(
        ChatMessage(
            id = "msg_1",
            role = MessageRole.ASSISTANT,
            content = "You’re doing well today. Your protein intake is strong and you’re close to your daily goal.",
            timestamp = "9:30 AM"
        ),
        ChatMessage(
            id = "msg_2",
            role = MessageRole.USER,
            content = "What should I have for dinner?",
            timestamp = "9:31 AM"
        ),
        ChatMessage(
            id = "msg_3",
            role = MessageRole.ASSISTANT,
            content = "Try grilled salmon with vegetables and a small portion of quinoa. It's a balanced option with protein, fiber and healthy fats.",
            timestamp = "9:32 AM"
        )
    )

    override fun getChatHistory(): Flow<Resource<List<ChatMessage>>> = flow {
        emit(Resource.Loading)
        delay(100)
        emit(Resource.Success(initialMessages.toList()))
    }

    override fun sendMessage(userMessage: String): Flow<Resource<ChatMessage>> = flow {
        emit(Resource.Loading)
        val timeNow = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())

        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            role = MessageRole.USER,
            content = userMessage,
            timestamp = timeNow
        )
        initialMessages.add(userMsg)

        delay(600)

        val botResponse = when {
            userMessage.contains("dinner", ignoreCase = true) ->
                "Here are great dinner ideas focused on fiber and antioxidants:\n• Wild-caught salmon with roasted veggies\n• Quinoa bowl with roasted chickpeas & kale\n• Turkey & vegetable soup with whole grain bread."
            userMessage.contains("avoid", ignoreCase = true) ->
                "To optimize cancer-prevention nutrition, try to minimize:\n• Highly processed meats (bacon, sausage)\n• Sugary drinks & ultra-processed snacks\n• Excess refined grains."
            userMessage.contains("protein", ignoreCase = true) ->
                "A good target for daily protein is around 0.8–1.0g per lb of body weight. Great healthy sources include grilled chicken, salmon, egg whites, Greek yogurt, and legumes!"
            userMessage.contains("sugar", ignoreCase = true) || userMessage.contains("processed", ignoreCase = true) ->
                "Tips to reduce processed foods:\n1. Swap white bread for 100% whole grain\n2. Snack on fresh berries and nuts\n3. Prepare homemade dressings with olive oil."
            else ->
                "Thanks for asking! Incorporating plenty of colorful vegetables, high-fiber legumes, and whole grains into your meals is one of the most effective ways to support long-term health."
        }

        val aiMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            role = MessageRole.ASSISTANT,
            content = botResponse,
            timestamp = timeNow
        )
        initialMessages.add(aiMsg)

        emit(Resource.Success(aiMsg))
    }

    override fun clearChat(): Flow<Resource<Unit>> = flow {
        initialMessages.clear()
        initialMessages.add(
            ChatMessage(
                id = UUID.randomUUID().toString(),
                role = MessageRole.ASSISTANT,
                content = "Chat cleared! How can I help you with your nutrition today?",
                timestamp = SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
            )
        )
        emit(Resource.Success(Unit))
    }

    override fun getTodayOverview(): Flow<Resource<List<TodayOverviewItem>>> = flow {
        emit(Resource.Success(
            listOf(
                TodayOverviewItem("score", "Nutrition Score", "87", "/100", "Good", 87.0, 100.0),
                TodayOverviewItem("fiber", "Fiber", "28g", "/ 30g", "Good", 28.0, 30.0),
                TodayOverviewItem("fruits", "Fruits & Veggies", "5.2", "/ 5 cups", "Great", 5.2, 5.0),
                TodayOverviewItem("grains", "Whole Grains", "3", "/ 3 servings", "Great", 3.0, 3.0),
                TodayOverviewItem("meat", "Processed Meat", "0.5", "/ 1 oz", "High", 0.5, 1.0, isWarning = true)
            )
        ))
    }

    override fun getCoachInsights(): Flow<Resource<CoachInsight>> = flow {
        emit(Resource.Success(
            CoachInsight(
                title = "Great job staying on track!",
                body = "You met your fiber and whole grain goals today. Try to increase your fruits and vegetables intake for even better cancer-aware nutrition."
            )
        ))
    }
}
