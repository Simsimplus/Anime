package io.simsim.anime.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.TopAnimeResponse
import io.simsim.anime.network.repo.JikanRepo
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class TopAnimeRemoteMediator @Inject constructor(
    private val repo: JikanRepo,
    private val db: AnimeDataBase
) : RemoteMediator<Int, TopAnimeResponse.TopAnimeData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, TopAnimeResponse.TopAnimeData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
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
                MediatorResult.Success(endOfPaginationReached)
            },
            onFailure = {
                MediatorResult.Error(it)
            }
        )
    }
}