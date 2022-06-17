package io.simsim.anime.ui.screen.search

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.text.input.ImeAction
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import io.simsim.anime.data.entity.AnimeType

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
    val results = vm.queryResult.collectAsLazyPagingItems()
    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = {
                        nvc.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBackIosNew,
                            contentDescription = "back"
                        )
                    }
                },
                actions = {
                    OutlinedTextField(
                        modifier = Modifier.onKeyEvent {
                            if (it.key == Key.Enter) {
                                vm.search(
                                    query, type
                                )
                                true
                            } else false
                        },
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
            )
        }
    ) {
        LazyColumn(modifier = Modifier.padding(it)) {
            items(results) { result ->
                result ?: return@items
                Text(text = result.title.ifBlank { "ppp" })
            }
        }
    }
}