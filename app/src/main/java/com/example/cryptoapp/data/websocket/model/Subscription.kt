package com.example.cryptoapp.data.websocket.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Subscription(
    @SerialName("action")
    val action: String,
    @SerialName("subs")
    val subs: List<String> = emptyList(),
)
