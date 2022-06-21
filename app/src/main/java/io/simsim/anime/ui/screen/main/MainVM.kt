package io.simsim.anime.ui.screen.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.TopAnimeFilterType
import io.simsim.anime.data.pagination.TopAnimeRemoteMediator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    db: AnimeDataBase,
    rm: TopAnimeRemoteMediator
) : ViewModel() {
    val mainState = rm.mainState

    private val _filter = MutableStateFlow(TopAnimeFilterType.Score)
    val filter = _filter.asStateFlow()

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    val recommendations = _filter.flatMapLatest { filterType ->
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true
            ),
            remoteMediator = rm.apply {
                filter = filterType
            }
        ) {
            db.topAnimeDao().getAllPS()
        }.flow.cachedIn(viewModelScope)
    }

    fun filter(filterType: TopAnimeFilterType) = viewModelScope.launch {
        _filter.emit(filterType)
    }

    sealed class MainState {
        object Loading : MainState()
        object Success : MainState()
        object Fail : MainState()
    }
}
