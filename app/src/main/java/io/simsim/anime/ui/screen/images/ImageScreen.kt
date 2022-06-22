package io.simsim.anime.ui.screen.images

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import io.simsim.anime.utils.compose.CoilImage

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageScreen(
    vm: ImageVm = hiltViewModel(),
    malId: Int
) {
    LaunchedEffect(malId) {
        vm.getAnimeImages(malId)
    }
    val animeImages by vm.animeImages.collectAsState()
    HorizontalPager(count = animeImages.size) { index ->
        val imageUrl = animeImages[index].webp.largeImageUrl
        CoilImage(
            modifier = Modifier.fillMaxSize(),
            model = imageUrl,
            contentDescription = "image",
        )
    }
}