package io.simsim.anime.network.api

import io.simsim.anime.data.entity.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface JikanService {

    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int,
        @Query("filter") filter: String? = null
    ): Response<TopAnimeResponse>

    @GET("anime/{mal_id}/full")
    suspend fun getAnimeFullById(@Path("mal_id") mailId: Int): Response<AnimeFullResponse>

    @GET("anime/{mal_id}/statistics")
    suspend fun getAnimeStatistics(@Path("mal_id") mailId: Int): Response<AnimeStatisticsResponse>

    @GET("anime/{mal_id}/pictures")
    suspend fun getAnimePictures(@Path("mal_id") mailId: Int): Response<AnimeImagesResponse>

    @GET("anime")
    suspend fun getAnimeSearch(
        @Query("q") query: String,
        @Query("type") type: String,
        @Query("page") page: Int
    ): Response<SearchAnimeResponse>
}
