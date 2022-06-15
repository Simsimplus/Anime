package io.simsim.anime.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.compose.AsyncImagePainter
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import io.simsim.anime.data.entity.TopAnimeResponse
import io.simsim.anime.ui.widget.ScoreStar
import io.simsim.anime.utils.compose.items

@Composable
fun MainScreen(
    vm: MainVM = viewModel()
) {
    val topAnimeList = vm.recommendations.collectAsLazyPagingItems()
    val gap = 8.dp
    val padding = 16.dp
    BoxWithConstraints(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        val columnCount = 3
        val cardWidth = (maxWidth - gap.times(columnCount - 1)).div(columnCount)
        val cardHeight = cardWidth.times(1.618f)
        val imageSize = DpSize(cardWidth, cardHeight)
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            horizontalArrangement = Arrangement.spacedBy(gap),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            items(
                items = topAnimeList,
                key = {
                    it.malId
                }
            ) { top ->
                top?.let {
                    TopAnimeCard(anime = top, imageSize = imageSize, placeholder = false)
                } ?: TopAnimeCard(
                    anime = TopAnimeResponse.TopAnimeData(),
                    imageSize = imageSize,
                    placeholder = true
                )
            }
        }
    }
}

@Composable
fun TopAnimeCard(
    anime: TopAnimeResponse.TopAnimeData,
    imageSize: DpSize,
    placeholder: Boolean
) {
    var imageLoading by remember {
        mutableStateOf(true)
    }
    val placeholderVisible = placeholder || imageLoading
    Column(
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        AsyncImage(
            modifier = Modifier
                .size(imageSize)
                .clip(MaterialTheme.shapes.small)
                .placeholder(
                    visible = placeholderVisible,
                    color = Color.White,
                    highlight = PlaceholderHighlight.shimmer(
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                    )
                ),
            model = anime.images.webp.imageUrl,
            contentDescription = "",
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop,
            onState = {
                imageLoading = it is AsyncImagePainter.State.Loading
            }
        )
        Text(
            modifier = Modifier.placeholder(
                visible = placeholderVisible,
                color = Color.White,
                highlight = PlaceholderHighlight.shimmer(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ),
            text = if (placeholder) "placeholder" else anime.title,
            style = MaterialTheme.typography.labelSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        ScoreStar(
            modifier = Modifier.placeholder(
                visible = placeholderVisible,
                color = Color.White,
                highlight = PlaceholderHighlight.shimmer(
                    MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                )
            ),
            score = anime.score.coerceIn(0f, 10f)
        )
    }
}
