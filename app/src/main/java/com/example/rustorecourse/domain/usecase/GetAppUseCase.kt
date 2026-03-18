package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.repository.IAppRepository
import javax.inject.Inject

class GetAppUseCase @Inject constructor(
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(): App{
        return appRepository.getApp()
    }
}