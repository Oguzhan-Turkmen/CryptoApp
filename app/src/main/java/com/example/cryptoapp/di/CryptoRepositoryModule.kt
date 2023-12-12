package com.example.cryptoapp.di

import com.example.cryptoapp.data.repository.CoinRepositoryImpl
import com.example.cryptoapp.data.repository.DataBaseRepositoryImpl
import com.example.cryptoapp.data.repository.SettingsRepositoryImpl
import com.example.cryptoapp.domain.repository.CoinRepository
import com.example.cryptoapp.domain.repository.DataBaseRepository
import com.example.cryptoapp.domain.repository.SettingsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CryptoRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCoinRepository(coinRepositoryImpl: CoinRepositoryImpl): CoinRepository

    @Binds
    @Singleton
    abstract fun bindDataBaseRepository(dataBaseRepositoryImpl: DataBaseRepositoryImpl): DataBaseRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(settingsRepositoryImpl: SettingsRepositoryImpl): SettingsRepository

}