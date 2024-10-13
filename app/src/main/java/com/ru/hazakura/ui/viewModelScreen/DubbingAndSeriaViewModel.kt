package com.ru.hazakura.ui.viewModelScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.database.anime.AnimeDataBase
import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.data.database.anime.WatchedEpisodeEntity
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.Translations
import com.ru.hazakura.domain.model.WatchedEpisode
import com.ru.hazakura.domain.repository.AnimeRepository
import com.ru.hazakura.domain.repository.KodikRepository
import com.ru.hazakura.domain.repository.KodikStringRepository
import com.ru.hazakura.util.Result
import com.ru.hazakura.util.animeDetailGraphQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class DubbingAndSeriaViewModel@Inject constructor(
    private val repository: KodikStringRepository,
    private val animeDB: AnimeRepository,
):ViewModel() {
    private var _dubbing = MutableStateFlow<List<Translations>>(emptyList())
    val dubbing = _dubbing.asStateFlow()

    private var _animeLink = MutableStateFlow<String?>(null)
    val animeLink = _animeLink.asStateFlow()

    private var _episode = MutableStateFlow<String?>(null)
    val episode = _episode.asStateFlow()

    private var _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private var _urlParams = MutableStateFlow(JSONObject())
    val urlParams = _urlParams.asStateFlow()


    private var _animeEntity = MutableStateFlow<AnimeEntity?>(null)
    val animeEntity = _animeEntity.asStateFlow()

    private var _episodeWatched = MutableStateFlow<List<WatchedEpisode>>(emptyList())
    val episodeWatched = _episodeWatched.asStateFlow()

    fun fetchEpisodes(id: String){
        viewModelScope.launch {
            val episodes = animeDB.getWatchedEpisodesByAnimeId(id)
            if (episodes.isEmpty() != true){
                _episodeWatched.update { episodes }
            }
        }
    }

    fun fetchAnimeIdInDB(id: String){
        viewModelScope.launch {
            val animeDataEntity = animeDB.getAnimeById(id)
            if (animeDataEntity != null){
                _animeEntity.update { animeDataEntity }
            }
        }
    }

    fun addAnimeInDB(animeEntity: AnimeEntity){
        viewModelScope.launch {
            animeDB.upsertAnime(animeEntity)
        }
    }

    fun animeLinkNull(){
        _animeLink.value = null
    }

    fun fetchAnimeLink(mediaId: String,mediaHash: String,episode: String, urlParams: JSONObject){
        _loading.update { true }
        viewModelScope.launch {
            _episode.update { episode }
            when(val result = repository.getAnimeLink(mediaId, mediaHash, episode, urlParams)) {
                is Result.Success -> {
                    val data = result.data
                    _animeLink.update { data }
                    _loading.update { false }
                }
                is Result.Error -> {
                    val data = result.message
                    Log.e("Anime link",data)
                    _loading.update { false }
                }
                else -> {_loading.update { false }}
            }
        }
    }

    fun fetchDubbing(dubbingLink: String) {
        viewModelScope.launch {
            when(val result = repository.getTranslate(dubbingLink)) {
                is Result.Success -> {
                    val data = result.data
                    _dubbing.update { data.second }
                    _urlParams.update { data.first }
                }
                is Result.Error -> {
                    val data = result.message
                    Log.e("DubbingList",data)
                }

                else -> {}
            }
        }
    }
}
