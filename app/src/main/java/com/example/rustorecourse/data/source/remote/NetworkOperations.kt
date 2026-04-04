package com.example.rustorecourse.data.source.remote

import com.example.rustorecourse.data.source.remote.mapper.toDomain
import com.example.rustorecourse.data.source.remote.mapper.toDomainList
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import javax.inject.Inject

class NetworkOperations @Inject constructor(
    private val networkDataSource: NetworkDataSource
): INetworkOperations {
    override suspend fun getAppDetails(id: String): Result<App> {
        return networkDataSource.getAppDetails(id).mapCatching { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getListOfApps(): Result<List<AppDetailsItem>> {
        return networkDataSource.getListOfApps().mapCatching { dtoList ->
            dtoList.toDomainList()
        }
    }
}