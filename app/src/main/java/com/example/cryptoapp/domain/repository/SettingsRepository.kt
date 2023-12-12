package com.example.cryptoapp.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface SettingsRepository {

    fun getBaseCurrencyFlow(): StateFlow<BaseCurrency>

    suspend fun setBaseCurrency(currency: BaseCurrency)
    fun getBaseCurrency(): BaseCurrency

    suspend fun getAvailableBaseCurrencies(): List<BaseCurrency>
}

/*enum class BaseCurrency {
    USD, EUR
}*/

data class BaseCurrency(val name: String) {
    companion object {
        fun getDefault() = BaseCurrency(name = "USD")
    }
}