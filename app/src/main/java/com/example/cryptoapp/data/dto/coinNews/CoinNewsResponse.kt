package com.example.cryptoapp.data.dto.coinNews


import com.google.gson.annotations.SerializedName

data class CoinNewsResponse(
    @SerializedName("Data")
    val coinNew: List<CoinNew>,
    @SerializedName("HasWarning")
    val hasWarning: Boolean,
    @SerializedName("Message")
    val message: String,
    @SerializedName("Promoted")
    val promoted: List<Any>,
    @SerializedName("Type")
    val type: Int
)