package io.simsim.anime.network.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class TopAnime(
    @Json(name = "data") val `data`: List<Data> = listOf(),
    @Json(name = "pagination") val pagination: Pagination = Pagination()
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "aired") val aired: Aired = Aired(),
        @Json(name = "airing") val airing: Boolean = false,
        @Json(name = "background") val background: String = "",
        @Json(name = "broadcast") val broadcast: Broadcast = Broadcast(),
        @Json(name = "demographics") val demographics: List<Demographic> = listOf(),
        @Json(name = "duration") val duration: String = "",
        @Json(name = "episodes") val episodes: Int = 0,
        @Json(name = "explicit_genres") val explicitGenres: List<ExplicitGenre> = listOf(),
        @Json(name = "favorites") val favorites: Int = 0,
        @Json(name = "genres") val genres: List<Genre> = listOf(),
        @Json(name = "images") val images: Images = Images(),
        @Json(name = "licensors") val licensors: List<Licensor> = listOf(),
        @Json(name = "mal_id") val malId: Int = 0,
        @Json(name = "members") val members: Int = 0,
        @Json(name = "popularity") val popularity: Int = 0,
        @Json(name = "producers") val producers: List<Producer> = listOf(),
        @Json(name = "rank") val rank: Int = 0,
        @Json(name = "rating") val rating: String = "",
        @Json(name = "score") val score: Float = 0f,
        @Json(name = "scored_by") val scoredBy: Int = 0,
        @Json(name = "season") val season: String = "",
        @Json(name = "source") val source: String = "",
        @Json(name = "status") val status: String = "",
        @Json(name = "studios") val studios: List<Studio> = listOf(),
        @Json(name = "synopsis") val synopsis: String = "",
        @Json(name = "themes") val themes: List<Theme> = listOf(),
        @Json(name = "title") val title: String = "",
        @Json(name = "title_english") val titleEnglish: String = "",
        @Json(name = "title_japanese") val titleJapanese: String = "",
        @Json(name = "title_synonyms") val titleSynonyms: List<String> = listOf(),
        @Json(name = "trailer") val trailer: Trailer = Trailer(),
        @Json(name = "type") val type: String = "",
        @Json(name = "url") val url: String = "",
        @Json(name = "year") val year: Int = 0
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Aired(
            @Json(name = "from") val from: String = "",
            @Json(name = "prop") val prop: Prop = Prop(),
            @Json(name = "to") val to: String = ""
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Prop(
                @Json(name = "from") val from: From = From(),
                @Json(name = "string") val string: String = "",
                @Json(name = "to") val to: To = To()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class From(
                    @Json(name = "day") val day: Int = 0,
                    @Json(name = "month") val month: Int = 0,
                    @Json(name = "year") val year: Int = 0
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class To(
                    @Json(name = "day") val day: Int = 0,
                    @Json(name = "month") val month: Int = 0,
                    @Json(name = "year") val year: Int = 0
                )
            }
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class Broadcast(
            @Json(name = "day") val day: String = "",
            @Json(name = "string") val string: String = "",
            @Json(name = "time") val time: String = "",
            @Json(name = "timezone") val timezone: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Demographic(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class ExplicitGenre(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Images(
            @Json(name = "jpg") val jpg: Jpg = Jpg(),
            @Json(name = "webp") val webp: Webp = Webp()
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Jpg(
                @Json(name = "image_url") val imageUrl: String = "",
                @Json(name = "large_image_url") val largeImageUrl: String = "",
                @Json(name = "small_image_url") val smallImageUrl: String = ""
            )

            @Keep
            @JsonClass(generateAdapter = true)
            data class Webp(
                @Json(name = "image_url") val imageUrl: String = "",
                @Json(name = "large_image_url") val largeImageUrl: String = "",
                @Json(name = "small_image_url") val smallImageUrl: String = ""
            )
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class Licensor(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Producer(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Studio(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Theme(
            @Json(name = "mal_id") val malId: Int = 0,
            @Json(name = "name") val name: String = "",
            @Json(name = "type") val type: String = "",
            @Json(name = "url") val url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Trailer(
            @Json(name = "embed_url") val embedUrl: String = "",
            @Json(name = "url") val url: String = "",
            @Json(name = "youtube_id") val youtubeId: String = ""
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Pagination(
        @Json(name = "has_next_page") val hasNextPage: Boolean = false,
        @Json(name = "items") val items: Items = Items(),
        @Json(name = "last_visible_page") val lastVisiblePage: Int = 0
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Items(
            @Json(name = "count") val count: Int = 0,
            @Json(name = "per_page") val perPage: Int = 0,
            @Json(name = "total") val total: Int = 0
        )
    }
}
