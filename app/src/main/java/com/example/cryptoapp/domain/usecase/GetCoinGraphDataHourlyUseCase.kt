package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinGraphDataHourlyUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    suspend fun execute(
        tsym: String = "USD",
        fsym: String,
        aggregateId: Int
    ): List<CoinGraphModel> {
        return coinRepository.getCoinGraphDataHourly(
            tsym = tsym,
            fsym = fsym,
            aggregateId = aggregateId
        )
    }
}