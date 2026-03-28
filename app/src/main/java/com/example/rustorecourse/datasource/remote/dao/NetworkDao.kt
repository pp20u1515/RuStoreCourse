package com.example.rustorecourse.datasource.remote.dao

import com.example.rustorecourse.datasource.remote.api.INetworkApi
import com.example.rustorecourse.datasource.remote.model.AppDetailsItemDto
import com.example.rustorecourse.datasource.remote.model.AppDto
import javax.inject.Inject

class NetworkDao @Inject constructor(
    private val api: INetworkApi
) {
    suspend fun getListOfApps(): Result<List<AppDetailsItemDto>>{
        return try {
            val response = api.getListOfApps()
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }

    suspend fun getAppDetails(id: String): Result<AppDto>{
        return try {
            val response = api.getAppDetails(id)
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}