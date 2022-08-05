package com.czech.paybacktask.data.network.repositories

import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    suspend fun getSearchResult(query: String): Flow<DataState<List<Result.Hit>>>

    suspend fun getCachedResult(): Flow<DataState<List<Result.Hit>>>
}