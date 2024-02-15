package com.example.cryptoapp.presentation.coindetail.coindetailcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoapp.R
import com.example.cryptoapp.core.CircleImage
import com.example.cryptoapp.data.websocket.Difference
import com.example.cryptoapp.data.websocket.Price
import com.example.cryptoapp.domain.model.CoinNewUiModel
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.coindetail.ChartHistoryRange
import com.example.cryptoapp.presentation.news.newscomponents.CoinNewItem
import com.example.cryptoapp.presentation.ui.theme.AppColors
import com.example.cryptoapp.presentation.ui.theme.customTypographyBold
import com.example.cryptoapp.presentation.ui.theme.customTypogrphyRegular
import com.example.cryptoapp.core.util.withDecimalDigits

@Composable
fun CoinDetailName(coinName: String) {
    Text(
        text = coinName,
        style = MaterialTheme.customTypographyBold.title2,
    )
}

@Composable
fun CoinDetailPrice(price: Price, baseCurrency: String) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = price.value.toString() + baseCurrency,
        style = MaterialTheme.customTypographyBold.title1,
    )
}

@Composable
fun CoinDetailBackButton(
    onclick: () -> Unit
) {
    IconButton(
        onClick = onclick,
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_back),
            contentDescription = "Detail Back",
        )
    }
}

@Composable
fun CoinDetailFavoriteButton(
    coinUiModel: CoinUiModel,
    modifier: Modifier = Modifier,
    onclick: (CoinUiModel) -> Unit,
    favoriteTint: Boolean,
) {
    IconButton(
        modifier = modifier,
        onClick = {
            onclick.invoke(coinUiModel)
        },
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_detail_favorite),
            contentDescription = "Detail Favorite",
            tint = if (favoriteTint) AppColors.SelectedBlue else Color.Black
        )
    }
}


@Composable
fun CoinDetailChangePct(price: Price) {
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(25.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(AppColors.LightGray),
        contentAlignment = Alignment.Center
    ) {
        CoinChangePctText(changePctDay = price)
    }
}

@Composable
fun CoinChangePctText(
    changePctDay: Price,
) {
    val changePctDayText = changePctDay.exchangeValue.withDecimalDigits(2).toString()
    val textColor = when (changePctDay.exchange) {
        Difference.UP -> AppColors.Green
        Difference.Down -> AppColors.Red
        Difference.Stable -> AppColors.Blue
    }
    Text(
        text = changePctDayText,
        style = MaterialTheme.customTypogrphyRegular.subheadline,
        color = textColor,
    )
}

@Composable
fun CoinDetailChartDataRangeRow(
    modifier: Modifier,
    preferredRange: ChartHistoryRange,
    onclick: (ChartHistoryRange) -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ChartHistoryRange.values().forEach() {
            CoinDetailChartDataRangeText(
                it.rangeName,
                preferredRange = preferredRange,
                onclick = { onclick.invoke(it) })
        }
    }
}

@Composable
fun CoinDetailChartDataRangeText(
    rangeText: String,
    preferredRange: ChartHistoryRange,
    onclick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(32.dp)
            .height(24.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (preferredRange.rangeName == rangeText) AppColors.Blue else Color.White)
            .clickable(onClick = onclick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = rangeText,
            style = MaterialTheme.customTypographyBold.caption2,
            color = if (preferredRange.rangeName == rangeText) Color.White else AppColors.Black
        )
    }
}


@Composable
fun ViewMoreButton(onClick: () -> Unit) {
    ElevatedButton(
        modifier = Modifier
            .fillMaxWidth(),
        onClick = { onClick() }
    ) {
        Text("View More")
    }
}

@Preview
@Composable
fun PrevViewMoreButton() {
    ViewMoreButton {

    }
}

