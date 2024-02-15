package com.example.cryptoapp.domain.source

import com.example.cryptoapp.domain.model.CoinUiModel

interface DataBaseLocalDataSource {
    suspend fun saveCoin(coinName: CoinUiModel)
    suspend fun deleteSavedCoin(coinName: CoinUiModel)
    suspend fun getSavedCoin(): List<CoinUiModel>
}