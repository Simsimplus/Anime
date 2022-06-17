package io.simsim.anime.data.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import io.simsim.anime.data.entity.SearchAnimeResponse

@Dao
interface SearchDao {

    @Query(
        "select * from SearchAnimeData"
    )
    fun getAllPS(): PagingSource<Int, SearchAnimeResponse.SearchAnimeData>

    @Insert
    suspend fun insertAll(
        list: List<SearchAnimeResponse.SearchAnimeData>
    )

    @Query(
        "delete from SearchAnimeData"
    )
    suspend fun clearData()

    @Query(
        "delete from SearchAnimePagination"
    )
    suspend fun clearPagination()

    @Transaction
    suspend fun clear() {
        clearData()
        clearPagination()
    }

    // pagination
    @Insert
    suspend fun insertPagination(
        pagination: SearchAnimeResponse.SearchAnimePagination
    )

    @Query(
        "select (currentPage + 1) from SearchAnimePagination order by currentPage desc limit 1 "
    )
    suspend fun getNextPage(): Int?

    @Transaction
    suspend fun insertResponse(
        response: SearchAnimeResponse
    ) {
        insertAll(response.data)
        insertPagination(response.pagination)
    }
}