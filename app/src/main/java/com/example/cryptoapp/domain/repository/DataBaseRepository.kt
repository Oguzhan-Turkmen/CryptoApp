package com.example.cryptoapp.domain.repository

import com.example.cryptoapp.domain.model.CoinUiModel

interface DataBaseRepository {
    suspend fun saveCoin(coinName: CoinUiModel)
    suspend fun deleteSavedCoin(coinName: CoinUiModel)
    suspend fun getSavedCoin(): List<CoinUiModel>
}