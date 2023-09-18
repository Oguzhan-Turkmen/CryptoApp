package com.example.cryptoapp.data.dto.coin


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("CHANGE24HOUR")
    val change24hour: Double,
    @SerializedName("CHANGEDAY")
    val changeDay: Double,
    @SerializedName("CHANGEHOUR")
    val changeHour: Double,
    @SerializedName("CHANGEPCT24HOUR")
    val changePct24hour: Double,
    @SerializedName("CHANGEPCTDAY")
    val changePctDay: Double,
    @SerializedName("CHANGEPCTHOUR")
    val changePctHour: Double,
    @SerializedName("CIRCULATINGSUPPLY")
    val circulatingSupply: Double,
    @SerializedName("CIRCULATINGSUPPLYMKTCAP")
    val circulatingSupplyMktCap: Double,
    @SerializedName("CONVERSIONLASTUPDATE")
    val conversionLastUpdate: Int,
    @SerializedName("CONVERSIONSYMBOL")
    val conversionSymbol: String,
    @SerializedName("CONVERSIONTYPE")
    val conversionType: String,
    @SerializedName("FLAGS")
    val flags: String,
    @SerializedName("FROMSYMBOL")
    val fromSymbol: String,
    @SerializedName("HIGH24HOUR")
    val high24hour: Double,
    @SerializedName("HIGHDAY")
    val highDay: Double,
    @SerializedName("HIGHHOUR")
    val highHour: Double,
    @SerializedName("IMAGEURL")
    val imageUrl: String,
    @SerializedName("LASTMARKET")
    val lastMarket: String,
    @SerializedName("LASTTRADEID")
    val lastTradeId: String,
    @SerializedName("LASTUPDATE")
    val lastUpdate: Int,
    @SerializedName("LASTVOLUME")
    val lastVolume: Double,
    @SerializedName("LASTVOLUMETO")
    val lastVolumeTo: Double,
    @SerializedName("LOW24HOUR")
    val low24hour: Double,
    @SerializedName("LOWDAY")
    val lowDay: Double,
    @SerializedName("LOWHOUR")
    val lowHour: Double,
    @SerializedName("MARKET")
    val market: String,
    @SerializedName("MEDIAN")
    val median: Double,
    @SerializedName("MKTCAP")
    val mktCap: Double,
    @SerializedName("MKTCAPPENALTY")
    val mktCapPenalty: Double,
    @SerializedName("OPEN24HOUR")
    val open24hour: Double,
    @SerializedName("OPENDAY")
    val openDay: Double,
    @SerializedName("OPENHOUR")
    val openHour: Double,
    @SerializedName("PRICE")
    val price: Double,
    @SerializedName("SUPPLY")
    val supply: Double,
    @SerializedName("TOPTIERVOLUME24HOUR")
    val topTierVolume24hour: Double,
    @SerializedName("TOPTIERVOLUME24HOURTO")
    val topTierVolume24hourTo: Double,
    @SerializedName("TOSYMBOL")
    val toSymbol: String,
    @SerializedName("TOTALTOPTIERVOLUME24H")
    val totalTopTierVolume24h: Double,
    @SerializedName("TOTALTOPTIERVOLUME24HTO")
    val totalTopTierVolume24hto: Double,
    @SerializedName("TOTALVOLUME24H")
    val totalVolume24h: Double,
    @SerializedName("TOTALVOLUME24HTO")
    val totalVolume24hto: Double,
    @SerializedName("TYPE")
    val type: String,
    @SerializedName("VOLUME24HOUR")
    val volume24hour: Double,
    @SerializedName("VOLUME24HOURTO")
    val volume24hourTo: Double,
    @SerializedName("VOLUMEDAY")
    val volumeDay: Double,
    @SerializedName("VOLUMEDAYTO")
    val volumeDayTo: Double,
    @SerializedName("VOLUMEHOUR")
    val volumeHour: Double,
    @SerializedName("VOLUMEHOURTO")
    val volumeHourTo: Double
)