package com.example.cryptoapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.cryptoapp.domain.model.CoinUiModel

@Database(
    entities = [CoinUiModel::class],
    version = 1
)
abstract class CryptoAppDb : RoomDatabase() {
    abstract fun getDao(): CryptoAppDao
}