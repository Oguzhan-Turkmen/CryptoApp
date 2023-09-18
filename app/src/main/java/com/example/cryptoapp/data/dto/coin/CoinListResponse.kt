package com.example.cryptoapp.data.dto.coin


import com.google.gson.annotations.SerializedName

data class CoinListResponse(
    @SerializedName("Data")
    val data: List<CoinResponse>,
    @SerializedName("HasWarning")
    val hasWarning: Boolean,
    @SerializedName("Message")
    val message: String,
    @SerializedName("MetaData")
    val metaData: MetaData,
    @SerializedName("RateLimit")
    val rateLimit: RateLimit,
    @SerializedName("SponsoredData")
    val sponsoredData: List<Any>,
    @SerializedName("Type")
    val type: Int
)