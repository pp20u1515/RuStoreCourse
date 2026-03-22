package com.example.rustorecourse.domain.repository

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem

interface IAppRepository {
    suspend fun getApp(): App
    suspend fun getListOfApps(): List<AppDetailsItem>
}