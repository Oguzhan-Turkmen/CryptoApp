package com.example.cryptoapp.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptoapp.data.api.CryptoApi
import com.example.cryptoapp.data.dto.coin.CoinResponse
import com.example.cryptoapp.data.dto.coingraph.CoinGraphResponse
import com.example.cryptoapp.data.repository.CoinsPagingSource
import com.example.cryptoapp.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val cryptoApi: CryptoApi
) : RemoteDataSource {
    override fun getAllCoins(
        tsym: String
    ): Flow<PagingData<CoinResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { CoinsPagingSource(cryptoApi, tsym) }
        ).flow
    }

    override suspend fun getCoinGraphDataHourly(
        fsym: String,
        tsym: String,
        aggregateId: Int
    ): CoinGraphResponse {
        return cryptoApi.getCoinGraphDataHourly(
            fsym = fsym,
            tsym = tsym,
            aggregateId = aggregateId,
        )
    }

    override suspend fun getCoinGraphDataDaily(
        fsym: String,
        tsym: String,
        aggregateId: Int
    ): CoinGraphResponse {
        return cryptoApi.getCoinGraphDataDaily(
            fsym = fsym,
            tsym = tsym,
            aggregateId = aggregateId,
        )
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}
