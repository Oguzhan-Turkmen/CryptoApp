package com.example.cryptoapp.domain.repository

import androidx.paging.PagingData
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.model.CoinUiModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getAllCoins(currency: String): Flow<PagingData<CoinUiModel>>
    suspend fun getCoinGraphDataHourly(
        currency: String,
        coinName: String,
        limit:Int,
        aggregateId: Int
    ): List<CoinGraphModel>

    suspend fun getCoinGraphDataDaily(
        currency: String,
        coinName: String,
        limit:Int,
        aggregateId: Int
    ): List<CoinGraphModel>

}