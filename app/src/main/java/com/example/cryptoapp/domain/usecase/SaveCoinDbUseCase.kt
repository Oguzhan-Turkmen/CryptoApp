package com.example.cryptoapp.domain.usecase

import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.DataBaseRepository
import javax.inject.Inject

class SaveCoinDbUseCase @Inject constructor(
    private val dataBaseRepository: DataBaseRepository
) {
    suspend fun execute(coinUiModel: CoinUiModel){
        dataBaseRepository.saveCoin(coinUiModel)
    }
}