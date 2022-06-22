package io.simsim.anime.ui.screen.images

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.simsim.anime.data.entity.Images
import io.simsim.anime.network.repo.JikanRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ImageVm @Inject constructor(
    val repo: JikanRepo
) : ViewModel() {
    private val _animeImages = MutableStateFlow(listOf(Images()))
    val animeImages = _animeImages.asStateFlow()

    suspend fun getAnimeImages(malId: Int) = viewModelScope.launch {
        repo.getAnimeImages(malId).onSuccess {
            _animeImages.emit(it.images)
        }
    }
}