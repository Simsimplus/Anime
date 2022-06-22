package io.simsim.anime.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.data.entity.TopAnimeFilterType
import io.simsim.anime.data.entity.TopAnimeResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface TopAnimeDao {
    @Query("select * from TopAnimeData")
    fun getAll(): Flow<List<TopAnimeResponse.TopAnimeData>>

    @Query("select * from TopAnimeData")
    fun getAllPS(): PagingSource<Int, TopAnimeResponse.TopAnimeData>

    @Query("delete from TopAnimeData")
    suspend fun clearData()

    @Transaction
    @Insert
    suspend fun insertAll(
        datum: List<TopAnimeResponse.TopAnimeData>
    )

    // pagination
    @Insert
    suspend fun insertPagination(
        pagination: TopAnimeResponse.TopAnimePagination
    )

    @Query(
        "select (currentPage + 1) from TopAnimePagination order by currentPage desc limit 1 "
    )
    suspend fun getNextPage(): Int?

    @Query(
        "select * from TopAnimePagination where filterType = :filterType and currentPage = :page"
    )
    suspend fun getPagination(
        filterType: TopAnimeFilterType,
        page: Int
    ): TopAnimeResponse.TopAnimePagination?

    @Query("delete from TopAnimePagination")
    suspend fun clearPagination()

    @Transaction
    suspend fun insertResponse(
        response: TopAnimeResponse
    ) {
        insertAll(response.data)
        insertPagination(response.pagination)
    }

    @Transaction
    suspend fun clear() {
        clearData()
        clearPagination()
    }

    // full
    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAnimeFullData(animeFullData: AnimeFullResponse.AnimeFullData)

    @Update
    suspend fun updateAnimeFullData(animeFullData: AnimeFullResponse.AnimeFullData)

    @Query(
        "select * from AnimeFullData where malId = :malId"
    )
    fun getAnimeFullDataFlow(malId: Int): Flow<AnimeFullResponse.AnimeFullData?>
}
