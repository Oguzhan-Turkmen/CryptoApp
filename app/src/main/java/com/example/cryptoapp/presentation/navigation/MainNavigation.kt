package com.example.cryptoapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.cryptoapp.domain.model.CoinUiModel
import com.example.cryptoapp.presentation.coindetail.CoinDetailScreen
import com.example.cryptoapp.presentation.favorite.FavoriteScreen
import com.example.cryptoapp.presentation.home.HomeScreen
import com.example.cryptoapp.presentation.settings.SettingsScreen

@Composable
fun MainNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MainScreens.HomeScreen.route
    ) {
        composable(
            route = MainScreens.HomeScreen.route
        ) {
            HomeScreen(navController = navController)
        }
        composable(
            route = MainScreens.FavoriteScreen.route
        ) {
            FavoriteScreen(navController = navController)
        }
        composable(
            route = MainScreens.SettingScreen.route
        ) {
            SettingsScreen(navController = navController)
        }
        composable(
            route = MainScreens.CoinDetailScreen.route + "{coinName}",
            arguments = listOf(navArgument("coinName") { type = NavType.StringType })
        ) {
            val coinUiModel =
                navController.previousBackStackEntry?.savedStateHandle?.get<CoinUiModel>("coinUiModel")
            if (coinUiModel != null)
                CoinDetailScreen(navController = navController, coinUiModel = coinUiModel)
        }
    }
}

sealed class MainScreens(val route: String) {
    object HomeScreen : MainScreens("home_screen")
    object FavoriteScreen : MainScreens("favorite_screen")
    object SettingScreen : MainScreens("setting_screen")
    object CoinDetailScreen : MainScreens("coin_detail_screen")
}