package com.example.cryptoapp.presentation.news

import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import java.net.URLDecoder

@Composable
fun CoinNewsWebViewScreen(
    coinNewUrl: String,
) {
    val decodedUrl = URLDecoder.decode(coinNewUrl, "UTF-8")
    AndroidView(factory = {
        WebView(it).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            webViewClient = WebViewClient()
            loadUrl(decodedUrl)
        }
    }, update = {
        it.loadUrl(decodedUrl)
    })

}
