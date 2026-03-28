package com.example.rustorecourse.datasource.remote.api

import com.example.rustorecourse.datasource.remote.model.AppDetailsItemDto
import com.example.rustorecourse.datasource.remote.model.AppDto
import retrofit2.http.GET
import retrofit2.http.Path

interface INetworkApi {
    @GET("catalog")
    suspend fun getListOfApps(): List<AppDetailsItemDto>

    @GET("catalog/{id}")
    suspend fun getAppDetails(@Path("id") id: String): AppDto
}