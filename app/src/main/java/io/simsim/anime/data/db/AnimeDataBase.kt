package io.simsim.anime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.simsim.anime.data.converters.*
import io.simsim.anime.data.dao.SearchDao
import io.simsim.anime.data.dao.TopAnimeDao
import io.simsim.anime.data.entity.SearchAnimeResponse
import io.simsim.anime.data.entity.TopAnimeResponse

@Database(
    entities = [
        TopAnimeResponse.TopAnimeData::class,
        TopAnimeResponse.TopAnimePagination::class,
        SearchAnimeResponse.SearchAnimeData::class,
        SearchAnimeResponse.SearchAnimePagination::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImagesConverter::class,
    ProducerListConverter::class,
    StringListConverter::class,
    TopAnimePageInfoConverter::class,
    SearchAnimePageInfoConverter::class,
    AnimeTypeConverters::class
)
abstract class AnimeDataBase : RoomDatabase() {
    abstract fun topAnimeDao(): TopAnimeDao
    abstract fun searchDao(): SearchDao
}