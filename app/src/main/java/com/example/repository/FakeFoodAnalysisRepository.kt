package com.example.repository

import com.example.model.FoodAnalysisData
import com.example.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeFoodAnalysisRepository : FoodAnalysisRepository {

    override fun getLatestFoodAnalysis(): Flow<Resource<FoodAnalysisData>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(FoodAnalysisData()))
    }

    override fun saveToHistory(foodAnalysisData: FoodAnalysisData): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        emit(Resource.Success(true))
    }
}
