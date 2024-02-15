package com.example.cryptoapp.presentation.news

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.cryptoapp.presentation.navigation.MainScreens
import com.example.cryptoapp.presentation.news.newscomponents.CoinNewItem
import com.example.cryptoapp.presentation.news.newscomponents.NewsText
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoinNewsScreen(
    navController: NavController,
    coinDetailViewModel: NewsViewModel = hiltViewModel(),
) {
    val coinNewsData by coinDetailViewModel.coinNewsData.collectAsStateWithLifecycle()


    LazyColumn(
        modifier = Modifier.padding(start = 8.dp, top = 12.dp, end = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        stickyHeader {
            Surface(Modifier.fillParentMaxWidth()) {
                NewsText(textStyle = TextStyle(fontSize = 20.sp))
            }
        }
        items(coinNewsData) {
            val encodeUrl = URLEncoder.encode(it.sourceUrl, StandardCharsets.UTF_8.toString())
            CoinNewItem(
                coinNewUiModel = it,
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "coinNewUrl",
                        value = encodeUrl
                    )
                    navController.navigate(MainScreens.CoinNewsWebViewScreen.route + encodeUrl)
                }
            )
        }
    }

}