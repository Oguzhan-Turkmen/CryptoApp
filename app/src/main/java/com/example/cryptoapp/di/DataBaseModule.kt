package com.example.cryptoapp.di

import android.content.Context
import androidx.room.Room
import com.example.cryptoapp.data.local.CryptoAppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DataBaseModule {
    @Singleton
    @Provides
    fun provideCryptoAppDb(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        CryptoAppDb::class.java,
        "CryptoAppDb"
    ).build()

    @Singleton
    @Provides
    fun provideCryptoAppDao(database: CryptoAppDb) = database.getDao()
}