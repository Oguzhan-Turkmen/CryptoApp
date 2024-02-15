package com.example.cryptoapp.data.dto.coinNews


import com.google.gson.annotations.SerializedName

data class CoinNew(
    @SerializedName("body")
    val body: String,
    @SerializedName("categories")
    val categories: String,
    @SerializedName("downvotes")
    val downVotes: String,
    @SerializedName("guid")
    val guid: String,
    @SerializedName("id")
    val id: String,
    @SerializedName("imageurl")
    val imageUrl: String,
    @SerializedName("lang")
    val lang: String,
    @SerializedName("published_on")
    val publishedOn: Int,
    @SerializedName("source")
    val source: String,
    @SerializedName("source_info")
    val sourceInfo: SourceInfo,
    @SerializedName("tags")
    val tags: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("upvotes")
    val upVotes: String,
    @SerializedName("url")
    val url: String
)