package com.example.rustorecourse.di

import com.example.rustorecourse.data.datasource.local.service.AppDaoService
import com.example.rustorecourse.data.repository.AppRepository
import com.example.rustorecourse.domain.repository.IAppRepository
import com.example.rustorecourse.domain.usecase.GetAppUseCase
import com.example.rustorecourse.domain.usecase.GetListOfAppsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideAppRepository(
        appDaoService: AppDaoService
    ): IAppRepository{
        return AppRepository(appDaoService)
    }

    @Provides
    fun provideGetAppUseCase(
        appRepository: IAppRepository
    ): GetAppUseCase{
        return GetAppUseCase(appRepository)
    }

    @Provides
    fun provideGetListOfAppsUseCase(
        appRepository: IAppRepository
    ): GetListOfAppsUseCase{
        return GetListOfAppsUseCase(appRepository)
    }
}