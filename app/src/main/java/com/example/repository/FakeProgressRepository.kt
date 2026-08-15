package com.example.repository

import com.example.model.HistoryItem
import com.example.model.ProgressRecord
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

class FakeProgressRepository : BaseRepository(), ProgressRepository {

    private val progressRecords = mutableListOf(
        ProgressRecord("p1", "2026-08-01", 73.5, "Starting journey"),
        ProgressRecord("p2", "2026-08-03", 73.0, "Good energy"),
        ProgressRecord("p3", "2026-08-05", 72.5, "On track")
    )

    private val historyItems = listOf(
        HistoryItem("h1", "2026-08-04", "Completed daily macro target", 2150),
        HistoryItem("h2", "2026-08-03", "Logged 3 meals", 2080),
        HistoryItem("h3", "2026-08-02", "Logged 4 meals", 2210)
    )

    override fun getProgressHistory(): Flow<Resource<List<ProgressRecord>>> = safeApiCall {
        progressRecords.toList()
    }

    override fun addProgressRecord(record: ProgressRecord): Flow<Resource<ProgressRecord>> = safeApiCall {
        val newRecord = record.copy(id = "p_${System.currentTimeMillis()}")
        progressRecords.add(newRecord)
        newRecord
    }

    override fun getNutritionHistory(): Flow<Resource<List<HistoryItem>>> = safeApiCall {
        historyItems
    }
}
