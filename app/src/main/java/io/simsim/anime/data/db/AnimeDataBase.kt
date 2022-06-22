package io.simsim.anime.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.simsim.anime.data.converters.*
import io.simsim.anime.data.dao.SearchDao
import io.simsim.anime.data.dao.TopAnimeDao
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.data.entity.SearchAnimeResponse
import io.simsim.anime.data.entity.TopAnimeResponse

@Database(
    entities = [
        TopAnimeResponse.TopAnimeData::class,
        TopAnimeResponse.TopAnimePagination::class,
        SearchAnimeResponse.SearchAnimeData::class,
        SearchAnimeResponse.SearchAnimePagination::class,
        AnimeFullResponse.AnimeFullData::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    ImagesConverter::class,
    AiredConverter::class,
    BroadcastConverter::class,
    OPEDConverter::class,
    TrailerConverter::class,
    AnimeStatisticsDataConverter::class,
    DemographicsListConverter::class,
    ExplicitGenresListConverter::class,
    ExternalListConverter::class,
    GenreListConverter::class,
    LicensorListConverter::class,
    RelationListConverter::class,
    StudioListConverter::class,
    StudioListConverter::class,
    ThemeListConverter::class,
    ProducerListConverter::class,
    TopAnimePageInfoConverter::class,
    StringListConverter::class,
    SearchAnimePageInfoConverter::class,
    AnimeTypeConverters::class
)
abstract class AnimeDataBase : RoomDatabase() {
    abstract fun topAnimeDao(): TopAnimeDao
    abstract fun searchDao(): SearchDao
}
