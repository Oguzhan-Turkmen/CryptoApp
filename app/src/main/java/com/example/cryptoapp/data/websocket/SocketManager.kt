package com.example.cryptoapp.data.websocket


import android.util.Log
import com.example.cryptoapp.data.websocket.model.Subscription
import com.example.cryptoapp.data.websocket.model.WsCoinData
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject

class SocketManager @Inject constructor(
    private val json: Json,
    private val okHttpClient: OkHttpClient,
    private val request: Request
) {
    private lateinit var socket: WebSocket
    private var prevValue: Double = 0.0
    private var nextValue: Double = 0.0
    private var differenceRes = Difference.Stable

    fun handleWs(coinName: String): Flow<Price> {
        val events: Flow<Price> = callbackFlow {
            val listener = websocketListener(coinName)
            socket = okHttpClient.newWebSocket(request, listener)
            awaitClose {
                unSubscribe(emptyList())
                socket.cancel()
            }
        }
        return events
    }

    private fun ProducerScope<Price>.websocketListener(coinName: String): WebSocketListener {
        return object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                super.onOpen(webSocket, response)
                subscribe(listOf(coinName))
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)
                Log.d(
                    "OkSocket2",
                    text
                )
                val channel = json.decodeFromString<WsCoinData>(text)
                prevValue = nextValue
                Log.e("prevValue", prevValue.toString())
                nextValue = channel.price
                Log.e("nextValue", nextValue.toString())
                var exchangeValue = nextValue - prevValue
                Log.e("exchangeValue", exchangeValue.toString())
                if (prevValue == 0.0) {
                    differenceRes = Difference.Stable
                    exchangeValue = 0.0
                } else {
                    differenceRes = if (exchangeValue > 0) {
                        Difference.UP
                    } else if (exchangeValue < 0) {
                        Difference.Down
                    } else {
                        Difference.Stable
                    }
                }
                if (nextValue != 0.0 && prevValue != 0.0)
                    trySendBlocking(Price(nextValue, differenceRes, exchangeValue))
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                super.onMessage(webSocket, bytes)
                onMessage(webSocket, String(bytes.toByteArray()))
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosing(webSocket, code, reason)
                Log.d("SocketState", "Closing")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)
                Log.d("SocketState", "Closed")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)
                Log.d("SocketState", response.toString())
            }
        }
    }

    private fun subscribe(coinList: List<String>) {
        val message = Subscription(
            action = SUBSCRIBE_MESSAGE,
            subs = coinList
        )
        socket.send(json.encodeToString(message))
    }

    private fun unSubscribe(coinList: List<String>) {
        val message = Subscription(
            action = UNSUBSCRIBE_MESSAGE,
            subs = coinList
        )
        socket.send(json.encodeToString(message))
    }

    companion object {
        private const val SUBSCRIBE_MESSAGE = "SubAdd"
        private const val UNSUBSCRIBE_MESSAGE = "SubRemove"
    }
}

data class Price(
    val value: Double,
    val exchange: Difference,
    val exchangeValue: Double
)

enum class Difference {
    UP,
    Down,
    Stable
}