package com.czech.paybacktask.data.network.repositories

import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.utils.states.DataState
import kotlinx.coroutines.flow.Flow

interface PhotoDetailsRepository {

    suspend fun getPhotoById(id: Int): Flow<DataState<Result.Hit>>
}