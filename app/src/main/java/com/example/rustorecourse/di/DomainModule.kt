package com.example.rustorecourse.di

import com.example.rustorecourse.domain.repository.IAppRepository
import com.example.rustorecourse.domain.usecase.GetRemoteAppUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

}