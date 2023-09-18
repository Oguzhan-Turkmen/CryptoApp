package com.example.cryptoapp.domain.repository

import androidx.paging.PagingData
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.model.CoinUiModel
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun getAllCoins(tsym: String): Flow<PagingData<CoinUiModel>>
    suspend fun getCoinGraphDataHourly(
        tsym: String,
        fsym: String,
        aggregateId: Int
    ): List<CoinGraphModel>

    suspend fun getCoinGraphDataDaily(
        tsym: String,
        fsym: String,
        aggregateId: Int
    ): List<CoinGraphModel>

}