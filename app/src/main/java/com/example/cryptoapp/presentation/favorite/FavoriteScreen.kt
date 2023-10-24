package com.example.cryptoapp.presentation.favorite

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import com.example.cryptoapp.ui.theme.CryptoAppTheme

@Composable
fun FavoriteScreen(
    navController: NavController
) {
    //SmoothLineGraph()
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