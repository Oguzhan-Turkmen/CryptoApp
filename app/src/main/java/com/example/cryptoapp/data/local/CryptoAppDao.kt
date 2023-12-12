package com.example.cryptoapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.cryptoapp.domain.model.CoinUiModel

@Dao
interface CryptoAppDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCoin(coin: CoinUiModel): Long

    @Delete
    suspend fun deleteCoin(coin: CoinUiModel)

    @Query("Select * FROM CoinEntity")
    fun getAllSavedCoins(): List<CoinUiModel>
}