package com.example.cryptoapp.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.repository.BaseCurrency
import com.example.cryptoapp.domain.repository.SettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsScreenViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModel() {
    private val _baseCurrency = MutableStateFlow(settingsRepository.getBaseCurrency())
    val baseCurrency: StateFlow<BaseCurrency>
        get() {
            viewModelScope.launch {
                settingsRepository.getBaseCurrencyFlow()
                    .map { BaseCurrency(name = it.name.uppercase()) }.collectLatest {
                        _baseCurrency.value = it
                    }
            }

            return _baseCurrency.asStateFlow()
        }

    private val _availableBaseCurrencies = MutableStateFlow(listOf(BaseCurrency.getDefault()))
    val availableBaseCurrencies: StateFlow<List<BaseCurrency>>
        get() {
            viewModelScope.launch {
                _availableBaseCurrencies.value =
                    settingsRepository.getAvailableBaseCurrencies().map {
                        BaseCurrency(name = it.name.uppercase())
                    }
            }

            return _availableBaseCurrencies.asStateFlow()
        }

    fun setBaseCurrency(currency: BaseCurrency) {
        viewModelScope.launch {
            settingsRepository.setBaseCurrency(currency)
        }
    }
}