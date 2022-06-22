package io.simsim.anime.data.entity

import android.os.Parcelable
import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize


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
data class Licensor(
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

@Parcelize
@Keep
@JsonClass(generateAdapter = true)
data class Images(
    @Json(name = "jpg") var jpg: Jpg = Jpg(),
    @Json(name = "webp") var webp: Webp = Webp()
) : Parcelable {

    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Jpg(
        @Json(name = "image_url") var imageUrl: String = "",
        @Json(name = "large_image_url") var largeImageUrl: String = "",
        @Json(name = "small_image_url") var smallImageUrl: String = ""
    ) : Parcelable

    @Parcelize
    @Keep
    @JsonClass(generateAdapter = true)
    data class Webp(
        @Json(name = "image_url") var imageUrl: String = "",
        @Json(name = "large_image_url") var largeImageUrl: String = "",
        @Json(name = "small_image_url") var smallImageUrl: String = ""
    ) : Parcelable
}

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

@Keep
@JsonClass(generateAdapter = true)
data class OPED(
    @Json(name = "endings") var endings: List<String> = listOf(),
    @Json(name = "openings") var openings: List<String> = listOf()
)

@Keep
@JsonClass(generateAdapter = true)
data class Demographic(
    @Json(name = "mal_id") var malId: Int = 0,
    @Json(name = "name") var name: String = "",
    @Json(name = "type") var type: String = "",
    @Json(name = "url") var url: String = ""
)

@Keep
@JsonClass(generateAdapter = true)
data class ExplicitGenre(
    @Json(name = "mal_id") var malId: Int = 0,
    @Json(name = "name") var name: String = "",
    @Json(name = "type") var type: String = "",
    @Json(name = "url") var url: String = ""
)