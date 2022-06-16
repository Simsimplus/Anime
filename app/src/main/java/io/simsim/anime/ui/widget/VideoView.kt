package io.simsim.anime.ui.widget

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.web.WebView
import com.google.accompanist.web.rememberWebViewState

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun YTBVideo(
    modifier: Modifier = Modifier,
    ytbId: String
) {
    val mediaUrl =
        "https://www.youtube.com/embed/${ytbId}?iv_load_policy=3&wmode=opaque&autoplay=0&controls=0"
    WebView(
        modifier = modifier,
        captureBackPresses = false,
        state = rememberWebViewState(url = mediaUrl),
        onCreated = { webView ->
            webView.settings.javaScriptEnabled = true
        }
    )
}