package com.example.cryptoapp.data.dto.coingraph


import com.google.gson.annotations.SerializedName

data class CoinGraphResponse(
    @SerializedName("Data")
    val coinData: Data,
    @SerializedName("HasWarning")
    val hasWarning: Boolean,
    @SerializedName("Message")
    val message: String,
    @SerializedName("RateLimit")
    val rateLimit: RateLimit,
    @SerializedName("Response")
    val response: String,
    @SerializedName("Type")
    val type: Int
)