package com.example.cryptoapp.presentation.coindetail.coindetailcomponent

import com.example.cryptoapp.presentation.coindetail.ChartHistoryRange

data class ChartDataHistoryRangeModel(
    val rangeId: ChartHistoryRange,
    val rangeName: String
)

val rangeModel = listOf(
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.ONE_DAY, rangeName = "1D"),
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.ONE_WEEK, rangeName = "1W"),
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.ONE_MONTH, rangeName = "1M"),
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.THREE_MONTH, rangeName = "3M"),
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.SIX_MONTH, rangeName = "6M"),
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.ONE_YEAR, rangeName = "1Y"),
    ChartDataHistoryRangeModel(rangeId = ChartHistoryRange.ALL, rangeName = "ALL"),
)