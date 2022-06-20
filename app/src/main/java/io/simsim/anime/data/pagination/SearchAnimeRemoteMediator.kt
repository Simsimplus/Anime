package io.simsim.anime.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.entity.SearchAnimeResponse
import io.simsim.anime.network.repo.JikanRepo
import io.simsim.anime.ui.screen.search.SearchVM
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalPagingApi::class)
class SearchAnimeRemoteMediator(
    private val repo: JikanRepo,
    private val db: AnimeDataBase,
    private val query: Pair<String, AnimeType>,
    private val searchState: MutableStateFlow<SearchVM.SearchState>
) : RemoteMediator<Int, SearchAnimeResponse.SearchAnimeData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchAnimeResponse.SearchAnimeData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                searchState.emit(SearchVM.SearchState.Searching)
                1
            }
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> db.searchDao().getNextPage()
                ?: return MediatorResult.Success(false)
            else -> {
                return MediatorResult.Success(true)
            }
        }
        return repo.searchAnime(query.first, query.second, page).fold(
            onSuccess = { response ->
                val endOfPaginationReached = !response.pagination.hasNextPage
                db.searchDao().insertResponse(response)
                searchState.emit(SearchVM.SearchState.Success)
                MediatorResult.Success(endOfPaginationReached)
            },
            onFailure = {
                searchState.emit(SearchVM.SearchState.Fail)
                MediatorResult.Error(it)
            }
        )
    }
}