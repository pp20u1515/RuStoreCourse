package com.example.rustorecourse.di

import com.example.rustorecourse.data.source.local.service.AppDaoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    fun provideAppDaoService(): AppDaoService{
        return AppDaoService()
    }
}