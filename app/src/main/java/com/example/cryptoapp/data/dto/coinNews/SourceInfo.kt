package com.example.cryptoapp.data.dto.coinNews


import com.google.gson.annotations.SerializedName

data class SourceInfo(
    @SerializedName("img")
    val img: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("name")
    val name: String
)