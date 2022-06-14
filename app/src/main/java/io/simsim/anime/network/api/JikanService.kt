package io.simsim.anime.network.api

import io.simsim.anime.data.entity.RecentAnimeRecommendation
import io.simsim.anime.data.entity.TopAnime
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface JikanService {
    @GET("recommendations/anime")
    suspend fun getRecentAnimeRecommendations(@Query("page") page: Int): Response<RecentAnimeRecommendation>

    @GET("top/anime")
    suspend fun getTopAnime(@Query("page") page: Int): Response<TopAnime>
}
