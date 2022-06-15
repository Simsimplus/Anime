package io.simsim.anime.ui.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import io.simsim.anime.data.entity.TopAnimeResponse
import io.simsim.anime.navi.NaviRoute
import io.simsim.anime.ui.widget.ScoreStar
import io.simsim.anime.utils.compose.CoilImage
import io.simsim.anime.utils.compose.items
import io.simsim.anime.utils.compose.placeholder

@Composable
fun MainScreen(
    vm: MainVM = hiltViewModel(),
    nvc: NavHostController
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
                    TopAnimeCard(anime = top, imageSize = imageSize, placeholder = false) {
                        nvc.navigate(NaviRoute.Detail.getDetailRoute(top.malId))
                    }
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
    placeholder: Boolean,
    onClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(3.dp)
    ) {
        CoilImage(
            modifier = Modifier,
            model = anime.images.webp.imageUrl,
            imageSize = imageSize,
            contentDescription = "",
            showPlaceholderAlways = placeholder
        )
        Text(
            modifier = Modifier.placeholder(
                visible = placeholder,
            ),
            text = if (placeholder) "placeholder" else anime.title,
            style = MaterialTheme.typography.labelSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
        ScoreStar(
            modifier = Modifier.placeholder(
                visible = placeholder,
            ),
            score = anime.score.coerceIn(0f, 10f)
        )
    }
}
