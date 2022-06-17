package io.simsim.anime.data.entity

sealed class AnimeType(val type: String) {
    object TV : AnimeType("tv")
    object MOVIE : AnimeType("movie")
    object OVA : AnimeType("ova")
    object SPECIAL : AnimeType("special")
    object ONA : AnimeType("ona")
    object MUSIC : AnimeType("music")
}
