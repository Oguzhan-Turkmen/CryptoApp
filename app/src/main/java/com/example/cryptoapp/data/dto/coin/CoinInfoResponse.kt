package com.example.cryptoapp.data.dto.coin


import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class CoinInfoResponse(
    @SerializedName("Algorithm")
    val algorithm: String,
    @SerializedName("AssetLaunchDate")
    val assetLaunchDate: String,
    @SerializedName("BlockNumber")
    val blockNumber: Long,
    @SerializedName("BlockReward")
    val blockReward: Double,
    @SerializedName("BlockTime")
    val blockTime: Double,
    @SerializedName("DocumentType")
    val documentType: String,
    @SerializedName("FullName")
    val fullName: String,
    @SerializedName("Id")
    val id: String,
    @SerializedName("ImageUrl")
    val imageUrl: String,
    @SerializedName("Internal")
    val `internal`: String,
    @SerializedName("MaxSupply")
    val maxSupply: Double,
    @SerializedName("Name")
    val name: String,
    @SerializedName("NetHashesPerSecond")
    val netHashesPerSecond: BigDecimal,
    @SerializedName("ProofType")
    val proofType: String,
    @SerializedName("Rating")
    val rating: Rating,
    @SerializedName("Type")
    val type: Int,
    @SerializedName("Url")
    val url: String
)