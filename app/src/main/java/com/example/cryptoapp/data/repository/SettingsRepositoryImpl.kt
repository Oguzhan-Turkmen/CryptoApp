package com.example.cryptoapp.data.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.cryptoapp.domain.repository.BaseCurrency
import com.example.cryptoapp.domain.repository.SettingsRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

private const val PREFS_SETTING = "settingsPreferences"
private const val KEY_BASE_CURRENCY = "keyBaseCurrency"

class SettingsRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context,
    private val json: Json,
) : SettingsRepository {
    private val preferences = context.getSharedPreferences(PREFS_SETTING, Context.MODE_PRIVATE)

    private val baseCurrencyJson =
        json.decodeFromString<BaseCurrency>(
            preferences.getString(
                KEY_BASE_CURRENCY,
                json.encodeToString(BaseCurrency.getDefault())
            )!!
        )

    private val _baseCurrency: MutableStateFlow<BaseCurrency> = MutableStateFlow(
        BaseCurrency(
            name = baseCurrencyJson.name,
            symbol = baseCurrencyJson.symbol
        )
    )

    override fun getBaseCurrencyFlow(): StateFlow<BaseCurrency> {
        return _baseCurrency.asStateFlow()
    }

    @SuppressLint("ApplySharedPref")
    override suspend fun setBaseCurrency(currency: BaseCurrency) {
        val currencyData = BaseCurrency(name = currency.name, symbol = currency.symbol)
        preferences.edit()
            .putString(KEY_BASE_CURRENCY, json.encodeToString(currencyData))
            .commit().also {
                if (it) _baseCurrency.value = currency
            }
    }

    override fun getBaseCurrency(): BaseCurrency {
        return _baseCurrency.value
    }

    override suspend fun getAvailableBaseCurrencies(): List<BaseCurrency> {
        return listOf(BaseCurrency("usd", "$"), BaseCurrency("eur", "€"))
    }
}