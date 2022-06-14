package io.simsim.anime.ui.screen.main

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    vm: MainVM = viewModel()
) {
    val recommendations by vm.recommendations.collectAsState(initial = emptyList())
    LazyVerticalGrid(columns = GridCells.Fixed(2)) {
        items(recommendations) { recommendation ->
            Card {
                recommendation.content
            }
        }
    }
}