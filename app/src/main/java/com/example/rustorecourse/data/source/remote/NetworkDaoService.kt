package com.example.rustorecourse.data.source.remote

import com.example.rustorecourse.data.source.remote.mapper.toDomain
import com.example.rustorecourse.data.source.remote.mapper.toDomainList
import com.example.rustorecourse.data.source.remote.service.IAppRemoteSource
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import javax.inject.Inject

class NetworkDaoService @Inject constructor(
    private val networkDao: NetworkOperations
): IAppRemoteSource {
    override suspend fun getAppDetails(id: String): Result<App> {
        return networkDao.getAppDetails(id).mapCatching { dto ->
            dto.toDomain()
        }
    }

    override suspend fun getListOfApps(): Result<List<AppDetailsItem>> {
        return networkDao.getListOfApps().mapCatching { dtoList ->
            dtoList.toDomainList()
        }
    }
}