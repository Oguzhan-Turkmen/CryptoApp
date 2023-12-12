package com.example.cryptoapp.data.source

import com.example.cryptoapp.data.local.CryptoAppDao
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.source.DataBaseLocalDataSource
import javax.inject.Inject


class DataBaseDataSourceImpl @Inject constructor(
    private val cryptoAppDao: CryptoAppDao
) : DataBaseLocalDataSource {
    override suspend fun saveCoin(coinName: CoinUiModel) {
        cryptoAppDao.saveCoin(coinName)
    }

    override suspend fun deleteSavedCoin(coinName: CoinUiModel) {
        cryptoAppDao.deleteCoin(coinName)
    }

    override suspend fun getSavedCoin(): List<CoinUiModel> {
        return cryptoAppDao.getAllSavedCoins()
    }
}