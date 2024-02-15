package com.example.cryptoapp.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.cryptoapp.R
import com.example.cryptoapp.core.CircleImage
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.home.HomeViewModel
import com.example.cryptoapp.presentation.ui.theme.AppColors
import com.example.cryptoapp.presentation.ui.theme.customTypographyBold
import com.example.cryptoapp.presentation.ui.theme.customTypogrphyRegular
import com.example.cryptoapp.core.util.Const.CRYPTO_API_IMAGE_URL
import com.example.cryptoapp.core.util.withDecimalDigits

@Composable
fun CoinList(
    coinUiModelPagingItems: LazyPagingItems<CoinUiModel>,
    onClick: (coinUiModel: CoinUiModel) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .background(Color.White)
    ) {
        item {
            Spacer(Modifier.height(4.dp))
        }
        items(
            count = coinUiModelPagingItems.itemCount,
        ) { index ->
            val item = coinUiModelPagingItems[index]
            if (item != null) {
                CoinItem(
                    coinUiModel = item,
                    onClick = { onClick.invoke(item) }
                )
            }
        }
        coinUiModelPagingItems.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { PageLoader(modifier = Modifier.fillParentMaxSize()) }
                }

                loadState.refresh is LoadState.Error -> {
                    val error = coinUiModelPagingItems.loadState.refresh as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier.fillParentMaxSize(),
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingNextPageItem(modifier = Modifier) }
                }

                loadState.append is LoadState.Error -> {
                    val error = coinUiModelPagingItems.loadState.append as LoadState.Error
                    item {
                        ErrorMessage(
                            modifier = Modifier,
                            message = error.error.localizedMessage!!,
                            onClickRetry = { retry() })
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoinItem(
    coinUiModel: CoinUiModel,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp),
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
                .background(Color.White)
                .padding(
                    start = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp,
                    end = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(modifier = Modifier.height(IntrinsicSize.Min)) {
                CircleImage(
                    imageUrl = CRYPTO_API_IMAGE_URL + coinUiModel.imageUrl,
                    width = 40,
                    height = 40
                )
                Spacer(modifier = modifier.width(8.dp))
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    CoinFullName(
                        coinFullName = coinUiModel.fullName,
                        textStyle = MaterialTheme.customTypographyBold.body
                    )
                    CoinName(
                        coinName = coinUiModel.name,
                        textStyle = MaterialTheme.customTypographyBold.footnote
                    )
                }
            }

            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End
            ) {
                CoinPrice(
                    coinPrice = coinUiModel.priceWithSymbol,
                    textStyle = MaterialTheme.customTypographyBold.headline
                )
                Row {
                    CoinChangePct(
                        changePctDay = coinUiModel.changePctDay,
                        textStyle = MaterialTheme.customTypogrphyRegular.caption1
                    )
                    CoinChangePrice(
                        changeDay = coinUiModel.changeDay,
                        toSymbol = coinUiModel.toSymbol,
                        textStyle = MaterialTheme.customTypogrphyRegular.caption1
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarWidget(
    query: String,
    onQueryChanged: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String? = null,
    label: String? = null,
    shape: Shape = RoundedCornerShape(12.dp),
    backgroundColor: Color = Color.White,
    innerPadding: Dp = 15.dp,
    elevation: Dp = 5.dp
) {
    val focusManager = LocalFocusManager.current

    Card(
        modifier = modifier
            .then(
                Modifier
                    .padding(innerPadding)
                    .shadow(elevation = elevation, shape = shape)
                    .background(color = backgroundColor, shape = shape)
            ),
        shape = shape
    ) {
        Column(
            modifier = Modifier
                .background(color = backgroundColor, shape = shape)
        ) {
            OutlinedTextField(
                value = query,
                onValueChange = {
                    onQueryChanged(it)
                },
                leadingIcon = {
                    Icon(Icons.Default.Search, contentDescription = null)
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            onQueryChanged("")
                        }
                    ) {

                    }
                },
                placeholder = if (placeholder != null) {
                    {
                        Text(placeholder)
                    }
                } else {
                    null
                },
                label = if (label != null) {
                    {
                        Text(label)
                    }
                } else {
                    null
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true,
                    keyboardType = KeyboardType.Ascii,
                    imeAction = ImeAction.Search,
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        focusManager.clearFocus()
                    },
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.White,
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    disabledBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}


@Composable
fun FilterDropDownMenu(
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Most Popular",
            style = MaterialTheme.customTypographyBold.subheadline,
            color = AppColors.Gray,
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(Alignment.TopEnd)

        ) {
            IconButton(onClick = { expanded = !expanded }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter),
                    contentDescription = "Filter Icon",
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                filterModel.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.filterName) },
                        onClick = {
                            homeViewModel.filterState.value = it.filterId
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CoinName(
    coinName: String,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    Text(
        text = coinName,
        style = textStyle,
        color = Color.Gray,
    )
}

@Composable
fun CoinPrice(
    coinPrice: String,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    Text(
        text = coinPrice,
        style = textStyle,
        color = Color.Black,
    )
}

@Composable
fun CoinFullName(
    coinFullName: String,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    Text(
        modifier = Modifier.width(120.dp),
        text = coinFullName,
        style = textStyle,
        color = Color.Black,
        overflow = TextOverflow.Ellipsis,
        maxLines = 1
    )
}


@Composable
fun CoinChangePct(
    changePctDay: Double,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    val changePctDayText = changePctDay.withDecimalDigits(2).toString() + "%"
    val textColor = if (changePctDay < 0) AppColors.Red else AppColors.Green
    Text(
        text = changePctDayText,
        style = textStyle,
        color = textColor,
    )
}

@Composable
fun CoinChangePrice(
    changeDay: Double,
    toSymbol: String,
    textStyle: TextStyle = TextStyle(fontSize = 15.sp)
) {
    val changePriceDayText =
        "(" + toSymbol + changeDay.withDecimalDigits(2).toString().removePrefix("-") + ")"
    val textColor = if (changeDay < 0) AppColors.Red else AppColors.Green
    Text(
        text = changePriceDayText,
        style = textStyle,
        color = textColor,
    )
}


@Composable
fun ErrorMessage(
    message: String,
    modifier: Modifier = Modifier,
    onClickRetry: () -> Unit
) {
    Row(
        modifier = modifier.padding(10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = message,
            color = MaterialTheme.colorScheme.error,
            modifier = Modifier.weight(1f),
            maxLines = 2
        )
        OutlinedButton(onClick = onClickRetry) {
            Text(text = stringResource(id = R.string.strRetry))
        }
    }
}

@Composable
fun PageLoader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.strFetchingDataFromServer),
            color = MaterialTheme.colorScheme.primary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        CircularProgressIndicator(Modifier.padding(top = 10.dp))
    }
}

@Composable
fun LoadingNextPageItem(modifier: Modifier) {
    CircularProgressIndicator(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)
            .wrapContentWidth(Alignment.CenterHorizontally)
    )
}

@Preview
@Composable
fun filterDropWidgetPrev() {
    FilterDropDownMenu()
}


@Preview
@Composable
fun SearchBarPrev() {
    SearchBarWidget(query = "saaa", onQueryChanged = { "" })
}

@Preview
@Composable
fun CoinItemPrev() {
    CoinItem(coinUiModel = coinUiModelPrev, modifier = Modifier, onClick = {})
}

@Preview
@Composable
fun CircleImagePrev() {
    CircleImage(
        imageUrl = "https://www.cryptocompare.com/media/37747199/shib.png",
        modifier = Modifier,
        40,
        40
    )
}

@Preview
@Composable
fun CoinChangePricePrev() {
    CoinChangePrice(changeDay = -0.0006964497035043538, toSymbol = "\$")
}

@Preview
@Composable
fun CoinChangePrev() {
    CoinChangePct(changePctDay = -2.069999999999993)
}


@Preview
@Composable
fun CoinNamePrev() {
    CoinName(coinName = "BTC")
}

@Preview
@Composable
fun CoinPricePrev() {
    CoinPrice(coinPrice = "\$29,4")
}

@Preview
@Composable
fun CoinFullNamePrev() {
    CoinFullName(coinFullName = "Bitcoin")
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

