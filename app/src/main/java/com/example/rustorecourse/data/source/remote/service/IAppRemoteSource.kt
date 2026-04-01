package com.example.rustorecourse.data.source.remote.service

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem

interface IAppRemoteSource {
    suspend fun getListOfApps(): Result<List<AppDetailsItem>>

    suspend fun getAppDetails(id: String): Result<App>
}