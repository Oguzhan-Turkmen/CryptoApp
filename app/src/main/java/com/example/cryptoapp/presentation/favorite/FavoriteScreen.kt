package com.example.cryptoapp.presentation.favorite

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.favorite.components.FavoriteScreenTitle
import com.example.cryptoapp.presentation.home.components.CoinItem
import com.example.cryptoapp.presentation.home.components.SearchBarWidget
import com.example.cryptoapp.presentation.navigation.MainScreens
import com.example.cryptoapp.ui.theme.CryptoAppTheme

@Composable
fun FavoriteScreen(
    navController: NavController,
    favoriteScreenViewModel: FavoriteScreenViewModel = hiltViewModel()
) {
    val searchText by favoriteScreenViewModel.searchQuery.collectAsState()

    val savedCoinList by favoriteScreenViewModel.savedCoinList.collectAsState()

    LaunchedEffect(Unit) {
        favoriteScreenViewModel.getSavedCoins()
    }

    Column {
        FavoriteScreenTitle()
        SearchBarWidget(
            query = searchText,
            onQueryChanged = favoriteScreenViewModel::setSearch,
            placeholder = "Search for a Coin"
        )
        if (searchText.isEmpty()) {
            CoinItemList(coinList = savedCoinList, navController = navController)
        } else {
            CoinItemList(
                coinList = favoriteScreenViewModel.getSearchedResult(savedCoinList),
                navController = navController
            )
        }
    }
}

@Composable
fun CoinItemList(
    coinList: List<CoinUiModel>,
    navController: NavController,
) {
    LazyColumn {
        items(coinList)
        {
            CoinItem(
                coinUiModel = it,
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "coinUiModel",
                        value = it
                    )
                    navController.navigate(MainScreens.CoinDetailScreen.route + it.name)
                }
            )
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    CryptoAppTheme {
        Greeting("Android")
    }
}