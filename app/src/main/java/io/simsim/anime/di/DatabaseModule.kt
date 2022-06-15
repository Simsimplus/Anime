package io.simsim.anime.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.simsim.anime.data.db.AnimeDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDB(
        app: Application
    ) = Room.inMemoryDatabaseBuilder(app, AnimeDataBase::class.java)
        .fallbackToDestructiveMigration()
        .build()
}