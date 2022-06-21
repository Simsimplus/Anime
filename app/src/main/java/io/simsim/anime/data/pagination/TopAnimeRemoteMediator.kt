package io.simsim.anime.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.simsim.anime.data.db.AnimeDataBase
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

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopAnimeResponse.TopAnimeData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                _mainState.emit(MainVM.MainState.Loading)
                1
            }
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> db.topAnimeDao().getNextPage()
                ?: return MediatorResult.Success(false)
            else -> {
                return MediatorResult.Success(true)
            }
        }
        return repo.getTopAnime(page).fold(
            onSuccess = { response ->
                val endOfPaginationReached = !response.pagination.hasNextPage
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