package io.simsim.anime.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.TopAnimeFilterType
import io.simsim.anime.data.entity.TopAnimeResponse
import io.simsim.anime.network.repo.JikanRepo
import io.simsim.anime.ui.screen.main.MainVM
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TopAnimeRemoteMediator @Inject constructor(
    private val repo: JikanRepo,
    private val db: AnimeDataBase
) : RemoteMediator<Int, TopAnimeResponse.TopAnimeData>() {

    private val _mainState = MutableStateFlow<MainVM.MainState>(MainVM.MainState.Loading)
    val mainState = _mainState.asStateFlow()
    var filter: TopAnimeFilterType = TopAnimeFilterType.Score

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopAnimeResponse.TopAnimeData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                1
            }
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> db.topAnimeDao().getNextPage()
                ?: return MediatorResult.Success(false)
            else -> {
                return MediatorResult.Success(true)
            }
        }
        val dbPage = db.topAnimeDao().getPagination(filter, page)
        if (dbPage != null) {
            return MediatorResult.Success(
                !dbPage.hasNextPage
            )
        }
        if (loadType == LoadType.REFRESH) {
            db.topAnimeDao().clear()
            _mainState.emit(MainVM.MainState.Loading)
        }
        return repo.getTopAnime(page, filter).fold(
            onSuccess = { response ->
                val endOfPaginationReached = !response.pagination.hasNextPage
                response.pagination.filterType = filter
                db.topAnimeDao().insertResponse(response)
                _mainState.emit(MainVM.MainState.Success)
                MediatorResult.Success(endOfPaginationReached)
            },
            onFailure = {
                _mainState.emit(MainVM.MainState.Fail)
                MediatorResult.Error(it)
            }
        )
    }
}