package io.simsim.anime.network.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class RecentAnimeRecommendation(
    @Json(name = "data") var `data`: List<Data>,
    @Json(name = "pagination") var pagination: Pagination
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Data(
        @Json(name = "content") var content: String,
        @Json(name = "entry") var entry: List<Entry>,
        @Json(name = "mal_id") var malId: String,
        @Json(name = "user") var user: User
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        data class Entry(
            @Json(name = "images") var images: Images,
            @Json(name = "mal_id") var malId: Int,
            @Json(name = "title") var title: String,
            @Json(name = "url") var url: String
        ) {
            @Keep
            @JsonClass(generateAdapter = true)
            data class Images(
                @Json(name = "jpg") var jpg: Jpg,
                @Json(name = "webp") var webp: Webp
            ) {
                @Keep
                @JsonClass(generateAdapter = true)
                data class Jpg(
                    @Json(name = "image_url") var imageUrl: String,
                    @Json(name = "large_image_url") var largeImageUrl: String,
                    @Json(name = "small_image_url") var smallImageUrl: String
                )

                @Keep
                @JsonClass(generateAdapter = true)
                data class Webp(
                    @Json(name = "image_url") var imageUrl: String,
                    @Json(name = "large_image_url") var largeImageUrl: String,
                    @Json(name = "small_image_url") var smallImageUrl: String
                )
            }
        }

        @Keep
        @JsonClass(generateAdapter = true)
        data class User(
            @Json(name = "url") var url: String,
            @Json(name = "username") var username: String
        )
    }

    @Keep
    @JsonClass(generateAdapter = true)
    data class Pagination(
        @Json(name = "has_next_page") var hasNextPage: Boolean,
        @Json(name = "last_visible_page") var lastVisiblePage: Int
    )
}
