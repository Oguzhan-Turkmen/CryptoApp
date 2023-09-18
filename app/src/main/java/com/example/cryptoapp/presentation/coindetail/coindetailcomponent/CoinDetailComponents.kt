package com.example.cryptoapp.presentation.coindetail.coindetailcomponent

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.R
import com.example.cryptoapp.ui.theme.AppColors
import com.example.cryptoapp.ui.theme.customTypographyBold
import com.example.cryptoapp.ui.theme.customTypogrphyRegular
import com.example.cryptoapp.util.withDecimalDigits

@Composable
fun CoinDetailName(coinName: String) {
    Text(
        text = coinName,
        style = MaterialTheme.customTypographyBold.title2,
    )
}

@Composable
fun CoinDetailPrice(price: String) {
    Text(
        modifier = Modifier.padding(top = 8.dp),
        text = price,
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
fun CoinDetailFavoriteButton(modifier: Modifier = Modifier) {
    IconButton(
        modifier = modifier,
        onClick = { /*TODO*/ }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_detail_favorite),
            contentDescription = "Detail Favorite",
        )
    }
}


@Composable
fun CoinDetailChangePct(changePctDay: Double) {
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(25.dp)
            .clip(RoundedCornerShape(6.dp))
            .background(AppColors.LightGray),
        contentAlignment = Alignment.Center
    ) {
        CoinChangePctText(changePctDay = changePctDay)
    }
}

@Composable
fun CoinChangePctText(
    changePctDay: Double,
) {
    val changePctDayText = changePctDay.withDecimalDigits(2).toString() + "%"
    val textColor = if (changePctDay < 0) AppColors.Red else AppColors.Green
    Text(
        text = changePctDayText,
        style = MaterialTheme.customTypogrphyRegular.subheadline,
        color = textColor,
    )
}

@Composable
fun CoinDetailChartDataRangeRow(
    modifier: Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp, end = 24.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        rangeModel.forEach {
            CoinDetailChartDataRangeText(it.rangeName)
        }
    }
}

@Composable
fun CoinDetailChartDataRangeText(rangeText: String) {
    Box (
        modifier = Modifier
            .width(28.dp)
            .height(20.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(Color.White),
        contentAlignment = Alignment.Center
    ){
        Text(
            text = rangeText,
            style = MaterialTheme.customTypographyBold.caption2,
            color = AppColors.Black
        )
    }
}

@Preview
@Composable
fun CoinDetailChartDataRangeRowPrev() {
    CoinDetailChartDataRangeRow(modifier = Modifier)
}

@Preview
@Composable
fun CoinDetailChangePctPrev() {
    CoinDetailChangePct(-2.069999999999993)
}

@Preview
@Composable
fun CoinDetailNamePrev() {
    CoinDetailName(coinName = "Bitcoin")
}

@Preview
@Composable
fun CoinDetailPricePrev() {
    CoinDetailPrice(price = "$29,467.560")
}

