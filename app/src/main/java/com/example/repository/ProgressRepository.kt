package com.example.repository

import com.example.model.HistoryItem
import com.example.model.ProgressRecord
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface ProgressRepository {
    fun getProgressHistory(): Flow<Resource<List<ProgressRecord>>>
    fun addProgressRecord(record: ProgressRecord): Flow<Resource<ProgressRecord>>
    fun getNutritionHistory(): Flow<Resource<List<HistoryItem>>>
}
