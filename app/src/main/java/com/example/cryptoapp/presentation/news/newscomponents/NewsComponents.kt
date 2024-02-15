package com.example.cryptoapp.presentation.news.newscomponents

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cryptoapp.core.CircleImage
import com.example.cryptoapp.domain.model.CoinNewUiModel
import com.example.cryptoapp.presentation.ui.theme.customTypogrphyRegular

@Composable
fun NewsText(
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    Text(
        modifier = Modifier.padding(
            start = 20.dp,
            top = 12.dp,
            bottom = 8.dp,
            end = 12.dp
        ),
        text = "News",
        style = textStyle,
        color = Color.Black,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinNewItem(
    coinNewUiModel: CoinNewUiModel,
    onClick: () -> Unit,
    ) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    start = 12.dp,
                    top = 8.dp,
                    bottom = 12.dp,
                    end = 12.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleImage(
                imageUrl = coinNewUiModel.imageUrl,
                width = 50,
                height = 50
            )
            Spacer(modifier = Modifier.width(4.dp))
            CoinNewTitle(
                coinFullName = coinNewUiModel.title,
                textStyle = MaterialTheme.customTypogrphyRegular.subheadline
            )
        }
        Divider(
            color = Color.Gray,
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
        )
    }
}

@Composable
fun CoinNewTitle(
    coinFullName: String,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    Text(
        text = coinFullName,
        style = textStyle,
        color = Color.Black,
        overflow = TextOverflow.Ellipsis,
        maxLines = 3
    )
}

@Preview
@Composable
fun prevCoinNewsItem() {
    CoinNewItem(
        coinNewUiModel = CoinNewUiModel(
            title = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s ",
            imageUrl = "http://www.bing.com/search?q=lectus",
            sourceUrl = "https://www.google.com/#q=curae"
        ),
        onClick = {}
    )
}