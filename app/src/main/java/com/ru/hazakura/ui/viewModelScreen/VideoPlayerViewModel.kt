package com.ru.hazakura.ui.viewModelScreen

import android.content.Context
import android.net.Uri
import androidx.annotation.OptIn
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import com.ru.hazakura.data.database.anime.WatchedEpisodeEntity
import com.ru.hazakura.domain.model.VideoItem
import com.ru.hazakura.domain.repository.AnimeRepository
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val animeDB: AnimeRepository,
    val player: Player
):ViewModel() {

    private val animeUrls = savedStateHandle.getStateFlow("animeUri", emptyList<String>())
    var isFullScreen = mutableStateOf(false)

    private val _needSave = savedStateHandle.getStateFlow("isFlagTrue", false)
    val needSave: MutableStateFlow<Boolean> = MutableStateFlow(_needSave.value)



    val videoItems = animeUrls.map { urls ->
        urls.map { url ->
            VideoItem(
                contentUri = url,
                mediaItem = MediaItem.fromUri(url),
                name = "No name"
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        player.prepare()
        // Подписываемся на изменения и сохраняем значение 'a' при изменении
        viewModelScope.launch {
            needSave.collect { value ->
                savedStateHandle["isFlagTrue"] = value
            }
        }
    }

    fun addVideoUri(url: String){
        savedStateHandle["videoUris"] = animeUrls.value + url
        player.addMediaItem(MediaItem.fromUri(url))
    }

    fun playVideo(url: String){
        player.setMediaItem(
            videoItems.value.find { it.contentUri == url }?.mediaItem?: return
        )
    }

    fun setFlagTrue(value: Boolean) {
        needSave.value = value
    }

    fun addEpisodeToWatched(watchedEpisode: WatchedEpisodeEntity){
        viewModelScope.launch {
            animeDB.addWatchedEpisode(watchedEpisode)
        }
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }
}

