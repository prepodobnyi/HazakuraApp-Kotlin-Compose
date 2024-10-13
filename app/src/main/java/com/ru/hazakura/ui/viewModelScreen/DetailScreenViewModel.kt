package com.ru.hazakura.ui.viewModelScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.AnimeDetail
import com.ru.hazakura.domain.model.Comments
import com.ru.hazakura.domain.model.DataAnime
import com.ru.hazakura.domain.repository.AnimeRepository
import com.ru.hazakura.domain.repository.KodikRepository
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.Result
import com.ru.hazakura.util.animeDetailGraphQuery
import com.ru.hazakura.util.homeScreenGraphQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailScreenViewModel@Inject constructor(
    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository,
    private val animeDB: AnimeRepository,
    private val kodikRepository: KodikRepository
) :ViewModel() {

    private var _animeDB = MutableStateFlow<AnimeEntity?>(null)
    val animeinDb = _animeDB.asStateFlow()

    private var _animeDetail = MutableStateFlow<AnimeDetail?>(null)
    val animeDetail = _animeDetail.asStateFlow()

    private var _dubbingLink = MutableStateFlow<String?>(null)
    val dubbingLink = _dubbingLink.asStateFlow()

    private var _isLoading = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private var _animeSimilar = MutableStateFlow<List<Anime>>(emptyList())
    val animeSimilar = _animeSimilar.asStateFlow()

    private var _comments = MutableStateFlow<List<Comments>>(emptyList())
    val comments = _comments.asStateFlow()

    private fun fetchDubbingLink(id: String){
        viewModelScope.launch {
            when(val result = kodikRepository.getLinkDubbingInfo(id = id)){
                is Result.Success ->{
                    val data = result.data.link
                    _dubbingLink.update { data }
                }

                is Result.Error -> {
                    val data = result.message
                    Log.e("DubbinkLink",data)
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
    private fun fetchSimilarAnime(id: String){
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                when(val result = repository.getSimilarAnime(accessToken.toString(),id)) {
                    is Result.Success -> {
                        val data = result.data
                        _animeSimilar.update { data }
                    }
                    is Result.Error -> {
                        val data = result.message
                        if(data == "401"){
                            if (refreshToken != null) {
                                fetchSimilarAnime(id)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun fetchComments(id: String){
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                when(val result = repository.getComments(accessToken.toString(),id)) {
                    is Result.Success -> {
                        val data = result.data
                        _comments.update { data }
                    }
                    is Result.Error -> {
                        val data = result.message
                        println("ошибка комментариев $data")
                        if(data == "401"){
                            if (refreshToken != null) {
                                fetchComments(id)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }
    private fun fetchAnimeDb(id: String){
        viewModelScope.launch {
            val animeInDb = animeDB.getAnimeById(id)
            if (animeInDb != null){
                _animeDB.update { animeInDb }
            }
        }
    }

    fun addAnimeInDB(animeEntity: AnimeEntity){
        viewModelScope.launch {
            animeDB.upsertAnime(animeEntity)
            fetchAnimeDb(animeEntity.id)
        }
    }

    fun fetchAnime(id: String) {
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                val query = animeDetailGraphQuery(id)
                when(val result = repository.getAnimeDetail(accessToken.toString(),query)) {
                    is Result.Success -> {
                        val data = result.data
                        _animeDetail.update { data }
                        fetchAnimeDb(id)
                        fetchSimilarAnime(id)
                        fetchDubbingLink(id)
                        fetchComments(data.topic)


                    }
                    is Result.Error -> {
                        val data = result.message
                        println("ОШибка тут $data")
                        if(data == "401"){
                            if (refreshToken != null) {
                                refreshToken(refreshToken)
                                fetchAnime(id)
                            }
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private suspend fun refreshToken(refreshToken: String) {
        when (val result = repository.getRefreshToken(refreshToken)) {
            is Result.Success -> {
                val token = result.data
                storeManager.saveTokens(token)
            }
            is Result.Error -> {
                Log.i("RefreshToken", result.message)
            }

            else -> {}
        }
    }
}