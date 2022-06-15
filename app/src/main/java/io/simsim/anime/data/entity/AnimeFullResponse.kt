package io.simsim.anime.data.entity


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeFullResponse(
    @Json(name = "data") var animeFullData: AnimeFullData = AnimeFullData()
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class AnimeFullData(
        @Json(name = "aired") var aired: Aired = Aired(),
        @Json(name = "airing") var airing: Boolean = false,
        @Json(name = "background") var background: String = "",
        @Json(name = "broadcast") var broadcast: Broadcast = Broadcast(),
        @Json(name = "demographics") var demographics: List<Any> = listOf(),
        @Json(name = "duration") var duration: String = "",
        @Json(name = "episodes") var episodes: Int = 0,
        @Json(name = "explicit_genres") var explicitGenres: List<Any> = listOf(),
        @Json(name = "external") var `external`: List<External> = listOf(),
        @Json(name = "favorites") var favorites: Int = 0,
        @Json(name = "genres") var genres: List<Genre> = listOf(),
        @Json(name = "images") var images: Images = Images(),
        @Json(name = "licensors") var licensors: List<Licensor> = listOf(),
        @Json(name = "mal_id") var malId: Int = 0,
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
        @Json(name = "title_synonyms") var titleSynonyms: List<Any> = listOf(),
        @Json(name = "trailer") var trailer: Trailer = Trailer(),
        @Json(name = "type") var type: String = "",
        @Json(name = "url") var url: String = "",
        @Json(name = "year") var year: Int = 0
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Aired(
            @Json(name = "from") var from: String = "",
            @Json(name = "prop") var prop: Prop = Prop(),
            @Json(name = "string") var string: String = "",
            @Json(name = "to") var to: String = ""
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Prop(
                @Json(name = "from") var from: From = From(),
                @Json(name = "to") var to: To = To()
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class From(
                    @Json(name = "day") var day: Int = 0,
                    @Json(name = "month") var month: Int = 0,
                    @Json(name = "year") var year: Int = 0
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class To(
                    @Json(name = "day") var day: Int = 0,
                    @Json(name = "month") var month: Int = 0,
                    @Json(name = "year") var year: Int = 0
                )
            }
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class Broadcast(
            @Json(name = "day") var day: String = "",
            @Json(name = "string") var string: String = "",
            @Json(name = "time") var time: String = "",
            @Json(name = "timezone") var timezone: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class External(
            @Json(name = "name") var name: String = "",
            @Json(name = "url") var url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Genre(
            @Json(name = "mal_id") var malId: Int = 0,
            @Json(name = "name") var name: String = "",
            @Json(name = "type") var type: String = "",
            @Json(name = "url") var url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Images(
            @Json(name = "jpg") var jpg: Jpg = Jpg(),
            @Json(name = "webp") var webp: Webp = Webp()
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Jpg(
                @Json(name = "image_url") var imageUrl: String = "",
                @Json(name = "large_image_url") var largeImageUrl: String = "",
                @Json(name = "small_image_url") var smallImageUrl: String = ""
            )

            @Keep
            @JsonClass(generateAdapter = true)
            data class Webp(
                @Json(name = "image_url") var imageUrl: String = "",
                @Json(name = "large_image_url") var largeImageUrl: String = "",
                @Json(name = "small_image_url") var smallImageUrl: String = ""
            )
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class Licensor(
            @Json(name = "mal_id") var malId: Int = 0,
            @Json(name = "name") var name: String = "",
            @Json(name = "type") var type: String = "",
            @Json(name = "url") var url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Producer(
            @Json(name = "mal_id") var malId: Int = 0,
            @Json(name = "name") var name: String = "",
            @Json(name = "type") var type: String = "",
            @Json(name = "url") var url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Relation(
            @Json(name = "entry") var entry: List<Entry> = listOf(),
            @Json(name = "relation") var relation: String = ""
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Entry(
                @Json(name = "mal_id") var malId: Int = 0,
                @Json(name = "name") var name: String = "",
                @Json(name = "type") var type: String = "",
                @Json(name = "url") var url: String = ""
            )
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class Studio(
            @Json(name = "mal_id") var malId: Int = 0,
            @Json(name = "name") var name: String = "",
            @Json(name = "type") var type: String = "",
            @Json(name = "url") var url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class OPED(
            @Json(name = "endings") var endings: List<String> = listOf(),
            @Json(name = "openings") var openings: List<String> = listOf()
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Theme(
            @Json(name = "mal_id") var malId: Int = 0,
            @Json(name = "name") var name: String = "",
            @Json(name = "type") var type: String = "",
            @Json(name = "url") var url: String = ""
        )

        @Keep
        @JsonClass(generateAdapter = true)
        data class Trailer(
            @Json(name = "embed_url") var embedUrl: String = "",
            @Json(name = "images") var images: Images = Images(),
            @Json(name = "url") var url: String = "",
            @Json(name = "youtube_id") var youtubeId: String = ""
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Images(
                @Json(name = "image_url") var imageUrl: String = "",
                @Json(name = "large_image_url") var largeImageUrl: String = "",
                @Json(name = "maximum_image_url") var maximumImageUrl: String = "",
                @Json(name = "medium_image_url") var mediumImageUrl: String = "",
                @Json(name = "small_image_url") var smallImageUrl: String = ""
            )
        }
    }
}