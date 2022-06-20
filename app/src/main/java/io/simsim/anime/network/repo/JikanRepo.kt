package io.simsim.anime.network.repo

import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.network.api.JikanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

class JikanRepo(
    private val service: JikanService
) {
    fun getAnimeFullByIdFlow(malId: Int) = safeRequestAsFlow {
        service.getAnimeFullById(malId)
    }

    suspend fun getAnimeFullById(malId: Int) = safeRequest {
        service.getAnimeFullById(malId)
    }

    suspend fun getAnimeStatistic(malId: Int) = safeRequest {
        service.getAnimeStatistics(malId)
    }

    suspend fun getTopAnime(page: Int = 1) = safeRequest {
        service.getTopAnime(page)
    }

    suspend fun searchAnime(query: String, type: AnimeType, page: Int) = safeRequest {
        service.getAnimeSearch(query, type.type, page)
    }
}

suspend fun <R> safeRequest(block: suspend () -> Response<R>): Result<R> =
    withContext(Dispatchers.IO) {
        runCatching {
            block.invoke().body()!!
        }
    }.onFailure {
        Timber.e(it)
    }

fun <R> safeRequestAsFlow(block: suspend () -> Response<R>) = flow {
    emit(block.invoke().body()!!)
}.catch {
    Timber.e(it)
}
