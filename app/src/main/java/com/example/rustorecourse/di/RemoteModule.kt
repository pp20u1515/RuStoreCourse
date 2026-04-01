package com.example.rustorecourse.di

import com.example.rustorecourse.data.repository.AppRepository
import com.example.rustorecourse.data.source.remote.service.IAppRemoteSource
import com.example.rustorecourse.data.source.remote.INetworkOperations
import com.example.rustorecourse.data.source.remote.NetworkOperations
import com.example.rustorecourse.data.source.remote.NetworkDaoService
import com.example.rustorecourse.domain.repository.IAppRepository
import com.example.rustorecourse.domain.usecase.GetAppUseCase
import com.example.rustorecourse.domain.usecase.GetListOfAppsUseCase
import com.example.rustorecourse.utils.RetrofitInvoker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {
    @Provides
    fun provideAppRepository(
        networkDaoService: IAppRemoteSource
    ): IAppRepository{
        return AppRepository(networkDaoService)
    }

    @Provides
    fun provideNetworkApi(): INetworkOperations{
        return RetrofitInvoker.apiService
    }

    @Provides
    fun provideNetworkDao(api: INetworkOperations): NetworkOperations{
        return NetworkOperations(api)
    }

    @Provides
    fun provideNetworkDaoService(networkDao: NetworkOperations): IAppRemoteSource{
        return NetworkDaoService(networkDao)
    }

    @Provides
    fun provideGetListOfAppsUseCase(
        appRepository: IAppRepository
    ): GetListOfAppsUseCase{
        return GetListOfAppsUseCase(appRepository)
    }

    @Provides
    fun provideGetAppUseCase(
        appRepository: IAppRepository
    ): GetAppUseCase{
        return GetAppUseCase(appRepository)
    }
}