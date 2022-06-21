package io.simsim.anime.data.dao

import androidx.paging.PagingSource
import androidx.room.*
import io.simsim.anime.data.entity.AnimeType
import io.simsim.anime.data.entity.SearchAnimeResponse

@Dao
interface SearchDao {

    @Query(
        "select * from SearchAnimeData"
    )
    fun getAllPS(): PagingSource<Int, SearchAnimeResponse.SearchAnimeData>

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
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

    @Query(
        "select * from SearchAnimePagination where currentPage = :page and searchQuery = :query and searchType = :type limit 1"
    )
    suspend fun getPagination(
        query: String,
        type: AnimeType,
        page: Int
    ): SearchAnimeResponse.SearchAnimePagination?

    @Transaction
    suspend fun insertResponse(
        response: SearchAnimeResponse
    ) {
        insertAll(response.data)
        insertPagination(response.pagination)
    }
}