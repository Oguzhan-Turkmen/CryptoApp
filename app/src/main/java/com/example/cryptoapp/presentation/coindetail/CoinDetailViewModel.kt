package com.example.cryptoapp.presentation.coindetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.usecase.GetCoinGraphDataDailyUseCase
import com.example.cryptoapp.domain.usecase.GetCoinGraphDataHourlyUseCase
import com.example.cryptoapp.util.Const.PARAM_COIN_NAME
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinGraphDataDailyUseCase: GetCoinGraphDataDailyUseCase,
    private val getCoinGraphDataHourlyUseCase: GetCoinGraphDataHourlyUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val coinName = savedStateHandle.get<String>(PARAM_COIN_NAME)

    private val _coinGraphData = MutableStateFlow<List<CoinGraphModel>>(emptyList())
    val coinGraphData = _coinGraphData.asStateFlow()

    private val _preferredRange = MutableStateFlow(ChartHistoryRange.ONE_DAY)
    val preferredRange = _preferredRange.asStateFlow()

    private fun getCoinGraphDataHourly(coinName: String, limit: Int, aggregateId: Int) {
        viewModelScope.launch {
            _coinGraphData.value =
                getCoinGraphDataHourlyUseCase.execute(
                    coinName = coinName,
                    limit = limit,
                    aggregateId = aggregateId
                )
        }
    }

    private fun getCoinGraphDataDaily(
        coinName: String,
        limit: Int,
        aggregateId: Int,
        chartHistoryRange: ChartHistoryRange
    ) {
        viewModelScope.launch {
            _coinGraphData.value =
                getCoinGraphDataDailyUseCase.execute(
                    coinName = coinName,
                    limit = limit,
                    chartHistoryRange = chartHistoryRange,
                    aggregateId = aggregateId
                )
        }
    }

    fun onDateRangeClick(chartHistoryRange: ChartHistoryRange) {
        _preferredRange.value = chartHistoryRange
    }

    fun fetchRequestedRangeData() {
        viewModelScope.launch {
            preferredRange.collectLatest {
                when (it) {
                    ChartHistoryRange.ONE_DAY -> getCoinGraphDataHourly(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId
                    )

                    ChartHistoryRange.ONE_WEEK -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId,
                        chartHistoryRange = it
                    )

                    ChartHistoryRange.ONE_MONTH -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId,
                        chartHistoryRange = it
                    )

                    ChartHistoryRange.THREE_MONTH -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId,
                        chartHistoryRange = it
                    )

                    ChartHistoryRange.SIX_MONTH -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId,
                        chartHistoryRange = it
                    )

                    ChartHistoryRange.ONE_YEAR -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId,
                        chartHistoryRange = it
                    )

                    ChartHistoryRange.ALL -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId,
                        chartHistoryRange = it
                    )
                }
            }
        }
    }
}

enum class ChartHistoryRange(val limit: Int, val aggregateId: Int, val rangeName: String) {
    ONE_DAY(limit = 24, aggregateId = 1, rangeName = "1D"),
    ONE_WEEK(limit = 7, aggregateId = 1, rangeName = "1W"),
    ONE_MONTH(limit = 30, aggregateId = 1, rangeName = "1M"),
    THREE_MONTH(limit = 90, aggregateId = 1, rangeName = "3M"),
    SIX_MONTH(limit = 180, aggregateId = 1, rangeName = "6M"),
    ONE_YEAR(limit = 365, aggregateId = 1, rangeName = "1Y"),
    ALL(limit = 2000, aggregateId = 1, rangeName = "ALL")
}

