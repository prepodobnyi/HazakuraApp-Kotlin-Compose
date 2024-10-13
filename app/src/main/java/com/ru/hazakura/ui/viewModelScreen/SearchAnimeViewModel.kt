package com.ru.hazakura.ui.viewModelScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.SearchResponce
import com.ru.hazakura.domain.repository.KodikRepository
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.Result
import com.ru.hazakura.util.SearchAnimeGraphQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchAnimeViewModel@Inject constructor(
    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository,
    private val kodikrepository: KodikRepository
): ViewModel(){

    private var _animeList = MutableStateFlow<List<Anime>>(emptyList())
    val animeList = _animeList.asStateFlow()

    private var _shikiIdList = MutableStateFlow<SearchResponce?>(null)
    val shikiIdList = _shikiIdList.asStateFlow()

    private var _emptyListAnime = MutableStateFlow(false)
    val emptyListAnime = _emptyListAnime.asStateFlow()

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()


    fun searchAnime(title: String){
        viewModelScope.launch {
            if (_isLoading.value) return@launch // Prevent multiple simultaneous loads
            _isLoading.value = true
            _emptyListAnime.value = false
                when (val result = kodikrepository.getSearch(title)) {
                    is Result.Success -> {
                        val data = result.data
                        _shikiIdList.update { data }
                        if (_shikiIdList.value?.shikimori_ids?.isEmpty() == true) {
                            _emptyListAnime.value = true
                        }else{
                            searchAnimeItems()
                        }
                    }

                    is Result.Error -> {
                        _emptyListAnime.value = true
                }
            }
            _isLoading.value = false
        }
    }

    fun searchAnimeItems(){
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                val ids = _shikiIdList.value?.shikimori_ids?.joinToString(separator = ", ")?:""
                when (val result = repository.getSearchAnimeList(accessToken.toString(), SearchAnimeGraphQuery(ids))) {
                    is Result.Success -> {
                        val data = result.data
                        _animeList.update { data.animes }
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