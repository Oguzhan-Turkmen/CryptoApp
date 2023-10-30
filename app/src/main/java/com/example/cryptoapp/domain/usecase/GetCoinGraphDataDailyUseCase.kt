package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.repository.CoinRepository
import com.example.cryptoapp.presentation.coindetail.ChartHistoryRange
import javax.inject.Inject

class GetCoinGraphDataDailyUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    suspend fun execute(
        currency: String = "USD",
        coinName: String,
        limit: Int,
        chartHistoryRange: ChartHistoryRange,
        aggregateId: Int
    ): List<CoinGraphModel> {
        return coinRepository.getCoinGraphDataDaily(
            currency = currency,
            coinName = coinName,
            limit = limit,
            chartHistoryRange = chartHistoryRange,
            aggregateId = aggregateId
        )
    }
}