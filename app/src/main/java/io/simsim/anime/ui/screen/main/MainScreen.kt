package io.simsim.anime.ui.screen.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import io.simsim.anime.data.entity.TopAnimeResponse
import io.simsim.anime.navi.NaviRoute
import io.simsim.anime.ui.theme.ScoreColor
import io.simsim.anime.ui.widget.Loading
import io.simsim.anime.ui.widget.ScoreStars
import io.simsim.anime.utils.compose.CoilImage
import io.simsim.anime.utils.compose.items
import io.simsim.anime.utils.compose.placeholder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainVM = hiltViewModel(),
    nvc: NavHostController
) {
    val mainState by vm.mainState.collectAsState()
    val loading = mainState is MainVM.MainState.Loading
    val topAppBarScrollState = rememberTopAppBarScrollState()
    val topAppBarScrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(
        state = topAppBarScrollState
    )
    val maxWidth = LocalConfiguration.current.screenWidthDp.dp
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {
                    Text(text = "Top Anime")
                },
                actions = {
                    IconButton(onClick = {
                        nvc.navigate(NaviRoute.Search.route)
                    }) {
                        Icon(imageVector = Icons.Rounded.Search, contentDescription = "search")
                    }
                },
                scrollBehavior = topAppBarScrollBehavior
            )
        },
        floatingActionButton = {
            if (!loading && topAppBarScrollState.offset < 0) {
                FloatingActionButton(onClick = {
                    nvc.navigate(NaviRoute.Search.route)
                }) {
                    Icon(imageVector = Icons.Rounded.Search, contentDescription = "search")
                }
            }
        }
    ) {
        val topAnimeList = vm.recommendations.collectAsLazyPagingItems()
        val gap = 8.dp
        val columnCount = 3
        val cardWidth = (maxWidth - gap.times(columnCount - 1)).div(columnCount)
        val cardHeight = cardWidth.times(1.618f)
        val imageSize = DpSize(cardWidth, cardHeight)
        LazyVerticalGrid(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
                .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection),
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
        Loading(
            modifier = Modifier.fillMaxSize(),
            loading = loading && topAnimeList.itemCount == 0
        )
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
        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            ScoreStars(
                modifier = Modifier.placeholder(
                    visible = placeholder,
                ),
                score = anime.score.coerceIn(0f, 10f)
            )
            Text(
                text = anime.score.toString(),
                style = MaterialTheme.typography.labelSmall.copy(color = ScoreColor)
            )
        }

    }
}
