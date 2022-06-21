package io.simsim.anime.data.entity

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

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
