package com.example.cryptoapp.presentation.settings.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.cryptoapp.domain.repository.BaseCurrency
import com.example.cryptoapp.presentation.ui.theme.AppColors
import com.example.cryptoapp.presentation.ui.theme.customTypographyBold
import com.example.cryptoapp.presentation.ui.theme.customTypogrphyRegular

@Composable
fun SettingScreenTitle() {
    Text(
        modifier = Modifier.padding(
            start = 24.dp,
            top = 24.dp,
            bottom = 12.dp
        ),
        text = "Settings",
        style = MaterialTheme.customTypographyBold.title2,
        color = AppColors.Black
    )
}

@Composable
fun SettingsDropDownMenu(
    baseCurrency: BaseCurrency,
    onSetCurrency: (BaseCurrency) -> Unit,
    availableBaseCurrencies: List<BaseCurrency>,
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(
                start = 12.dp,
                end = 12.dp,
                top = 8.dp
            )
            .clip(RoundedCornerShape(6.dp))
            .background(AppColors.Gray3),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            modifier = Modifier.padding(start = 8.dp),
            text = "Currency",
            style = MaterialTheme.customTypographyBold.subheadline,
            color = AppColors.Gray,
        )
        Box(
            modifier = Modifier
                .wrapContentSize(Alignment.TopEnd)
        ) {
            Text(
                modifier = Modifier
                    .clickable {
                        run { expanded = !expanded }
                    }
                    .padding(end = 8.dp),
                text = baseCurrency.name,
                style = MaterialTheme.customTypographyBold.subheadline,
                color = AppColors.Gray,
            )
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.background(Color.White)
            ) {
                availableBaseCurrencies.forEach {
                    DropdownMenuItem(
                        text = { Text(text = it.name) },
                        onClick = {
                            onSetCurrency.invoke(it)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun InformationText() {
    Text(
        modifier = Modifier.padding(
            top = 4.dp,
            start = 14.dp,
            end = 4.dp
        ),
        text = "When you select a new base currency, all prices in the app will be \n" +
                "displayed in that currency.",
        style = MaterialTheme.customTypogrphyRegular.caption2,
        color = AppColors.Gray,
    )
}
