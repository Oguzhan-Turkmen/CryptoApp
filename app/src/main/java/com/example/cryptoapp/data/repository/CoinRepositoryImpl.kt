package com.example.cryptoapp.data.repository

import androidx.paging.PagingData
import androidx.paging.map
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.CoinRepository
import com.example.cryptoapp.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class CoinRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
) : CoinRepository {
    override fun getAllCoins(
        currency: String
    ): Flow<PagingData<CoinUiModel>> =
        remoteDataSource.getAllCoins(currency = currency).map { pagingData ->
            pagingData.map {
                CoinUiModel(
                    id = it.coinInfo.id,
                    name = it.coinInfo.name,
                    fullName = it.coinInfo.fullName,
                    imageUrl = it.coinInfo.imageUrl,
                    priceWithSymbol = it.display?.values?.first()?.price ?: "",
                    price = it.raw?.values?.first()?.price ?: 0.0,
                    changePctDay = it.raw?.values?.first()?.changePctDay ?: 0.0,
                    changeDay = it.raw?.values?.first()?.changeDay ?: 0.0,
                    toSymbol = it.display?.values?.first()?.toSymbol ?: ""
                )
            }
        }

    override suspend fun getCoinGraphDataHourly(
        currency: String,
        coinName: String,
        limit:Int,
        aggregateId: Int
    ): List<CoinGraphModel> {
        val result = remoteDataSource.getCoinGraphDataHourly(
            coinName = coinName,
            currency = currency,
            limit = limit,
            aggregateId = aggregateId,
        )
        return result.coinData.data.map {
            CoinGraphModel(
                time = it.time,
                close = it.close
            )
        }
    }

    override suspend fun getCoinGraphDataDaily(
        currency: String,
        coinName: String,
        limit:Int,
        aggregateId: Int
    ): List<CoinGraphModel> {
        val result = remoteDataSource.getCoinGraphDataDaily(
            coinName = coinName,
            currency = currency,
            limit = limit,
            aggregateId = aggregateId,
        )
        return result.coinData.data.map {
            CoinGraphModel(
                time = it.time,
                close = it.close
            )
        }
    }

    private fun getHour(epochTimeSec: Long): String {
        val localDateTime =
            Instant.ofEpochMilli(epochTimeSec).atZone(ZoneId.systemDefault()).toLocalDateTime()

        val formatter = DateTimeFormatter.ofPattern("hh:mm:ss a")

        return localDateTime.format(formatter)
    }

}