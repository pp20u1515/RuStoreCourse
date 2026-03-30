package com.example.rustorecourse.domain.repository

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem
import kotlinx.coroutines.flow.Flow

interface IAppRepository {
    suspend fun getRemoteListOfApps(): Result<List<AppDetailsItem>>
    suspend fun getRemoteApp(id: String): Result<App>

    suspend fun getLocalApp(id: String): Result<App>
}