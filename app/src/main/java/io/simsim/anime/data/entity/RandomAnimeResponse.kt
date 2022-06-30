package io.simsim.anime.data.entity


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class RandomAnimeResponse(
    @Json(name = "data") var `data`: RandomAnimeData = RandomAnimeData()
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class RandomAnimeData(
        @Json(name = "aired") var aired: Aired = Aired(),
        @Json(name = "airing") var airing: Boolean = false,
        @Json(name = "background") var background: String = "",
        @Json(name = "broadcast") var broadcast: Broadcast = Broadcast(),
        @Json(name = "demographics") var demographics: List<Demographic> = listOf(),
        @Json(name = "duration") var duration: String = "",
        @Json(name = "episodes") var episodes: Int = 0,
        @Json(name = "explicit_genres") var explicitGenres: List<Any> = listOf(),
        @Json(name = "favorites") var favorites: Int = 0,
        @Json(name = "genres") var genres: List<Genre> = listOf(),
        @Json(name = "images") var images: Images = Images(),
        @Json(name = "licensors") var licensors: List<Any> = listOf(),
        @Json(name = "mal_id") var malId: Int = 0,
        @Json(name = "members") var members: Int = 0,
        @Json(name = "popularity") var popularity: Int = 0,
        @Json(name = "producers") var producers: List<Any> = listOf(),
        @Json(name = "rank") var rank: Int = 0,
        @Json(name = "rating") var rating: String = "",
        @Json(name = "score") var score: Any = Any(),
        @Json(name = "scored_by") var scoredBy: Any = Any(),
        @Json(name = "season") var season: Any = Any(),
        @Json(name = "source") var source: String = "",
        @Json(name = "status") var status: String = "",
        @Json(name = "studios") var studios: List<Any> = listOf(),
        @Json(name = "synopsis") var synopsis: String = "",
        @Json(name = "themes") var themes: List<Theme> = listOf(),
        @Json(name = "title") var title: String = "",
        @Json(name = "title_english") var titleEnglish: Any = Any(),
        @Json(name = "title_japanese") var titleJapanese: String = "",
        @Json(name = "title_synonyms") var titleSynonyms: List<Any> = listOf(),
        @Json(name = "trailer") var trailer: Trailer = Trailer(),
        @Json(name = "type") var type: String = "",
        @Json(name = "url") var url: String = "",
        @Json(name = "year") var year: Any = Any()
    )
}