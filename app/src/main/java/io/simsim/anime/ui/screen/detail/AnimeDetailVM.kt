package io.simsim.anime.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.db.AnimeDataBase
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AnimeDetailVM @Inject constructor(
    private val db: AnimeDataBase,
    private val repo: JikanRepo
) : ViewModel() {
    init {
        Timber.e(this.toString())
    }

    private val malIdFlow = MutableSharedFlow<Int>(
        replay = 1
    )

    fun setMalId(malId: Int) = viewModelScope.launch {
        malIdFlow.emit(malId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    val animeFullDataFlow = malIdFlow.flatMapLatest { malId ->
        db.topAnimeDao().getAnimeFullDataFlow(malId).map { dbData ->
            dbData ?: repo.getAnimeFullById(malId)
                .onSuccess {
                    db.topAnimeDao().insertAnimeFullData(it)
                    getAnimeStatistic(it)
                }
                .getOrDefault(AnimeFullResponse.AnimeFullData())
        }
    }

    fun getAnimeDetailFullFlow(malId: Int) =
        db.topAnimeDao().getAnimeFullDataFlow(malId).map { dbData ->
            dbData ?: repo.getAnimeFullById(malId)
                .onSuccess {
                    db.topAnimeDao().insertAnimeFullData(it)
                    getAnimeStatistic(it)
                }
                .getOrDefault(AnimeFullResponse.AnimeFullData())
        }


    private suspend fun getAnimeStatistic(
        animeFullData: AnimeFullResponse.AnimeFullData
    ) = viewModelScope.launch {
        repo.getAnimeStatistic(animeFullData.malId).onSuccess {
            db.topAnimeDao().updateAnimeFullData(
                animeFullData.copy(
                    statistic = it.animeStatisticsData
                )
            )
        }
    }
}
