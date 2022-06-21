package io.simsim.anime.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import io.simsim.anime.R

@Composable
fun Loading(
    modifier: Modifier = Modifier,
    loading: Boolean
) {
    val lottieComposition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading))
    if (loading) {
        Box(modifier = modifier) {
            LottieAnimation(
                modifier = Modifier.align(Alignment.Center),
                composition = lottieComposition,
                iterations = Int.MAX_VALUE
            )
        }
    }
}
