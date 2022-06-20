package io.simsim.anime.ui.screen.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.pagination.SearchAnimeRemoteMediator
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchVM @Inject constructor(
    repo: JikanRepo,
    private val db: AnimeDataBase
) : ViewModel() {

    private val queryFlow = MutableSharedFlow<Pair<String, AnimeType>>()
    private val _searchState = MutableStateFlow<SearchState>(SearchState.NoResult)
    val searchState: StateFlow<SearchState>
        get() = _searchState

    @OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
    val queryResult = queryFlow.flatMapLatest {
        Pager(
            config = PagingConfig(
                pageSize = 10
            ),
            remoteMediator = SearchAnimeRemoteMediator(
                repo = repo, db = db, query = it, searchState = _searchState
            )
        ) {
            db.searchDao().getAllPS()
        }.flow
    }

    fun search(query: String, type: AnimeType) = viewModelScope.launch {
        db.searchDao().clear()
        queryFlow.emit(query to type)
    }

    sealed class SearchState {
        object NoResult : SearchState()
        object Searching : SearchState()
        object Success : SearchState()
        object Fail : SearchState()
    }

}