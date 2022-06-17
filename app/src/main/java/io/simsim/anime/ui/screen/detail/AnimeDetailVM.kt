package io.simsim.anime.ui.screen.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.entity.AnimeFullResponse
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

@HiltViewModel
class AnimeDetailVM @Inject constructor(
    private val repo: JikanRepo
) : ViewModel() {
    private val _animeDetailFull = MutableSharedFlow<AnimeFullResponse.AnimeFullData>()
    val animeDetailFull: SharedFlow<AnimeFullResponse.AnimeFullData>
        get() = _animeDetailFull

    suspend fun getAnimeDetailFull(malId: Int) {
        repo.getAnimeFullById(malId).onSuccess {
            _animeDetailFull.emit(it.animeFullData)
        }
    }
}