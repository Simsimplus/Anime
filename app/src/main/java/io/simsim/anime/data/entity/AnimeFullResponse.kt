package io.simsim.anime.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeFullResponse(
    @Json(name = "data") var animeFullData: AnimeFullData = AnimeFullData()
) {
    @Entity
    @Keep
    @JsonClass(generateAdapter = true)
    data class AnimeFullData(
        @Json(name = "aired") var aired: Aired = Aired(),
        @Json(name = "airing") var airing: Boolean = false,
        @Json(name = "background") var background: String = "",
        @Json(name = "broadcast") var broadcast: Broadcast = Broadcast(),
        @Json(name = "demographics") var demographics: List<Demographic> = listOf(),
        @Json(name = "duration") var duration: String = "",
        @Json(name = "episodes") var episodes: Int = 0,
        @Json(name = "explicit_genres") var explicitGenres: List<ExplicitGenre> = listOf(),
        @Json(name = "external") var `external`: List<External> = listOf(),
        @Json(name = "favorites") var favorites: Int = 0,
        @Json(name = "genres") var genres: List<Genre> = listOf(),
        @Json(name = "images") var images: Images = Images(),
        @Json(name = "licensors") var licensors: List<Licensor> = listOf(),
        @PrimaryKey @Json(name = "mal_id") var malId: Int = 0,
        @Json(name = "members") var members: Int = 0,
        @Json(name = "popularity") var popularity: Int = 0,
        @Json(name = "producers") var producers: List<Producer> = listOf(),
        @Json(name = "rank") var rank: Int = 0,
        @Json(name = "rating") var rating: String = "",
        @Json(name = "relations") var relations: List<Relation> = listOf(),
        @Json(name = "score") var score: Double = 0.0,
        @Json(name = "scored_by") var scoredBy: Int = 0,
        @Json(name = "season") var season: String = "",
        @Json(name = "source") var source: String = "",
        @Json(name = "status") var status: String = "",
        @Json(name = "studios") var studios: List<Studio> = listOf(),
        @Json(name = "synopsis") var synopsis: String = "",
        @Json(name = "theme") var theme: OPED = OPED(),
        @Json(name = "themes") var themes: List<Theme> = listOf(),
        @Json(name = "title") var title: String = "",
        @Json(name = "title_english") var titleEnglish: String = "",
        @Json(name = "title_japanese") var titleJapanese: String = "",
        @Json(name = "title_synonyms") var titleSynonyms: List<String> = listOf(),
        @Json(name = "trailer") var trailer: Trailer = Trailer(),
        @Json(name = "type") var type: String = "",
        @Json(name = "url") var url: String = "",
        @Json(name = "year") var year: Int = 0,
        @Json(ignore = true) var statistic: AnimeStatisticsResponse.AnimeStatisticsData = AnimeStatisticsResponse.AnimeStatisticsData()
    )
}
