package io.simsim.anime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.simsim.anime.data.converters.ImagesConverter
import io.simsim.anime.data.converters.ProducerListConverter
import io.simsim.anime.data.converters.StringListConverter
import io.simsim.anime.data.converters.TopAnimePageInfoConverter
import io.simsim.anime.data.dao.TopAnimeDao
import io.simsim.anime.data.entity.TopAnimeResponse

@Database(
    entities = [
        TopAnimeResponse.TopAnimeData::class,
        TopAnimeResponse.TopAnimePagination::class,
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImagesConverter::class,
    ProducerListConverter::class,
    StringListConverter::class,
    TopAnimePageInfoConverter::class
)
abstract class AnimeDataBase : RoomDatabase() {
    abstract fun topAnimeDao(): TopAnimeDao
}