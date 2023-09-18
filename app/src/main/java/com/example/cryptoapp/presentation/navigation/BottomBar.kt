package com.example.cryptoapp.presentation.navigation

import androidx.annotation.DrawableRes
import com.example.cryptoapp.R

sealed class BottomBar(
    val route: String,
    @DrawableRes
    val icon: Int
) {
    object Home : BottomBar(
        route = "home_screen",
        icon = R.drawable.ic_home
    )

    object Favorite : BottomBar(
        route = "favorite_screen",
        icon = R.drawable.ic_favorite
    )

    object Settings : BottomBar(
        route = "setting_screen",
        icon = R.drawable.ic_settings
    )
}