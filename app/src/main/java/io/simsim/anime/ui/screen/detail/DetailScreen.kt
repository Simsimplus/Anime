package io.simsim.anime.ui.screen

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.DpSize
import androidx.hilt.navigation.compose.hiltViewModel
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.ui.screen.detail.AnimeDetailVM
import io.simsim.anime.utils.compose.CoilImage

@Composable
fun AnimeDetailScreen(
    vm: AnimeDetailVM = hiltViewModel(),
    mailId: Int
) {
    val animeFullData by vm.getAnimeDetailFull(mailId)
        .collectAsState(initial = AnimeFullResponse.AnimeFullData())
    val loading = false
    BoxWithConstraints {
        val imageWidth = maxWidth / 3
        val imageHeight = imageWidth.times(1.618f)
        val imageSize = DpSize(imageWidth, imageHeight)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row {
                CoilImage(
                    model = animeFullData.images.webp.imageUrl,
                    contentDescription = "image",
                    imageSize = imageSize,
                    showPlaceholderAlways = loading
                )
            }
        }
    }

}