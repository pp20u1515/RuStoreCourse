package com.example.rustorecourse.di

import com.example.rustorecourse.data.source.remote.service.INetworkDaoService
import com.example.rustorecourse.datasource.remote.api.INetworkApi
import com.example.rustorecourse.datasource.remote.dao.NetworkDao
import com.example.rustorecourse.datasource.remote.service.NetworkDaoService
import com.example.rustorecourse.domain.repository.IAppRepository
import com.example.rustorecourse.domain.usecase.GetRemoteListOfAppsUseCase
import com.example.rustorecourse.utils.RetrofitInvoker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    fun provideNetworkApi(): INetworkApi{
        return RetrofitInvoker.apiService
    }

    @Provides
    fun provideNetworkDao(api: INetworkApi): NetworkDao{
        return NetworkDao(api)
    }

    @Provides
    fun provideNetworkDaoService(networkDao: NetworkDao): INetworkDaoService{
        return NetworkDaoService(networkDao)
    }
}