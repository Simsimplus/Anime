package io.simsim.anime.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
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
import io.simsim.anime.ui.theme.ScoreColor
import io.simsim.anime.ui.widget.CenterAlignRow
import io.simsim.anime.ui.widget.Loading
import io.simsim.anime.ui.widget.ScoreStars
import io.simsim.anime.utils.compose.CoilImage
import io.simsim.anime.utils.compose.placeholder
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchScreen(
    nvc: NavHostController,
    vm: SearchVM = hiltViewModel()
) {
    val cs = rememberCoroutineScope()
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var type by remember { mutableStateOf(AnimeType.TV) }
    val searchState by vm.searchState.collectAsState()
    val searchResultCount =
        (searchState as? SearchVM.SearchState.Success)?.pageInfo?.items?.total ?: 0
    val searching = searchState is SearchVM.SearchState.Searching
    val results = vm.queryResult.collectAsLazyPagingItems()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    val lazyListState = rememberLazyListState()
    LaunchedEffect(searchState, focusRequester) {
        if (searchState is SearchVM.SearchState.Searching) {
            focusRequester.freeFocus()
            keyboardController?.hide()
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                hostState = snackbarHostState
            )
        },
        topBar = {
            CenterAlignRow(
                modifier = Modifier.padding(3.dp),
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
                            if (query.isNotBlank()) {
                                vm.search(
                                    query, type
                                )
                            } else {
                                cs.launch {
                                    snackbarHostState.showSnackbar(message = "query can't be blank!")
                                }
                            }
                        }
                    )
                )
            }
        },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (searchState) {
                SearchVM.SearchState.Empty -> {
                    Text(modifier = Modifier.align(Alignment.Center), text = "no result!")
                }
                SearchVM.SearchState.Fail -> {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "error occur, please retry!"
                    )
                }
                SearchVM.SearchState.Init -> {
                    Text(modifier = Modifier.align(Alignment.Center), text = "type and search!")
                }
                SearchVM.SearchState.Searching -> {
                    Loading(modifier = Modifier.fillMaxSize(), loading = searching)
                }
                is SearchVM.SearchState.Success -> {
                    LazyColumn(
                        state = lazyListState,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            if (results.itemCount > 0) {
                                Text(
                                    text = "find $searchResultCount",
                                    style = MaterialTheme.typography.titleSmall
                                )
                            }
                        }
                        items(results, key = { it.malId }) { anime ->
                            anime?.let {
                                SearchResultCard(
                                    modifier = Modifier.clickable {
                                        nvc.navigate(
                                            NaviRoute.Detail.getDetailRoute(anime.malId)
                                        ) {
                                            this.popUpTo(NaviRoute.Search.route) {
                                                saveState = true
                                            }
                                            restoreState = true
                                        }
                                    },
                                    anime = anime
                                )
                            } ?: SearchResultCard(
                                modifier = Modifier.placeholder(true),
                                anime = SearchAnimeResponse.SearchAnimeData(),
                            )
                        }
                    }
                }
            }


        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchResultCard(
    modifier: Modifier = Modifier,
    anime: SearchAnimeResponse.SearchAnimeData,
) {
    Card(modifier = modifier) {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .padding(8.dp),
        ) {
            val imageSize = DpSize(
                maxHeight * 0.618f, maxHeight
            )
            Row {
                CoilImage(
                    model = anime.images.webp.imageUrl,
                    contentDescription = "anime image",
                    imageSize = imageSize
                )
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
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
                    Row(verticalAlignment = Alignment.Bottom) {
                        ScoreStars(score = anime.score.toFloat(), starSize = 14.dp)
                        Text(
                            text = anime.score.toString(),
                            style = MaterialTheme.typography.labelSmall.copy(color = ScoreColor)
                        )
                    }
                }
            }
        }
    }
}