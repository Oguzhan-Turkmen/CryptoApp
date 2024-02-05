package com.example.cryptoapp.data.websocket.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WsCoinData(
    @SerialName("TYPE")
    val type: Int = 0,
    @SerialName("MARKET")
    val market: String = "",
    @SerialName("FROMSYMBOL")
    val fromSymbol: String = "",
    @SerialName("TOSYMBOL")
    val toSymbol: String = "",
    @SerialName("FLAGS")
    val flags: Int = 0,
    @SerialName("LASTTRADEID")
    val lastTradeId: Double = 0.0,
    @SerialName("PRICE")
    val price: Double = 0.0,
    @SerialName("LASTUPDATE")
    val lastUpdate: Double = 0.0,
    @SerialName("LASTVOLUME")
    val lastVolume: Double = 0.0,
    @SerialName("LASTVOLUMETO")
    val lastVolumeTo: Double = 0.0,
    @SerialName("VOLUMEHOUR")
    val volumeHour: Double = 0.0,
    @SerialName("VOLUMEHOURTO")
    val volumeHourTo: Double = 0.0,
    @SerialName("VOLUMEDAY")
    val volumeDay: Double = 0.0,
    @SerialName("VOLUMEDAYTO")
    val volumeDayTo: Double = 0.0,
    @SerialName("VOLUME24HOUR")
    val volume24Hour: Double = 0.0,
    @SerialName("VOLUME24HOURTO")
    val volume24HourTo: Double = 0.0,
)