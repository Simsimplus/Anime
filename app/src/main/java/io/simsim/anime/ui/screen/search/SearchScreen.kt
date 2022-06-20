package io.simsim.anime.ui.screen.search

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.entity.SearchAnimeResponse
import io.simsim.anime.navi.NaviRoute
import io.simsim.anime.ui.widget.CenterAlignRow
import io.simsim.anime.utils.compose.CoilImage

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    nvc: NavHostController,
    vm: SearchVM = hiltViewModel()
) {
    var query by remember {
        mutableStateOf("")
    }
    var type by remember {
        mutableStateOf(AnimeType.TV)
    }
    val searchState by vm.searchState.collectAsState()
    val searching = searchState is SearchVM.SearchState.Searching
    val results = vm.queryResult.collectAsLazyPagingItems()
    val focusRequester = remember {
        FocusRequester()
    }
    val keyboardController = LocalSoftwareKeyboardController.current
    LaunchedEffect(searchState, focusRequester) {
        if (searchState is SearchVM.SearchState.Searching) {
            focusRequester.freeFocus()
            keyboardController?.hide()
        }
    }
    Scaffold(
        topBar = {
            CenterAlignRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                IconButton(onClick = {
                    nvc.navigateUp()
                }) {
                    Icon(
                        imageVector = Icons.Rounded.ArrowBackIosNew,
                        contentDescription = "back"
                    )
                }
                OutlinedTextField(
                    modifier = Modifier
                        .onKeyEvent {
                            if (it.key == Key.Enter) {
                                vm.search(
                                    query, type
                                )
                                true
                            } else false
                        }
                        .focusRequester(focusRequester)
                        .weight(1f),
                    value = query,
                    onValueChange = { query = it },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            vm.search(
                                query, type
                            )
                        }
                    )
                )
            }
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn {
                items(results) { anime ->
                    anime ?: return@items
                    SearchResultCard(
                        anime = anime
                    ) {
                        nvc.navigate(
                            NaviRoute.Detail.getDetailRoute(anime.malId)
                        )
                    }
                }
            }
            if (searching) {
                Surface(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .wrapContentSize()
                            .align(Alignment.Center),
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultCard(
    anime: SearchAnimeResponse.SearchAnimeData,
    onClick: () -> Unit = {}
) {
    Card(onClick = onClick) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
        ) {
            Row {
                CoilImage(
                    model = anime.images.webp.imageUrl,
                    contentDescription = "anime image",
                    imageSize = DpSize(width = 100.dp.times(0.618f), 100.dp)
                )
                val genres = anime.genres.joinToString("/") {
                    it.name
                }
                val label =
                    "${anime.status}/" +
                            (if (anime.airing) "${anime.broadcast.string}/" else "") +
                            "$genres/" +
                            "${anime.aired.from.substringBefore("T")}/" +
                            "${anime.episodes} episodes"
                Column(
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = anime.title,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = "${anime.titleJapanese}(${anime.year})",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = label,
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}