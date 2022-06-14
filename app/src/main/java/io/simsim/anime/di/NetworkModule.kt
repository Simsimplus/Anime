package io.simsim.anime.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.simsim.anime.network.api.JikanService
import io.simsim.anime.network.repo.JikanRepo
import io.simsim.island.utils.moshi.moshi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttpClient() = OkHttpClient.Builder().build()

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