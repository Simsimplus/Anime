package io.simsim.anime.ui.screen.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
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
        LazyVerticalGrid(
            columns = GridCells.Fixed(columnCount),
            horizontalArrangement = Arrangement.spacedBy(gap),
            verticalArrangement = Arrangement.spacedBy(gap),
        ) {
            items(topAnimeList) { top ->
                top ?: return@items
                Column {
                    AsyncImage(
                        modifier = Modifier
                            .size(cardWidth, cardHeight)
                            .clip(MaterialTheme.shapes.small),
                        model = top.images.webp.imageUrl,
                        contentDescription = "",
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = top.titleJapanese,
                        style = MaterialTheme.typography.labelSmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    ScoreStar(
                        top.score.coerceIn(0f, 10f)
                    )
                }
            }
        }
    }
}
