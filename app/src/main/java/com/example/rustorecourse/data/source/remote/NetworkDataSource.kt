package com.example.rustorecourse.data.source.remote

import com.example.rustorecourse.data.source.remote.model.AppDetailsItemDto
import com.example.rustorecourse.data.source.remote.model.AppDto
import javax.inject.Inject

class NetworkDataSource @Inject constructor(
    private val api: INetworkDataSource
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