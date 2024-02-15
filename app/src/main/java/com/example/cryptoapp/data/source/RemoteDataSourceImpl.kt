package com.example.cryptoapp.data.source

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.cryptoapp.data.api.CryptoApi
import com.example.cryptoapp.data.dto.coin.CoinResponse
import com.example.cryptoapp.data.dto.coinNews.CoinNewsResponse
import com.example.cryptoapp.data.dto.coingraph.CoinGraphResponse
import com.example.cryptoapp.data.repository.CoinsPagingSource
import com.example.cryptoapp.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class RemoteDataSourceImpl @Inject constructor(
    private val cryptoApi: CryptoApi
) : RemoteDataSource {
    override fun getAllCoins(
        currency: String
    ): Flow<PagingData<CoinResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
            ),
            pagingSourceFactory = { CoinsPagingSource(cryptoApi, currency) }
        ).flow
    }

    override suspend fun getCoinGraphDataHourly(
        currency: String,
        coinName: String,
        limit: Int,
        aggregateId: Int
    ): CoinGraphResponse {
        return cryptoApi.getCoinGraphDataHourly(
            coinName = coinName,
            currency = currency,
            limit = limit,
            aggregateId = aggregateId,
        )
    }

    override suspend fun getCoinGraphDataDaily(
        currency: String,
        coinName: String,
        limit: Int,
        aggregateId: Int
    ): CoinGraphResponse {
        return cryptoApi.getCoinGraphDataDaily(
            coinName = coinName,
            currency = currency,
            limit = limit,
            aggregateId = aggregateId,
        )
    }

    override suspend fun getCoinNews(coinName: String): CoinNewsResponse {
        return cryptoApi.getCoinNews(coinName = coinName)
    }

    companion object {
        const val PAGE_SIZE = 10
    }
}
