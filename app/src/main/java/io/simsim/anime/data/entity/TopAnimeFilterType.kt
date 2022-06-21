package io.simsim.anime.data.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TopAnimeFilterType(val query: String) : Parcelable {
    Score(""), Airing("airing"), Upcoming("upcoming"), Popularity("bypopularity"), Favorite("favorite"),
}
