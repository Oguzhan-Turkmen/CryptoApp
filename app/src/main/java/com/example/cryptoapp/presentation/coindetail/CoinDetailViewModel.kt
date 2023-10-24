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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
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

    private fun getCoinGraphDataDaily(coinName: String, limit: Int, aggregateId: Int) {
        viewModelScope.launch {
            _coinGraphData.value =
                getCoinGraphDataDailyUseCase.execute(
                    coinName = coinName,
                    limit = limit,
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
                        aggregateId = it.aggregateId
                    )

                    ChartHistoryRange.ONE_MONTH -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId
                    )

                    ChartHistoryRange.THREE_MONTH -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId
                    )

                    ChartHistoryRange.SIX_MONTH -> getCoinGraphDataDaily(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId
                    )

                    ChartHistoryRange.ONE_YEAR -> getCoinGraphDataHourly(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId
                    )

                    ChartHistoryRange.ALL -> getCoinGraphDataHourly(
                        coinName = coinName!!,
                        limit = it.limit,
                        aggregateId = it.aggregateId
                    )
                }
            }
        }
    }


}

private fun getMonth(epochTimeMillis: Long): String {
    val localDateTime =
        Instant.ofEpochMilli(epochTimeMillis).atZone(ZoneId.systemDefault()).toLocalDateTime()

    val formatter = DateTimeFormatter.ofPattern("MMM")

    return localDateTime.format(formatter)
}

fun getDay(epochTimeSec: Long): Int {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epochTimeSec), ZoneId.systemDefault())

    val formatter = DateTimeFormatter.ofPattern("dd")

    return localDateTime.format(formatter).toInt()
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

