package com.example.rustorecourse.datasource.remote.service

import com.example.rustorecourse.data.source.remote.service.INetworkDaoService
import com.example.rustorecourse.datasource.remote.dao.NetworkDao
import com.example.rustorecourse.datasource.remote.mapper.toDomain
import com.example.rustorecourse.datasource.remote.mapper.toDomainList
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import javax.inject.Inject

class NetworkDaoService @Inject constructor(
    private val networkDao: NetworkDao
): INetworkDaoService {
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