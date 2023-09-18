package com.example.cryptoapp.presentation.coindetail

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.core.CircleImage
import com.example.cryptoapp.core.QuadLineChart
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailBackButton
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailChangePct
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailChartDataRangeRow
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailFavoriteButton
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailName
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailPrice
import com.example.cryptoapp.ui.theme.AppColors
import com.example.cryptoapp.util.Const.CRYPTO_API_IMAGE_URL

@Composable
fun CoinDetailScreen(
    navController: NavController,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    coinUiModel: CoinUiModel
) {
    LaunchedEffect(Unit) {
        coinDetailViewModel.getCoinGraphDataDaily(fsym = coinUiModel.name, aggregateId = 1)
    }

    val chartData by coinDetailViewModel.coinGraphDataDaily.collectAsState()

    CoinDetail(
        navController = navController,
        coinUiModel = coinUiModel,
        chartData = chartData
    )
}

@Composable
fun CoinDetail(
    navController: NavController,
    coinUiModel: CoinUiModel,
    chartData: List<Pair<Int, Double>>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        CoinHeader(navController = navController, coinUiModel = coinUiModel)
        CoinDetailChartDataRangeRow(modifier = Modifier)
        CoinChart(chartData = chartData)
    }

}

@Composable
fun CoinChart(chartData: List<Pair<Int, Double>>) {
    val fakeData = listOf(
        1694822400 to 26569.13,
        1694908800 to 26534.66,
        1694995200 to 26770.25,
        1695081600 to 27218.95,
        1695168000 to 27126.17,
        1695254400 to 26567.99
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .padding(start = 8.dp, end = 8.dp),
            //.clip(RoundedCornerShape(32.dp))
        contentAlignment = Alignment.Center

    ) {
        QuadLineChart(
            data = chartData, modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        )
        LaunchedEffect(chartData) {
            Log.e("CHARTDATA", chartData.toString())
        }
    }
}

@Composable
fun CoinHeader(
    navController: NavController,
    coinUiModel: CoinUiModel
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
            CoinDetailFavoriteButton()
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