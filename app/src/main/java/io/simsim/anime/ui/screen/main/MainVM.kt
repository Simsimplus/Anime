package io.simsim.anime.ui.screen.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MainVM @Inject constructor(
    repo: JikanRepo
) : ViewModel() {
    val recommendations = repo.getTopAnime().map {
        it.data
    }
//    init {
//        viewModelScope.launch {
//            repo.getRecentAnimeRecommendations(1).getOrNull()?.let {
//                recommendations.emit(it.data)
//            }
//        }
//    }
}
