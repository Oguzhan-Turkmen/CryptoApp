package com.example.cryptoapp.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.cryptoapp.presentation.home.components.CoinItem
import com.example.cryptoapp.presentation.home.components.CoinList
import com.example.cryptoapp.presentation.home.components.FilterDropDownMenu
import com.example.cryptoapp.presentation.home.components.SearchBarWidget
import com.example.cryptoapp.presentation.navigation.MainScreens

@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel(),
) {
    val coinPagingFlow = homeViewModel.coinPagingFlow.collectAsLazyPagingItems()
    val searchText by homeViewModel.searchQuery.collectAsState()
    val sortFilter by homeViewModel.filterState.collectAsState()
    val baseCurrency by homeViewModel.baseCurrency.collectAsState()

    LaunchedEffect(baseCurrency) { coinPagingFlow.refresh() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        SearchBarWidget(
            query = searchText,
            onQueryChanged = homeViewModel::setSearch,
            placeholder = "Search for a Coin"
        )
        FilterDropDownMenu()
        if (searchText.isEmpty() && sortFilter == SortFilter.EMPTY) {
            CoinList(
                coinUiModelPagingItems = coinPagingFlow,
                onClick = {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "coinUiModel",
                        value = it
                    )
                    navController.navigate(MainScreens.CoinDetailScreen.route + it.name)
                }
            )
        } else if (searchText.isNotEmpty()) {
            LazyColumn {
                items(
                    homeViewModel.getSearchedResult(
                        coinPagingFlow.itemSnapshotList,
                        sortFilter
                    )
                ) {
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
        } else {
            LazyColumn {
                items(
                    homeViewModel.filterData(
                        coinPagingFlow.itemSnapshotList,
                        sortFilter
                    )
                ) {
                    if (it != null) {
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
        }
    }
}