package com.example.cryptoapp.di

import com.example.cryptoapp.data.source.DataBaseDataSourceImpl
import com.example.cryptoapp.data.source.RemoteDataSourceImpl
import com.example.cryptoapp.domain.source.DataBaseLocalDataSource
import com.example.cryptoapp.domain.source.RemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun bindRemoteDataSource(remoteDataSourceImpl: RemoteDataSourceImpl): RemoteDataSource

    @Binds
    @Singleton
    abstract fun bindSharedPrefDataSource(shredPrefLocalDataSourceImpl: DataBaseDataSourceImpl): DataBaseLocalDataSource
}