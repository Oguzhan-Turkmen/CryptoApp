package com.example.cryptoapp.presentation.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptoapp.presentation.settings.components.InformationText
import com.example.cryptoapp.presentation.settings.components.SettingScreenTitle
import com.example.cryptoapp.presentation.settings.components.SettingsDropDownMenu

@Composable
fun SettingsScreen(
    navController: NavController,
    settingsScreenViewModel: SettingsScreenViewModel = hiltViewModel(),
) {

    val baseCurrency by settingsScreenViewModel.baseCurrency.collectAsState()
    val availableBaseCurrencies by settingsScreenViewModel.availableBaseCurrencies.collectAsState()

    Column {
        SettingScreenTitle()
        SettingsDropDownMenu(
            baseCurrency = baseCurrency,
            onSetCurrency = settingsScreenViewModel::setBaseCurrency,
            availableBaseCurrencies = availableBaseCurrencies
        )
        InformationText()
    }

}

