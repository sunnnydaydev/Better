package com.example.better.hilt

import com.example.better.repo.HomeLocalDataSource
import com.example.better.repo.HomeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Create by SunnyDay /01/22 21:54:04
 */

@Module
@InstallIn(SingletonComponent::class)
class LocalDataModule {
    @Provides
    fun providerHomeRepository(homeLocalDataSource: HomeLocalDataSource) = HomeRepository(homeLocalDataSource)

    @Provides
    fun providerHomeLocalDataSource() = HomeLocalDataSource()
}