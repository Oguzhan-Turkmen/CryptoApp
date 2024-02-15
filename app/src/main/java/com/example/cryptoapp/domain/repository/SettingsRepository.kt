package com.example.cryptoapp.domain.repository

import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable

interface SettingsRepository {
    fun getBaseCurrencyFlow(): StateFlow<BaseCurrency>

    suspend fun setBaseCurrency(currency: BaseCurrency)
    fun getBaseCurrency(): BaseCurrency

    suspend fun getAvailableBaseCurrencies(): List<BaseCurrency>
}

@Serializable
data class BaseCurrency(val name: String, val symbol: String) {
    companion object {
        fun getDefault() = BaseCurrency(name = "USD", symbol = "$")
    }
}