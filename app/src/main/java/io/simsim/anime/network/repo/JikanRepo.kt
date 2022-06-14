package io.simsim.anime.network.repo

import io.simsim.anime.network.api.JikanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response
import timber.log.Timber

class JikanRepo(
    private val service: JikanService
) {
    suspend fun getRecentAnimeRecommendations(page: Int) = safeRequest {
        service.getRecentAnimeRecommendations(page)
    }

    fun getRecentAnimeRecommendationsFlow(page: Int) = safeRequestAsFlow {
        service.getRecentAnimeRecommendations(page)
    }
}

suspend fun <R> safeRequest(block: suspend () -> Response<R>): Result<R> =
    withContext(Dispatchers.IO) {
        runCatching {
            block.invoke().body()!!
        }
    }

fun <R> safeRequestAsFlow(block: suspend () -> Response<R>) = flow {
    withContext(Dispatchers.IO) {
        runCatching {
            block.invoke().body()!!
        }
    }.onFailure {
        Timber.e(it)
    }.getOrNull()?.let {
        emit(it)
    }
}