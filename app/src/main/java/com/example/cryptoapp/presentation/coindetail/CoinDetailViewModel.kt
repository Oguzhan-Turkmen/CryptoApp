package com.example.cryptoapp.presentation.coindetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.usecase.GetCoinGraphDataDailyUseCase
import com.example.cryptoapp.domain.usecase.GetCoinGraphDataHourlyUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinGraphDataDailyUseCase: GetCoinGraphDataDailyUseCase,
    private val getCoinGraphDataHourlyUseCase: GetCoinGraphDataHourlyUseCase,
) : ViewModel() {
    private val _coinGraphDataHourly = MutableStateFlow<List<Pair<Int, Double>>>(emptyList())
    val coinGraphDataHourly = _coinGraphDataHourly.asStateFlow()

    private val _coinGraphDataDaily = MutableStateFlow<List<Pair<Int, Double>>>(emptyList())
    val coinGraphDataDaily = _coinGraphDataDaily.asStateFlow()
    fun getCoinGraphDataHourly(fsym: String, aggregateId: Int) {
        viewModelScope.launch {
            _coinGraphDataHourly.value =
                getCoinGraphDataHourlyUseCase.execute(fsym = fsym, aggregateId = aggregateId).map {
                    it.time to it.close
                }
        }
    }

    fun getCoinGraphDataDaily(fsym: String, aggregateId: Int) {
        viewModelScope.launch {
            _coinGraphDataDaily.value =
                getCoinGraphDataDailyUseCase.execute(fsym = fsym, aggregateId = aggregateId).map {
                    it.time to it.close
                }
        }
    }
}

enum class ChartHistoryRange {
    ONE_DAY,
    ONE_WEEK,
    ONE_MONTH,
    THREE_MONTH,
    SIX_MONTH,
    ONE_YEAR,
    ALL
}

