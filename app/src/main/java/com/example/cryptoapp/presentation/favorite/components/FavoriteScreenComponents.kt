package com.example.cryptoapp.presentation.favorite.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.presentation.ui.theme.AppColors
import com.example.cryptoapp.presentation.ui.theme.customTypographyBold

@Composable
fun FavoriteScreenTitle() {
    Text(
        modifier = Modifier.padding(
            start = 24.dp,
            top = 24.dp,
            bottom = 12.dp
        ),
        text = "Favorites",
        style = MaterialTheme.customTypographyBold.title2,
        color = AppColors.Black
    )
}