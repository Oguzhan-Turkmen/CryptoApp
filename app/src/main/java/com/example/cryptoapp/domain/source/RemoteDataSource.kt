package com.example.cryptoapp.domain.source

import androidx.paging.PagingData
import com.example.cryptoapp.data.dto.coin.CoinResponse
import com.example.cryptoapp.data.dto.coingraph.CoinGraphResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllCoins(currency: String): Flow<PagingData<CoinResponse>>
    suspend fun getCoinGraphDataHourly(
        currency: String,
        coinName: String,
        limit:Int,
        aggregateId: Int
    ): CoinGraphResponse

    suspend fun getCoinGraphDataDaily(
        currency: String,
        coinName: String,
        limit:Int,
        aggregateId: Int
    ): CoinGraphResponse

}