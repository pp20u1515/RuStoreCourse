package com.example.rustorecourse.di

import com.example.rustorecourse.data.source.remote.INetworkDataSource
import com.example.rustorecourse.data.source.remote.INetworkOperations
import com.example.rustorecourse.data.source.remote.NetworkDataSource
import com.example.rustorecourse.data.source.remote.NetworkOperations
import com.example.rustorecourse.utils.RetrofitInvoker
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    fun provideNetworkApi(): INetworkDataSource{
        return RetrofitInvoker.apiService
    }

    @Provides
    fun provideNetworkDataSource(api: INetworkDataSource): NetworkDataSource{
        return NetworkDataSource(api)
    }

    @Provides
    fun provideNetworkOperations(
        networkDataSource: NetworkDataSource
    ): INetworkOperations{
        return NetworkOperations(networkDataSource)
    }
}