package com.czech.paybacktask.data.network.repositories

import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.data.room.repositories.PhotosDaoRepository
import com.czech.paybacktask.utils.states.DataState
import com.czech.paybacktask.utils.toHit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PhotoDetailsRepositoryImpl @Inject constructor(
    private val photosDaoRepository: PhotosDaoRepository
): PhotoDetailsRepository {
    override suspend fun getPhotoById(id: Int): Flow<DataState<Result.Hit>> {
        return flow {
            emit(DataState.loading())

            val photo = photosDaoRepository.getPhotoById(id)?.toHit()

            try {
                when (photo != null) {
                    true -> {
                        emit(DataState.data(data = photo))
                    }
                    false -> {
                        emit(DataState.error(message = "Photo not found"))
                    }
                }
            }catch (e: Exception) {
                emit(
                    DataState.error(
                        message = e.message ?: "An error occurred"
                    )
                )
            }
        }
    }
}