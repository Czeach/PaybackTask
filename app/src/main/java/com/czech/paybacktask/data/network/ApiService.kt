package com.czech.paybacktask.data.network

import com.czech.paybacktask.data.network.model.Result
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api")
    suspend fun search(
        @Query("q") q: String,
        @Query("KEY") key: String
    ): Response<Result>
}