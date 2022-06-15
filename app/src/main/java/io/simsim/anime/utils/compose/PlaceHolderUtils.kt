package io.simsim.anime.utils.compose

import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.placeholder
import com.google.accompanist.placeholder.material.shimmer

fun Modifier.placeholder(
    visible: Boolean,
) = composed {
    placeholder(
        visible = visible,
        highlight = PlaceholderHighlight.shimmer()
    )
}