package com.example.cryptoapp.domain.usecase

import androidx.paging.PagingData
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetCoinListUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    fun execute(currency: String = "USD"): Flow<PagingData<CoinUiModel>> {
        return coinRepository.getAllCoins(currency)
    }
}