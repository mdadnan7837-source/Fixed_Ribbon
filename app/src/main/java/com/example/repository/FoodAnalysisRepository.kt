package com.example.repository

import com.example.model.FoodAnalysisData
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow

interface FoodAnalysisRepository {
    fun getLatestFoodAnalysis(): Flow<Resource<FoodAnalysisData>>
    fun saveToHistory(foodAnalysisData: FoodAnalysisData): Flow<Resource<Boolean>>
}
