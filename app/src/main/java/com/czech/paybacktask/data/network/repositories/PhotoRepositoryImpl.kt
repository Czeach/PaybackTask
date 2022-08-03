package com.czech.paybacktask.data.network.repositories

import com.czech.paybacktask.BuildConfig
import com.czech.paybacktask.data.network.ApiService
import com.czech.paybacktask.data.network.model.Result
import com.czech.paybacktask.data.room.repositories.PhotosDaoRepository
import com.czech.paybacktask.utils.states.DataState
import com.czech.paybacktask.utils.toEntityList
import com.czech.paybacktask.utils.toHitList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.lang.Exception
import javax.inject.Inject

class PhotoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val photosDaoRepository: PhotosDaoRepository
) : PhotoRepository {

    override suspend fun getSearchResult(query: String): Flow<DataState<List<Result.Hit>>> {
        return flow {
            emit(DataState.loading())

            val response = apiService.search(query, BuildConfig.API_KEY)

            val hits = response.body()?.hits

            try {
                when (response.isSuccessful) {
                    true -> {
                        if (hits.isNullOrEmpty()) {
                            emit(DataState.data(message = "No result for $query"))
                        } else {
                            photosDaoRepository.insertPhotos(hits.toEntityList())

                            emit(DataState.data(data = photosDaoRepository.getPhotos().toHitList()))
                        }
                    }
                    false -> {
                        emit(DataState.error(message = "Error ${response.code()}"))
                    }
                }
            }catch (e: Exception) {
                emit(
                    DataState.error(
                        message = e.message ?: "An error occurred"
                    )
                )
            }
        }.flowOn(Dispatchers.IO)
    }
}