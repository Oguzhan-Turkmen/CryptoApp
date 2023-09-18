package com.example.cryptoapp.data.dto.coingraph


import com.google.gson.annotations.SerializedName

data class CoinGraphData(
    @SerializedName("close")
    val close: Double,
    @SerializedName("conversionSymbol")
    val conversionSymbol: String,
    @SerializedName("conversionType")
    val conversionType: String,
    @SerializedName("high")
    val high: Double,
    @SerializedName("low")
    val low: Double,
    @SerializedName("open")
    val open: Double,
    @SerializedName("time")
    val time: Int,
    @SerializedName("volumefrom")
    val volumefrom: Double,
    @SerializedName("volumeto")
    val volumeto: Double
)