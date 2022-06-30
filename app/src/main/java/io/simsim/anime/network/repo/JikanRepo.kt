package io.simsim.anime.network.repo

import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.entity.TopAnimeFilterType
import io.simsim.anime.network.api.JikanService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

class JikanRepo(
    private val service: JikanService
) : CoroutineScope by MainScope() {
    fun getAnimeFullByIdFlow(malId: Int) = safeRequestAsFlow {
        service.getAnimeFullById(malId)
    }

    suspend fun getAnimeFullById(malId: Int) = safeRequest {
        service.getAnimeFullById(malId)
    }.mapCatching { it.animeFullData }

    suspend fun getAnimeStatistic(malId: Int) = safeRequest {
        service.getAnimeStatistics(malId)
    }

    suspend fun getAnimeImages(malId: Int) = safeRequest {
        service.getAnimePictures(malId)
    }

    fun getRandomAnimeAsState() = safeRequestAsStateFlow {
        service.getRandomAnime()
    }

    suspend fun getTopAnime(page: Int = 1, filterType: TopAnimeFilterType) = safeRequest {
        val filterString = if (filterType == TopAnimeFilterType.Score) null else filterType.query
        service.getTopAnime(page, filterString)
    }

    suspend fun searchAnime(query: String, type: AnimeType, page: Int) = safeRequest {
        service.getAnimeSearch(query, type.type, page)
    }

    private suspend fun <R> safeRequest(block: suspend () -> Response<R>): Result<R> =
        withContext(Dispatchers.IO) {
            runCatching {
                block.invoke().body()!!
            }
        }.onFailure {
            Timber.e(it)
        }

    private fun <R> safeRequestAsFlow(block: suspend () -> Response<R>) = flow {
        emit(block.invoke().body()!!)
    }.catch {
        Timber.e(it)
    }

    private fun <R> safeRequestAsStateFlow(block: suspend () -> Response<R>) = flow {
        emit(
            runCatching {
                block.invoke().body()!!
            }.fold(
                onSuccess = {
                    RequestState.Success(it)
                },
                onFailure = {
                    RequestState.Failure(it)
                }
            )
        )
    }.stateIn(this, SharingStarted.Lazily, RequestState.Started)
}


sealed class RequestState {
    object Started : RequestState()
    data class Success<Data>(val data: Data) : RequestState()
    data class Failure(val throwable: Throwable) : RequestState()
}
