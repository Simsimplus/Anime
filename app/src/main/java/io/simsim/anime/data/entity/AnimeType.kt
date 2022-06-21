package io.simsim.anime.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
enum class AnimeType(val type: String) : Parcelable {
    TV("tv"),
    MOVIE("movie"),
    OVA("ova"),
    SPECIAL("special"),
    ONA("ona"),
    MUSIC("music")
}
