package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.DataBaseRepository
import javax.inject.Inject

class GetSavedCoinsDbUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(): List<CoinUiModel> {
        return dataBaseRepository.getSavedCoin()
    }
}