package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinGraphDataDailyUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    suspend fun execute(
        currency: String = "USD",
        coinName: String,
        limit: Int,
        aggregateId: Int
    ): List<CoinGraphModel> {
        return coinRepository.getCoinGraphDataDaily(
            currency = currency,
            coinName = coinName,
            limit = limit,
            aggregateId = aggregateId
        )
    }
}