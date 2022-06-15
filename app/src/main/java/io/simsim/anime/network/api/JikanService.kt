package io.simsim.anime.network.api

import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.data.entity.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanService {

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): Response<TopAnimeResponse>

    @GET("anime/{mal_id}/full")
    suspend fun getAnimeFullById(@Path("mal_id") mailId: Int): Response<AnimeFullResponse>
}
