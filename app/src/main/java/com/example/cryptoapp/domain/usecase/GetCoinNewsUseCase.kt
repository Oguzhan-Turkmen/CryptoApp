package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.model.CoinNewUiModel
import com.example.cryptoapp.domain.repository.CoinRepository
import javax.inject.Inject

class GetCoinNewsUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    suspend fun execute(
        coinName: String,
    ): List<CoinNewUiModel> {
        return coinRepository.getCoinNews(
            coinName = coinName,
        )
    }
}