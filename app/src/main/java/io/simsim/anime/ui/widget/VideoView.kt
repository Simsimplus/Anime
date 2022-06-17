package io.simsim.anime.ui.widget

import android.annotation.SuppressLint
import android.webkit.WebResourceRequest
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState
import io.simsim.anime.utils.compose.placeholder

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YTBVideo(
    modifier: Modifier = Modifier,
    ytbId: String
) {
    val mediaUrl =
        "https://www.youtube.com/embed/${ytbId}?iv_load_policy=3&wmode=opaque&autoplay=0&controls=0"
    val state = rememberWebViewState(url = mediaUrl)
    WebView(
        modifier = modifier.placeholder(state.isLoading),
        captureBackPresses = false,
        state = state,
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        },
        client = object : AccompanistWebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                // Override all url loads to make the single source of truth
                // of the URL the state holder Url
                return true
            }
        }
    )
}