package io.simsim.anime.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.pagination.TopAnimeRemoteMediator
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    db: AnimeDataBase,
    rm: TopAnimeRemoteMediator
) : ViewModel() {
    val mainState = rm.mainState

    @OptIn(ExperimentalPagingApi::class)
    val recommendations = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true
        ),
        remoteMediator = rm
    ) {
        db.topAnimeDao().getAllPS()
    }.flow.cachedIn(viewModelScope)

    sealed class MainState {
        object Loading : MainState()
        object Success : MainState()
        object Fail : MainState()
    }
}
