package com.example.cryptoapp.domain.usecase

import androidx.paging.PagingData
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow

import javax.inject.Inject

class GetCoinPagingFlowUseCase @Inject constructor(
    private val coinRepository: CoinRepository
) {
    fun execute(): Flow<PagingData<CoinUiModel>> {
        return coinRepository.getAllCoinsPagingFlow()
    }
}