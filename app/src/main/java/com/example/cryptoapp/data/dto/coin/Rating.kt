package com.example.cryptoapp.data.dto.coin


import com.google.gson.annotations.SerializedName

data class Rating(
    @SerializedName("Weiss")
    val weiss: Weiss
)