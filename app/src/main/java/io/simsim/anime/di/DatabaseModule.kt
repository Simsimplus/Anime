package io.simsim.anime.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
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
    ) = Room.databaseBuilder(app, AnimeDataBase::class.java, "anime_db")
        .fallbackToDestructiveMigration()
        .addCallback(
            object : RoomDatabase.Callback() {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.execSQL(
                        "delete from TopAnimeData"
                    )
                }
            }
        )
        .build()
}