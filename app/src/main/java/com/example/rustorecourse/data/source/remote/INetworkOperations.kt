package com.example.rustorecourse.data.source.remote

import com.example.rustorecourse.data.source.remote.model.AppDetailsItemDto
import com.example.rustorecourse.data.source.remote.model.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface INetworkOperations {
    @GET("catalog")
    suspend fun getListOfApps(): List<AppDetailsItemDto>

    @GET("catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDto
}