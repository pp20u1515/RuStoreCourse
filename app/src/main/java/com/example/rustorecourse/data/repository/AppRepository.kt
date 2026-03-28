package com.example.rustorecourse.data.repository

import com.example.rustorecourse.data.source.remote.service.INetworkDaoService
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.repository.IAppRepository
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val networkDaoService: INetworkDaoService
): IAppRepository {
    override suspend fun getRemoteListOfApps(): Result<List<AppDetailsItem>>{
        return networkDaoService.getListOfApps()
    }

    override suspend fun getRemoteApp(id: String): Result<App>{
        return networkDaoService.getAppDetails(id)
    }
}