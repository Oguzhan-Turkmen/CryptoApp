package com.example.cryptoapp.presentation.coindetail

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.data.websocket.Difference
import com.example.cryptoapp.data.websocket.Price
import com.example.cryptoapp.data.websocket.SocketManager
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.model.CoinNewUiModel
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.domain.repository.SettingsRepository
import com.example.cryptoapp.domain.usecase.DeleteCoinDbUseCase
import com.example.cryptoapp.domain.usecase.GetCoinGraphDataDailyUseCase
import com.example.cryptoapp.domain.usecase.GetCoinGraphDataHourlyUseCase
import com.example.cryptoapp.domain.usecase.GetCoinNewsUseCase
import com.example.cryptoapp.domain.usecase.GetSavedCoinsDbUseCase
import com.example.cryptoapp.domain.usecase.SaveCoinDbUseCase
import com.example.cryptoapp.core.util.Const.PARAM_COIN_NAME
import com.example.cryptoapp.core.util.Const.PARAM_WS_COIN_PREFIX
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(
    private val getCoinGraphDataDailyUseCase: GetCoinGraphDataDailyUseCase,
    private val getCoinGraphDataHourlyUseCase: GetCoinGraphDataHourlyUseCase,
    private val saveCoinDbUseCase: SaveCoinDbUseCase,
    private val deleteCoinDbUseCase: DeleteCoinDbUseCase,
    private val getSavedCoinsDbUseCase: GetSavedCoinsDbUseCase,
    private val getCoinNewsUseCase: GetCoinNewsUseCase,
    private val socketManager: SocketManager,
    savedStateHandle: SavedStateHandle,
    settingsRepository: SettingsRepository,
) : ViewModel() {

    private val coinName = savedStateHandle.get<String>(PARAM_COIN_NAME)

    val baseCurrency = settingsRepository.getBaseCurrencyFlow()

    private val wsCoinName =
        PARAM_WS_COIN_PREFIX + coinName?.uppercase() + "~" + baseCurrency.value.name

    private val _wsData = MutableStateFlow(
        Price(
            value = 0.0,
            exchange = Difference.Stable,
            exchangeValue = 0.0
        )
    )
    val wsData = _wsData.asStateFlow()

    private val _coinGraphData = MutableStateFlow<List<CoinGraphModel>>(emptyList())
    val coinGraphData = _coinGraphData.asStateFlow()

    private val _coinNewsData = MutableStateFlow<List<CoinNewUiModel>>(emptyList())
    val coinNewsData = _coinNewsData.asStateFlow()

    private val _preferredRange = MutableStateFlow(ChartHistoryRange.ONE_DAY)
    val preferredRange = _preferredRange.asStateFlow()

    private val _isCoinSaved = MutableStateFlow(false)
    val isCoinSaved = _isCoinSaved.asStateFlow()

    init {
        handleWs(wsCoinName)
        getCoinNews(coinName!!)
    }

    private fun handleWs(coinName: String) {
        viewModelScope.launch {
            socketManager.handleWs(coinName).collectLatest {
                _wsData.value = it
            }
        }
    }

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

    private fun getCoinNews(coinName: String) {
        viewModelScope.launch {
            _coinNewsData.value =
                getCoinNewsUseCase.execute(
                    coinName = coinName,
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

    private suspend fun saveCoinDb(coinUiModel: CoinUiModel) {
        _isCoinSaved.value = true
        saveCoinDbUseCase.execute(coinUiModel)
    }

    private suspend fun deleteCoinDb(coinUiModel: CoinUiModel) {
        _isCoinSaved.value = false
        deleteCoinDbUseCase.execute(coinUiModel)
    }

    fun handleCoinSaveProcess(coinName: CoinUiModel) {
        viewModelScope.launch {
            if (_isCoinSaved.value) deleteCoinDb(coinName) else saveCoinDb(coinName)
        }
    }

    fun checkIsCoinSaved(coinName: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val savedCoinList = getSavedCoinsDbUseCase.execute()
            savedCoinList.forEach {
                if (coinName == it.name) _isCoinSaved.value = true
            }
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

