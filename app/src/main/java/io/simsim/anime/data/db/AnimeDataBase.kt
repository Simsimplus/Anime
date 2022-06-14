package io.simsim.anime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.simsim.anime.data.converters.ImagesConverter
import io.simsim.anime.data.converters.ProducerListConverter
import io.simsim.anime.data.converters.StringListConverter
import io.simsim.anime.data.dao.TopAnimeDao
import io.simsim.anime.data.entity.TopAnime

@Database(
    entities = [
        TopAnime.TopAnimeData::class
    ],
    version = 1
)
@TypeConverters(
    ImagesConverter::class,
    ProducerListConverter::class,
    StringListConverter::class
)
abstract class AnimeDataBase : RoomDatabase() {
    abstract fun topAnimeDao(): TopAnimeDao
}