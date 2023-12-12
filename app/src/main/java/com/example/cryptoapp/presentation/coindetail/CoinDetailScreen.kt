package com.example.cryptoapp.presentation.coindetail

import SmoothLineGraph
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.core.CircleImage
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailBackButton
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailChangePct
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailChartDataRangeRow
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailFavoriteButton
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailName
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailPrice
import com.example.cryptoapp.util.Const.CRYPTO_API_IMAGE_URL

@Composable
fun CoinDetailScreen(
    navController: NavController,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    coinUiModel: CoinUiModel
) {

    val preferredRange by coinDetailViewModel.preferredRange.collectAsState()

    val isCoinSaved by coinDetailViewModel.isCoinSaved.collectAsState()

    LaunchedEffect(Unit) {
        coinDetailViewModel.fetchRequestedRangeData()
    }
    LaunchedEffect(isCoinSaved) {
        coinDetailViewModel.checkIsCoinSaved(coinName = coinUiModel.name)
    }

    val chartData by coinDetailViewModel.coinGraphData.collectAsState()

    CoinDetail(
        navController = navController,
        coinUiModel = coinUiModel,
        preferredRange = preferredRange,
        chartData = chartData,
        onClick = coinDetailViewModel::onDateRangeClick,
        favoriteClick = coinDetailViewModel::handleCoinSaveProcess,
        favoriteTint = isCoinSaved
    )
}

@Composable
fun CoinDetail(
    navController: NavController,
    coinUiModel: CoinUiModel,
    preferredRange: ChartHistoryRange,
    chartData: List<CoinGraphModel>,
    onClick: (ChartHistoryRange) -> Unit,
    favoriteClick: (CoinUiModel) -> Unit,
    favoriteTint: Boolean,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoinHeader(
            navController = navController,
            coinUiModel = coinUiModel,
            onClick = favoriteClick,
            favoriteTint = favoriteTint
        )
        CoinDetailChartDataRangeRow(
            modifier = Modifier,
            preferredRange = preferredRange,
            onclick = onClick
        )
        CoinChart(chartData = chartData, preferredRange = preferredRange)
    }

}

@Composable
fun CoinChart(
    chartData: List<CoinGraphModel>, preferredRange: ChartHistoryRange,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(start = 8.dp, end = 8.dp),
        contentAlignment = Alignment.Center

    ) {
        SmoothLineGraph(chartData, preferredRange)
    }
}

@Composable
fun CoinHeader(
    navController: NavController,
    coinUiModel: CoinUiModel,
    onClick: (CoinUiModel) -> Unit,
    favoriteTint: Boolean
) {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, top = 32.dp, end = 8.dp, bottom = 15.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoinDetailBackButton(onclick = {
                navController.popBackStack()
            })
            CoinDetailName(coinName = coinUiModel.fullName)
            CoinDetailFavoriteButton(
                onclick = onClick,
                coinUiModel = coinUiModel,
                favoriteTint = favoriteTint
            )
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircleImage(
                imageUrl = CRYPTO_API_IMAGE_URL + coinUiModel.imageUrl,
                width = 80,
                height = 80
            )
            CoinDetailPrice(price = coinUiModel.priceWithSymbol)
            Spacer(modifier = Modifier.height(8.dp))
            CoinDetailChangePct(changePctDay = coinUiModel.changePctDay)
        }
    }
}

@Preview
@Composable
fun CoinDetailScreenPrev() {
    //CoinDetailScreen(coinUiModel = coinUiModelPrev)
}

private val coinUiModelPrev = CoinUiModel(
    id = "5",
    name = "BTC",
    fullName = "Bitcoin",
    imageUrl = "https://www.cryptocompare.com/media/37747199/shib.png",
    priceWithSymbol = "\$29,4",
    price = 29.3,
    changePctDay = -2.069999999999993,
    changeDay = -0.0006964497035043538,
    toSymbol = "\$"
)