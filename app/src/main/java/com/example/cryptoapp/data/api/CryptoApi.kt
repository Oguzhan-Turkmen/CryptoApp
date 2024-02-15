package com.example.cryptoapp.data.api

import com.example.cryptoapp.BuildConfig
import com.example.cryptoapp.data.dto.coin.CoinListResponse
import com.example.cryptoapp.data.dto.coinNews.CoinNewsResponse
import com.example.cryptoapp.data.dto.coingraph.CoinGraphResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoApi {

    @GET(ALL_COIN_PATH)
    suspend fun getAllCoin(
        @Query("page") page: Int,
        @Query("tsym") currency: String,
        @Query("api_key") apiKey: String = API_KEY
    ): CoinListResponse

    @GET(COIN_GRAPH_DATA_HOURLY)
    suspend fun getCoinGraphDataHourly(
        @Query("fsym") coinName: String,
        @Query("tsym") currency: String,
        @Query("limit") limit: Int = 30,
        @Query("aggregate") aggregateId: Int
    ): CoinGraphResponse

    @GET(COIN_GRAPH_DATA_DAILY)
    suspend fun getCoinGraphDataDaily(
        @Query("fsym") coinName: String,
        @Query("tsym") currency: String,
        @Query("limit") limit: Int = 24,
        @Query("aggregate") aggregateId: Int
    ): CoinGraphResponse

    @GET(COIN_NEWS)
    suspend fun getCoinNews(
        @Query("categories") coinName: String,
        @Query("api_key") apiKey: String = API_KEY
    ): CoinNewsResponse

    companion object {
        const val ALL_COIN_PATH = "top/mktcapfull"
        const val COIN_GRAPH_DATA_HOURLY = "v2/histohour"
        const val COIN_GRAPH_DATA_DAILY = "v2/histoday"
        const val COIN_NEWS = "v2/news/?"
        const val API_KEY = BuildConfig.API_KEY
    }
}