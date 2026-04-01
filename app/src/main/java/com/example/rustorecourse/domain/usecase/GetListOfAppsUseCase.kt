package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.repository.IAppRepository

class GetListOfAppsUseCase(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(): Result<List<AppDetailsItem>>{
        return appRepository.getRemoteListOfApps()
    }
}