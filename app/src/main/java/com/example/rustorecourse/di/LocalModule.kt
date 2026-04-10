package com.example.rustorecourse.di


import android.content.Context
import androidx.room.Room
import com.example.rustorecourse.data.source.local.database.AppDatabase
import com.example.rustorecourse.data.source.local.service.AppDaoService

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
class LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    fun provideAppDaoService(
        database: AppDatabase
    ): AppDaoService{
        return AppDaoService(database)
    }
}