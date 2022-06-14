package io.simsim.anime.utils.moshi

import com.squareup.moshi.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

fun <T> JsonAdapter<T>.fromJsonNullable(json: String) = kotlin.runCatching {
    this.fromJson(json)
}.getOrNull()

fun <T> JsonAdapter<T>.toJsonOrEmpty(t: T): String = kotlin.runCatching {
    this.toJson(t)
}.getOrDefault("")

val moshi: Moshi by lazy {
    Moshi.Builder().add(DefaultIfNullFactory)
        .build()
}

object DefaultIfNullFactory : JsonAdapter.Factory {
    override fun create(
        type: Type,
        annotations: MutableSet<out Annotation>,
        moshi: Moshi
    ): JsonAdapter<*>? {
        val delegate = moshi.nextAdapter<Any>(this, type, annotations)
        return object : JsonAdapter<Any>() {
            override fun fromJson(reader: JsonReader): Any? {
                val blob = reader.readJsonValue()
                if (blob is Map<*, *>) {
                    val noNulls = blob.filterValues { it != null }
                    return delegate.fromJsonValue(noNulls)
                }
                return delegate.fromJsonValue(blob)
            }

            override fun toJson(writer: JsonWriter, value: Any?) {
                return delegate.toJson(writer, value)
            }
        }
    }
}

inline fun <reified T> T.toJson(): String = moshi.adapter(T::class.java).toJsonOrEmpty(this)
fun <T> T.toJson(clazz: Class<T>): String = moshi.adapter(clazz).toJsonOrEmpty(this)

inline fun <reified T> String.parseJsonNullable(): T? =
    moshi.adapter(T::class.java).fromJsonNullable(this)

fun <T> String.parseJsonNullable(clazz: Class<T>): T? =
    moshi.adapter(clazz).fromJsonNullable(this)

inline fun <reified T> List<T>.toJson(): String {
    val type: ParameterizedType = Types.newParameterizedType(
        List::class.java, T::class.java
    )
    val adapter: JsonAdapter<List<T>> = Moshi.Builder().build().adapter(
        type
    )
    return adapter.toJsonOrEmpty(this)
}

inline fun <reified T> String.parseJsonList(): List<T>? {
    val type: ParameterizedType = Types.newParameterizedType(
        List::class.java, T::class.java
    )
    val adapter: JsonAdapter<List<T>> = Moshi.Builder().build().adapter(
        type
    )
    return adapter.fromJsonNullable(this)
}
