package io.simsim.anime.data.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.simsim.anime.data.entity.*
import io.simsim.anime.utils.moshi.parseJsonNullable
import io.simsim.anime.utils.moshi.toJson

abstract class Converter<T : Any>(
    private val clazz: Class<T>
) {
    @TypeConverter
    fun type2String(value: T): String {
        return value.toJson(clazz)
    }

    @TypeConverter
    fun string2Type(string: String): T {
        return string.parseJsonNullable(clazz)!!
    }
}

abstract class ListConverter<T : Any>(
    clazz: Class<T>
) {
    val adapter: JsonAdapter<List<T>> = Moshi.Builder().build().adapter(
        Types.newParameterizedType(
            List::class.java, clazz
        )
    )

    @TypeConverter
    fun list2String(value: List<T>): String {
        return adapter.toJson(value)
    }

    @TypeConverter
    fun string2List(string: String): List<T> {
        return adapter.fromJson(string)!!
    }
}

inline fun <reified T : Any> getTypeConverter(): Converter<T> =
    object : Converter<T>(T::class.java) {}

//fun Any.generateTypeConverters():List<Any>{
//    if (!this::class.isData) return emptyList()
//    this::class.typeParameters.forEach {
//        it::class.
//    }
//    return emptyList()
//}

val converters = setOf(
    ImagesConverter,
    AiredConverter,
    BroadcastConverter,
    OPEDConverter,
    TrailerConverter,
    AnimeStatisticsDataConverter,
    DemographicsListConverter,
    ExplicitGenresListConverter,
    ExternalListConverter,
    GenreListConverter,
    LicensorListConverter,
    RelationListConverter,
    StudioListConverter,
    StudioListConverter,
    ThemeListConverter,
    ProducerListConverter,
    TopAnimePageInfoConverter,
    StringListConverter,
    SearchAnimePageInfoConverter,
    AnimeTypeConverters
)

object ImagesConverter :
    Converter<Images>(Images::class.java)

object AiredConverter : Converter<Aired>(Aired::class.java)


object BroadcastConverter : Converter<Broadcast>(Broadcast::class.java)
object OPEDConverter : Converter<OPED>(OPED::class.java)
object TrailerConverter : Converter<Trailer>(Trailer::class.java)
object AnimeStatisticsDataConverter : Converter<AnimeStatisticsResponse.AnimeStatisticsData>(
    AnimeStatisticsResponse.AnimeStatisticsData::class.java
)


object DemographicsListConverter : ListConverter<Demographic>(Demographic::class.java)
object ExplicitGenresListConverter : ListConverter<ExplicitGenre>(ExplicitGenre::class.java)
object ExternalListConverter : ListConverter<External>(External::class.java)
object GenreListConverter : ListConverter<Genre>(Genre::class.java)
object LicensorListConverter : ListConverter<Licensor>(Licensor::class.java)
object RelationListConverter : ListConverter<Relation>(Relation::class.java)
object StudioListConverter : ListConverter<Studio>(Studio::class.java)
object ThemeListConverter : ListConverter<Theme>(Theme::class.java)
object ProducerListConverter : ListConverter<Producer>(Producer::class.java)

object TopAnimePageInfoConverter :
    Converter<TopAnimeResponse.TopAnimePagination.TopAnimePageInfo>(TopAnimeResponse.TopAnimePagination.TopAnimePageInfo::class.java)


object StringListConverter : ListConverter<String>(String::class.java)

object SearchAnimePageInfoConverter :
    Converter<SearchAnimeResponse.SearchAnimePagination.SearchAnimePageInfo>(SearchAnimeResponse.SearchAnimePagination.SearchAnimePageInfo::class.java)

object AnimeTypeConverters {
    @TypeConverter
    fun type2String(value: AnimeType): String {
        return value.type
    }

    @TypeConverter
    fun string2Type(string: String): AnimeType {
        return when (string) {
            AnimeType.MOVIE.type -> {
                AnimeType.MOVIE
            }
            AnimeType.MUSIC.type -> {
                AnimeType.MUSIC
            }
            AnimeType.ONA.type -> {
                AnimeType.ONA
            }
            AnimeType.OVA.type -> {
                AnimeType.OVA
            }
            AnimeType.SPECIAL.type -> {
                AnimeType.SPECIAL
            }
            AnimeType.TV.type -> {
                AnimeType.TV
            }
            else -> {
                AnimeType.TV
            }
        }
    }
}


