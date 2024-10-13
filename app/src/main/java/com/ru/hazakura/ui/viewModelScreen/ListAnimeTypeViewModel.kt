package com.ru.hazakura.ui.viewModelScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.Result
import com.ru.hazakura.util.homeOngoingGraphQuery
import com.ru.hazakura.util.homePopularGraphQuery
import com.ru.hazakura.util.homeRandomGraphQuery
import com.ru.hazakura.util.homeSeasonAnimeGraphQuery
import com.ru.hazakura.util.studioGraphQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListAnimeTypeViewModel@Inject constructor(
    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository
) : ViewModel() {

    private var _animeTypeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeTypeList = _animeTypeList.asStateFlow()

    private var _currentPage = MutableStateFlow(1)
    val currentPage = _currentPage.asStateFlow()

    private var _needMore = MutableStateFlow(true)
    val needMore = _needMore.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _emptyListAnime = MutableStateFlow(false)
    val emptyListAnime = _emptyListAnime.asStateFlow()



    fun fetchListAnimeType(type: String, page: Int) {
        viewModelScope.launch {
            if (_isLoading.value) return@launch // Prevent multiple simultaneous loads

            _isLoading.value = true

            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                val query = when(type){
                    "ongoing" ->{
                        homeOngoingGraphQuery(page)
                    }
                    "popular" ->{
                        homePopularGraphQuery(page)
                    }
                    "season_anime" ->{
                        homeSeasonAnimeGraphQuery(page)
                    }
                    "random"  ->{
                        homeRandomGraphQuery(page)
                    }
                    else ->{
                        studioGraphQuery(page, type)
                    }
                }
                when(val result = repository.getAnimeTypeList(accessToken.toString(),query)) {
                    is Result.Success -> {
                        val data = result.data
                        _animeTypeList.update { currentList -> currentList + data.animes }
                        if(data.animes.size < 50){
                            _needMore.value = false
                        }
                        _currentPage.value = page + 1
                        if(_animeTypeList.value.isEmpty()) {
                            _emptyListAnime.value = true
                        }
                    }
                    is Result.Error -> {
                        val data = result.message
                        if(data == "401"){
                            if (refreshToken != null) {
                                refreshToken(refreshToken)
                            }
                        }
                    }

                }
                _isLoading.value = false
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
        }
    }
}