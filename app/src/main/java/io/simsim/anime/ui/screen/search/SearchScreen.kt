package io.simsim.anime.ui.screen.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.entity.RandomAnimeResponse
import io.simsim.anime.data.entity.SearchAnimeResponse
import io.simsim.anime.navi.NaviRoute
import io.simsim.anime.network.repo.RequestState
import io.simsim.anime.ui.theme.ScoreColor
import io.simsim.anime.ui.widget.CenterAlignRow
import io.simsim.anime.ui.widget.Loading
import io.simsim.anime.ui.widget.ScoreStars
import io.simsim.anime.utils.compose.CoilImage
import io.simsim.anime.utils.compose.placeholder
import io.simsim.anime.utils.sensor.shakeEventFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalAnimationApi::class
)
@Composable
fun SearchScreen(
    nvc: NavHostController,
    vm: SearchVM = hiltViewModel()
) {
    val cs = rememberCoroutineScope()
    val lifecycleOwner = LocalLifecycleOwner.current
    var query by rememberSaveable {
        mutableStateOf("")
    }
    var showRandomDialog by remember {
        mutableStateOf(false)
    }
    LaunchedEffect(true) {
        lifecycleOwner.shakeEventFlow.collectLatest {
            showRandomDialog = it
        }
    }
    LaunchedEffect(query) {
        if (query.isBlank()) {
            vm.clearQuery()
        }
    }
    var type by rememberSaveable { mutableStateOf(AnimeType.TV) }
    val searchState by vm.searchState.collectAsState()
    val results = vm.queryResult.collectAsLazyPagingItems()
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current
    val snackbarHostState = remember { SnackbarHostState() }
    LaunchedEffect(searchState, focusRequester) {
        if (searchState is SearchVM.SearchState.Searching) {
            focusRequester.freeFocus()
            keyboardController?.hide()
        }
    }
    if (showRandomDialog) {
        val randomAnimeState by vm.getRandomAnimeState().collectAsState()
        Dialog(
            onDismissRequest = { showRandomDialog = false },
            content = {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        when (randomAnimeState) {
                            is RequestState.Failure -> {
                                Text(text = "error")
                            }
                            RequestState.Started -> {
                                CircularProgressIndicator()
                            }
                            is RequestState.Success<*> -> {
                                val anime =
                                    ((randomAnimeState as RequestState.Success<*>).data as RandomAnimeResponse).data
                                Text(text = anime.title)
                                TextButton(
                                    modifier = Modifier.align(Alignment.End),
                                    onClick = {
                                        showRandomDialog = false
                                        nvc.navigate(NaviRoute.Detail.getDetailRoute(anime.malId))
                                    }
                                ) {
                                    Text(text = "go")
                                }
                            }
                        }
                    }
                }
            }
        )

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
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            AnimatedVisibility(visible = searchState is SearchVM.SearchState.Success) {
                ScrollableTabRow(
                    selectedTabIndex = AnimeType.values().indexOf(type),
                    edgePadding = 8.dp
                ) {
                    AnimeType.values().forEach { tabType ->
                        Tab(
                            selected = tabType == type,
                            onClick = {
                                type = tabType
                                vm.search(query, type)
                            },
                            text = { Text(text = tabType.type) }
                        )
                    }
                }
            }

            SearchTabContent(
                searchState, results
            ) { anime ->
                nvc.navigate(
                    NaviRoute.Detail.getDetailRoute(anime.malId)
                )
            }
        }
    }
}

@Composable
fun SearchTabContent(
    searchState: SearchVM.SearchState,
    searchResults: LazyPagingItems<SearchAnimeResponse.SearchAnimeData>,
    onSearchResultCardClick: (SearchAnimeResponse.SearchAnimeData) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
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
                Loading(modifier = Modifier.fillMaxSize(), loading = true)
            }
            is SearchVM.SearchState.Success -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        if (searchResults.itemCount > 0) {
                            Text(
                                text = "find ${searchState.pageInfo.items.total}",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                    }
                    items(searchResults, key = { it.malId }) { anime ->
                        anime?.let {
                            SearchResultCard(
                                modifier = Modifier.clickable {
                                    onSearchResultCardClick(anime)
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
                    modifier = Modifier.size(imageSize),
                    model = anime.images.webp.imageUrl,
                    contentDescription = "anime image",
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
