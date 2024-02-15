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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cryptoapp.core.CircleImage
import com.example.cryptoapp.data.websocket.Price
import com.example.cryptoapp.domain.model.CoinGraphModel
import com.example.cryptoapp.domain.model.CoinNewUiModel
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailBackButton
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailChangePct
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailChartDataRangeRow
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailFavoriteButton
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailName
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.CoinDetailPrice
import com.example.cryptoapp.presentation.coindetail.coindetailcomponent.ViewMoreButton
import com.example.cryptoapp.presentation.navigation.MainScreens
import com.example.cryptoapp.presentation.news.newscomponents.CoinNewItem
import com.example.cryptoapp.presentation.news.newscomponents.NewsText
import com.example.cryptoapp.presentation.ui.theme.customTypographyBold
import com.example.cryptoapp.core.util.Const.CRYPTO_API_IMAGE_URL
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun CoinDetailScreen(
    navController: NavController,
    coinDetailViewModel: CoinDetailViewModel = hiltViewModel(),
    coinUiModel: CoinUiModel
) {
    val preferredRange by coinDetailViewModel.preferredRange.collectAsStateWithLifecycle()

    val isCoinSaved by coinDetailViewModel.isCoinSaved.collectAsStateWithLifecycle()

    val chartData by coinDetailViewModel.coinGraphData.collectAsStateWithLifecycle()

    val price by coinDetailViewModel.wsData.collectAsStateWithLifecycle()

    val baseCurrency by coinDetailViewModel.baseCurrency.collectAsStateWithLifecycle()

    val coinNews by coinDetailViewModel.coinNewsData.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        coinDetailViewModel.fetchRequestedRangeData()
    }
    LaunchedEffect(isCoinSaved) {
        coinDetailViewModel.checkIsCoinSaved(coinName = coinUiModel.name)
    }

    CoinDetail(
        navController = navController,
        coinUiModel = coinUiModel,
        preferredRange = preferredRange,
        chartData = chartData,
        onClick = coinDetailViewModel::onDateRangeClick,
        favoriteClick = coinDetailViewModel::handleCoinSaveProcess,
        favoriteTint = isCoinSaved,
        price = price,
        baseCurrency = baseCurrency.symbol,
        coinNews = coinNews
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
    price: Price,
    baseCurrency: String,
    coinNews: List<CoinNewUiModel>
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        item {
            CoinHeader(
                navController = navController,
                coinUiModel = coinUiModel,
                onClick = favoriteClick,
                favoriteTint = favoriteTint,
                price = price,
                baseCurrency = baseCurrency
            )
            CoinDetailChartDataRangeRow(
                modifier = Modifier,
                preferredRange = preferredRange,
                onclick = onClick
            )
            CoinChart(chartData = chartData, preferredRange = preferredRange)
            NewsText(
                textStyle = MaterialTheme.customTypographyBold.body
            )
        }
        itemsIndexed(coinNews) { index, item ->
            val encodeUrl = URLEncoder.encode(item.sourceUrl, StandardCharsets.UTF_8.toString())
            if (index < 5) {
                CoinNewItem(item, onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "coinNewUrl",
                        value = encodeUrl
                    )
                    navController.navigate(MainScreens.CoinNewsWebViewScreen.route + encodeUrl)
                })
            }
        }
        item {
            ViewMoreButton {
                navController.navigate(MainScreens.CoinNewsScreen.route + coinUiModel.name)
            }
        }

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
    favoriteTint: Boolean,
    price: Price,
    baseCurrency: String,
) {
    Column {
        Row(
            modifier = Modifier
                .padding(start = 8.dp, top = 32.dp, end = 8.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CoinDetailBackButton(
                onclick = {
                    navController.popBackStack()
                }
            )
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
            CoinDetailPrice(price = price, baseCurrency = baseCurrency)
            Spacer(modifier = Modifier.height(8.dp))
            CoinDetailChangePct(price = price)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}