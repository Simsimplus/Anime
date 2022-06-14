package io.simsim.anime.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.simsim.anime.data.entity.TopAnime
import kotlinx.coroutines.flow.Flow

@Dao
interface TopAnimeDao {
    @Query("select * from TopAnimeData order by score desc")
    fun getAll(): Flow<List<TopAnime.TopAnimeData>>

    @Query("select * from TopAnimeData order by score desc")
    fun getAllPS(): PagingSource<Int, TopAnime.TopAnimeData>

    @Query("delete from TopAnimeData")
    suspend fun clear()

    @Insert
    suspend fun insertAll(
        datum: List<TopAnime.TopAnimeData>
    )
}