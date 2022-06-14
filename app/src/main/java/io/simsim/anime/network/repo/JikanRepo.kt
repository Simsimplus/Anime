package io.simsim.anime.network.repo

import io.simsim.anime.network.api.JikanService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
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

    fun getTopAnime() = safeRequestAsFlow {
        service.getTopAnime()
    }
}

suspend fun <R> safeRequest(block: suspend () -> Response<R>): Result<R> =
    withContext(Dispatchers.IO) {
        runCatching {
            block.invoke().body()!!
        }
    }

fun <R> safeRequestAsFlow(block: suspend () -> Response<R>) = flow {
    emit(block.invoke().body()!!)
}.catch {
    Timber.e(it)
}.onEach {
    Timber.d(it.toString())
}
