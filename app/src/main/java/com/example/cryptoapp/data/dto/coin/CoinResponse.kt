package com.example.cryptoapp.data.dto.coin


import com.google.gson.annotations.SerializedName

data class CoinResponse(
    @SerializedName("CoinInfo")
    val coinInfo: CoinInfoResponse,
    @SerializedName("DISPLAY")
    val display: Map<String, DisplayCurrency>?,
    @SerializedName("RAW")
    val raw: Map<String, Currency>?
)