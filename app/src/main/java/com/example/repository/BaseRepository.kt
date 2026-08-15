package com.example.repository

import com.example.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

abstract class BaseRepository {
    protected fun <T> safeApiCall(call: suspend () -> T): Flow<Resource<T>> = flow {
        emit(Resource.Loading)
        try {
            val result = call()
            emit(Resource.Success(result))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "An unexpected error occurred", e))
        }
    }
}
