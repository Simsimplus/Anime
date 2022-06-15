package io.simsim.anime.data.converters

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import io.simsim.anime.data.entity.TopAnimeResponse
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

object ImagesConverter :
    Converter<TopAnimeResponse.TopAnimeData.Images>(TopAnimeResponse.TopAnimeData.Images::class.java)

object TopAnimePageInfoConverter :
    Converter<TopAnimeResponse.TopAnimePagination.TopAnimePageInfo>(TopAnimeResponse.TopAnimePagination.TopAnimePageInfo::class.java)

object ProducerListConverter :
    ListConverter<TopAnimeResponse.TopAnimeData.Producer>(TopAnimeResponse.TopAnimeData.Producer::class.java)

object StringListConverter : ListConverter<String>(String::class.java)
