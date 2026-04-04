package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.repository.IAppRepository
import javax.inject.Inject

class GetAppDetailsUseCase @Inject constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(id: String): Result<App> {
        return appRepository.getApp(id)
    }
}