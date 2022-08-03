package com.czech.paybacktask.data.network.repositories

import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.utils.states.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PhotoRepositoryImpl : PhotoRepository {

    override suspend fun getSearchResult(query: String): Flow<DataState<List<Result.Hit>>> {
        return flow {

        }
    }
}