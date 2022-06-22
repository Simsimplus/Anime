package io.simsim.anime.data.entity

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class TopAnimeResponse(
    @Json(name = "data") var `data`: List<TopAnimeData> = listOf(),
    @Json(name = "pagination") var pagination: TopAnimePagination = TopAnimePagination()
) {
    @Entity
    @Keep
    @JsonClass(generateAdapter = true)
    data class TopAnimeData(
        @PrimaryKey(autoGenerate = true) var dbId: Long = 0L,
        @Ignore @Json(name = "aired") var aired: Aired = Aired(),
        @Ignore @Json(name = "airing") var airing: Boolean = false,
        @Ignore @Json(name = "background") var background: String = "",
        @Ignore @Json(name = "broadcast") var broadcast: Broadcast = Broadcast(),
        @Ignore @Json(name = "demographics") var demographics: List<Demographic> = listOf(),
        @Json(name = "duration") var duration: String = "",
        @Json(name = "episodes") var episodes: Int = 0,
        @Ignore @Json(name = "explicit_genres") var explicitGenres: List<ExplicitGenre> = listOf(),
        @Json(name = "favorites") var favorites: Int = 0,
        @Ignore @Json(name = "genres") var genres: List<Genre> = listOf(),
        @Json(name = "images") var images: Images = Images(),
        @Ignore @Json(name = "licensors") var licensors: List<Licensor> = listOf(),
        @Json(name = "mal_id") var malId: Int = 0,
        @Json(name = "members") var members: Int = 0,
        @Json(name = "popularity") var popularity: Int = 0,
        @Json(name = "producers") var producers: List<Producer> = listOf(),
        @Json(name = "rank") var rank: Int = 0,
        @Json(name = "rating") var rating: String = "",
        @Json(name = "score") var score: Float = 0f,
        @Json(name = "scored_by") var scoredBy: Int = 0,
        @Json(name = "season") var season: String = "",
        @Json(name = "source") var source: String = "",
        @Json(name = "status") var status: String = "",
        @Ignore @Json(name = "studios") var studios: List<Studio> = listOf(),
        @Json(name = "synopsis") var synopsis: String = "",
        @Ignore @Json(name = "themes") var themes: List<Theme> = listOf(),
        @Json(name = "title") var title: String = "",
        @Json(name = "title_english") var titleEnglish: String = "",
        @Json(name = "title_japanese") var titleJapanese: String = "",
        @Json(name = "title_synonyms") var titleSynonyms: List<String> = listOf(),
        @Ignore @Json(name = "trailer") var trailer: Trailer = Trailer(),
        @Json(name = "type") var type: String = "",
        @Json(name = "url") var url: String = "",
        @Json(name = "year") var year: Int = 0
    )

    @Entity(
        primaryKeys = ["currentPage", "filterType"]
    )
    @Keep
    @JsonClass(generateAdapter = true)
    data class TopAnimePagination(
        @Json(name = "current_page") var currentPage: Int = 1,
        @Json(name = "has_next_page") var hasNextPage: Boolean = false,
        @Json(name = "items") var items: TopAnimePageInfo = TopAnimePageInfo(),
        @Json(name = "last_visible_page") var lastVisiblePage: Int = 0,
        @Json(ignore = true) var filterType: TopAnimeFilterType = TopAnimeFilterType.Score
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class TopAnimePageInfo(
            @Json(name = "count") var count: Int = 0,
            @Json(name = "per_page") var perPage: Int = 0,
            @Json(name = "total") var total: Int = 0
        )
    }
}
