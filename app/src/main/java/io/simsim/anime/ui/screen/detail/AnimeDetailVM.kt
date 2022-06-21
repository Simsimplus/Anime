package io.simsim.anime.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.data.entity.AnimeStatisticsResponse
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeDetailVM @Inject constructor(
    private val repo: JikanRepo
) : ViewModel() {
    private val _animeDetailFull = MutableSharedFlow<AnimeFullResponse.AnimeFullData>()
    val animeDetailFull: SharedFlow<AnimeFullResponse.AnimeFullData>
        get() = _animeDetailFull

    private val _animeStatistics = MutableSharedFlow<AnimeStatisticsResponse.AnimeStatisticsData>()
    val animeStatistics: SharedFlow<AnimeStatisticsResponse.AnimeStatisticsData>
        get() = _animeStatistics

    suspend fun getAnimeDetailFull(malId: Int) = viewModelScope.launch {
        repo.getAnimeFullById(malId).onSuccess {
            _animeDetailFull.emit(it.animeFullData)
        }
    }

    suspend fun getAnimeStatistic(malId: Int) = viewModelScope.launch {
        repo.getAnimeStatistic(malId).onSuccess {
            _animeStatistics.emit(it.animeStatisticsData)
        }
    }
}
