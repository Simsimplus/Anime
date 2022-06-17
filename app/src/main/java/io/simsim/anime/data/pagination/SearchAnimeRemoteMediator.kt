package io.simsim.anime.data.pagination

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.entity.SearchAnimeResponse
import io.simsim.anime.network.repo.JikanRepo

@OptIn(ExperimentalPagingApi::class)
class SearchAnimeRemoteMediator(
    private val repo: JikanRepo,
    private val db: AnimeDataBase,
    private val query: Pair<String, AnimeType>
) : RemoteMediator<Int, SearchAnimeResponse.SearchAnimeData>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchAnimeResponse.SearchAnimeData>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(true)
            LoadType.APPEND -> db.searchDao().getNextPage()
                ?: return MediatorResult.Success(false)
            else -> {
                return MediatorResult.Success(true)
            }
        }
        return repo.searchAnime(query.first, query.second).fold(
            onSuccess = { response ->
                val endOfPaginationReached = !response.pagination.hasNextPage
                db.searchDao().insertResponse(response)
                MediatorResult.Success(endOfPaginationReached)
            },
            onFailure = {
                MediatorResult.Error(it)
            }
        )
    }
}