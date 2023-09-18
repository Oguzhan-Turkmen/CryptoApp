package com.example.cryptoapp.domain.source

import androidx.paging.PagingData
import com.example.cryptoapp.data.dto.coin.CoinResponse
import com.example.cryptoapp.data.dto.coingraph.CoinGraphResponse
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    fun getAllCoins(tsym: String): Flow<PagingData<CoinResponse>>
    suspend fun getCoinGraphDataHourly(fsym: String, tsym: String, aggregateId: Int): CoinGraphResponse

    suspend fun getCoinGraphDataDaily(fsym: String, tsym: String, aggregateId: Int): CoinGraphResponse

}