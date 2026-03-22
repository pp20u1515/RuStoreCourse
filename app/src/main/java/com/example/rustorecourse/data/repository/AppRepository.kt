package com.example.rustorecourse.data.repository

import com.example.rustorecourse.data.source.local.mapper.toDomain
import com.example.rustorecourse.data.source.local.service.AppDaoService
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.repository.IAppRepository
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val appDaoService: AppDaoService
): IAppRepository {
    override suspend fun getApp(): App {
        return appDaoService.getApp().toDomain()
    }

    override suspend fun getListOfApps(): List<AppDetailsItem> {
        return appDaoService.getListOfApps().map { it.toDomain() }
    }
}