package com.example.rustorecourse.di

import com.example.rustorecourse.data.repository.AppRepository
import com.example.rustorecourse.data.source.local.service.AppDaoService
import com.example.rustorecourse.data.source.remote.INetworkOperations
import com.example.rustorecourse.domain.repository.IAppRepository
import com.example.rustorecourse.domain.usecase.GetAppDetailsUseCase
import com.example.rustorecourse.domain.usecase.GetListOfAppsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    fun provideAppRepository(
        appNetwork: INetworkOperations,
        localDaoService: AppDaoService,
        ioDispatcher: CoroutineDispatcher
    ): IAppRepository{
        return AppRepository(appNetwork, localDaoService, ioDispatcher)
    }

    @Provides
    fun provideGetAppDetailsUseCase(
        appRepository: IAppRepository
    ): GetAppDetailsUseCase{
        return GetAppDetailsUseCase(appRepository)
    }

    @Provides
    fun provideGetListOfAppsUseCase(
        appRepository: IAppRepository
    ): GetListOfAppsUseCase{
        return GetListOfAppsUseCase(appRepository)
    }
}