package io.simsim.anime.ui.screen.detail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class AnimeDetailVM @Inject constructor(
    private val repo: JikanRepo
) : ViewModel() {
    fun getAnimeDetailFull(malId: Int) = repo.getAnimeFullByIdFlow(malId).map { it.animeFullData }
}