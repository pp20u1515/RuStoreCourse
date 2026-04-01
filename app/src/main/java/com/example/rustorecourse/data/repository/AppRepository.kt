package com.example.rustorecourse.data.repository

import com.example.rustorecourse.data.source.remote.service.IAppRemoteSource
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.repository.IAppRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val networkDaoService: IAppRemoteSource
): IAppRepository {
    override suspend fun getRemoteListOfApps(): Result<List<AppDetailsItem>>{
        return networkDaoService.getListOfApps()
    }

    override suspend fun getRemoteApp(id: String): Result<App>{
        return networkDaoService.getAppDetails(id)
    }

    override suspend fun getLocalApp(id: String): Result<App> {
        val localEntity = localDaoService.getLocalApp(id).firstOrNull()

        if (localEntity == null){
            val remoteResult = networkDaoService.getAppDetails(id)
            val remoteEntity = remoteResult.getOrNull()

            if (remoteEntity != null){
                withContext(ioDispatcher) {
                    localDaoService.insertAppDetails(remoteEntity.toEntity())
                }
            }
            return remoteResult
        }

        return Result.success(localEntity.toDomain())
    }
}