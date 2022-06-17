package io.simsim.anime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.simsim.anime.network.api.JikanService
import io.simsim.anime.network.repo.JikanRepo
import io.simsim.anime.utils.gradle.isDebugMode
import io.simsim.anime.utils.moshi.moshi
import okhttp3.OkHttpClient
import okhttp3.internal.tls.OkHostnameVerifier
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient() =
        OkHttpClient.Builder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .callTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
//            .connectionPool(ConnectionPool(0, 1, TimeUnit.NANOSECONDS))
//            .protocols(listOf(Protocol.HTTP_1_1))
            .hostnameVerifier { host, sslSection ->
                if (isDebugMode) {
                    true
                } else {
                    OkHostnameVerifier.verify(host, sslSection)
                }
            }
            .addInterceptor { chain ->
                val request = chain.request()
                Timber.i(request.url.toString())
                chain.proceed(request)
            }
            .build()

    @Singleton
    @Provides
    fun providesApiService(
        okHttpClient: OkHttpClient
    ) = Retrofit.Builder()
        .baseUrl("https://api.jikan.moe/v4/")
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build().create<JikanService>()

    @Singleton
    @Provides
    fun provideApiRepo(
        jikanService: JikanService
    ) = JikanRepo(
        jikanService
    )
}
