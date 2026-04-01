package com.example.rustorecourse.di

import dagger.Module
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
        networkDaoService: INetworkDaoService,
        localDaoService: AppDaoService,
        ioDispatcher: CoroutineDispatcher
    ): IAppRepository{
        return AppRepository(networkDaoService, localDaoService, ioDispatcher)
    }

    @Provides
    fun provideGetAppDetailsUseCase(
        appRepository: IAppRepository
    ): GetAppDetailsUseCase{
        return GetAppDetailsUseCase(appRepository)
    }

    @Provides
    fun provideGetRemoteListOfAppsUseCase(
        appRepository: IAppRepository
    ): GetRemoteListOfAppsUseCase{
        return GetRemoteListOfAppsUseCase(appRepository)
    }
}