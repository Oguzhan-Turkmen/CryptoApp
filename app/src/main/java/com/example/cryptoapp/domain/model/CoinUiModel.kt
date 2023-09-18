package com.example.cryptoapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CoinUiModel(
    val id: String,
    val name: String,
    val fullName:String,
    val imageUrl: String,
    val priceWithSymbol: String,
    val price: Double,
    val changePctDay: Double,
    val changeDay: Double,
    val toSymbol: String
):Parcelable