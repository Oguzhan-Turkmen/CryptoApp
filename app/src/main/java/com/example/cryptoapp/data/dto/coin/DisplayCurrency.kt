package com.example.cryptoapp.data.dto.coin


import com.google.gson.annotations.SerializedName

data class DisplayCurrency(
    @SerializedName("CHANGE24HOUR")
    val change24Hour: String,
    @SerializedName("CHANGEDAY")
    val changeDay: String,
    @SerializedName("CHANGEHOUR")
    val changeHour: String,
    @SerializedName("CHANGEPCT24HOUR")
    val changePct24Hour: String,
    @SerializedName("CHANGEPCTDAY")
    val changePctDay: String,
    @SerializedName("CHANGEPCTHOUR")
    val changePctHour: String,
    @SerializedName("CIRCULATINGSUPPLY")
    val circulatingSupply: String,
    @SerializedName("CIRCULATINGSUPPLYMKTCAP")
    val circulatingSupplyMktCap: String,
    @SerializedName("CONVERSIONLASTUPDATE")
    val conversionLastUpdate: String,
    @SerializedName("CONVERSIONSYMBOL")
    val conversionSymbol: String,
    @SerializedName("CONVERSIONTYPE")
    val conversionType: String,
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String,
    @SerializedName("HIGH24HOUR")
    val high24hour: String,
    @SerializedName("HIGHDAY")
    val highDay: String,
    @SerializedName("HIGHHOUR")
    val highHour: String,
    @SerializedName("IMAGEURL")
    val imageUrl: String,
    @SerializedName("LASTMARKET")
    val lastMarket: String,
    @SerializedName("LASTTRADEID")
    val lastTradeid: String,
    @SerializedName("LASTUPDATE")
    val lastUpdate: String,
    @SerializedName("LASTVOLUME")
    val lastVolume: String,
    @SerializedName("LASTVOLUMETO")
    val lastVolumeto: String,
    @SerializedName("LOW24HOUR")
    val low24hour: String,
    @SerializedName("LOWDAY")
    val lowDay: String,
    @SerializedName("LOWHOUR")
    val lowHour: String,
    @SerializedName("MARKET")
    val market: String,
    @SerializedName("MKTCAP")
    val mktCap: String,
    @SerializedName("MKTCAPPENALTY")
    val mktCapPenalty: String,
    @SerializedName("OPEN24HOUR")
    val open24hour: String,
    @SerializedName("OPENDAY")
    val openDay: String,
    @SerializedName("OPENHOUR")
    val openHour: String,
    @SerializedName("PRICE")
    val price: String,
    @SerializedName("SUPPLY")
    val supply: String,
    @SerializedName("TOPTIERVOLUME24HOUR")
    val topTierVolume24hour: String,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    val topTierVolume24Hourto: String,
    @SerializedName("TOSYMBOL")
    val toSymbol: String,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    val totalTopTierVolume24h: String,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    val totalTopTierVolume24hto: String,
    @SerializedName("TOTALVOLUME24H")
    val totalVolume24h: String,
    @SerializedName("TOTALVOLUME24HTO")
    val totalVolume24hto: String,
    @SerializedName("VOLUME24HOUR")
    val volume24hour: String,
    @SerializedName("VOLUME24HOURTO")
    val volume24HourTo: String,
    @SerializedName("VOLUMEDAY")
    val volumeDay: String,
    @SerializedName("VOLUMEDAYTO")
    val volumeDayTo: String,
    @SerializedName("VOLUMEHOUR")
    val volumeHour: String,
    @SerializedName("VOLUMEHOURTO")
    val volumeHourTo: String
)