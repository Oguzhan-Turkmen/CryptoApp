package com.example.cryptoapp.data.dto.coingraph


import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("Aggregated")
    val aggregated: Boolean,
    @SerializedName("Data")
    val data: List<CoinGraphData>,
    @SerializedName("TimeFrom")
    val timeFrom: Int,
    @SerializedName("TimeTo")
    val timeTo: Int
)