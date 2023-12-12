package com.example.cryptoapp.data.repository

import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.DataBaseRepository
import com.example.cryptoapp.domain.source.DataBaseLocalDataSource
import javax.inject.Inject

class DataBaseRepositoryImpl @Inject constructor(
    private val localDataSource: DataBaseLocalDataSource
) : DataBaseRepository {
    override suspend fun saveCoin(coinName: CoinUiModel) {
        localDataSource.saveCoin(coinName)
    }

    override suspend fun deleteSavedCoin(coinName: CoinUiModel) {
        localDataSource.deleteSavedCoin(coinName)
    }

    override suspend fun getSavedCoin(): List<CoinUiModel> {
        return localDataSource.getSavedCoin()
    }
}