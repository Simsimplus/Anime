package io.simsim.anime.data.entity


import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class AnimeImagesResponse(
    @Json(name = "data") var images: List<Images> = listOf()
)