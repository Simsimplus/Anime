package io.simsim.anime.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.simsim.anime.data.entity.TopAnimeResponse
import kotlinx.coroutines.flow.Flow

@Dao
interface TopAnimeDao {
    @Query("select * from TopAnimeData order by score desc")
    fun getAll(): Flow<List<TopAnimeResponse.TopAnimeData>>

    @Query("select * from TopAnimeData order by score desc")
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
}