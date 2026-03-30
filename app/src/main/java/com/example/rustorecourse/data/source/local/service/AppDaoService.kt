package com.example.rustorecourse.data.source.local.service

import com.example.rustorecourse.data.source.local.database.AppDatabase
import com.example.rustorecourse.data.source.local.entity.AppEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AppDaoService @Inject constructor(
    database: AppDatabase
) {
    private val appDatabase = database.appDetailsDao()

    fun getLocalApp(id: String): Flow<AppEntity?> {
        return appDatabase.getAppDetails(id)
    }

    fun insertAppDetails(appDetails: AppEntity){
        appDatabase.insertAppDetails(appDetails)
    }
}