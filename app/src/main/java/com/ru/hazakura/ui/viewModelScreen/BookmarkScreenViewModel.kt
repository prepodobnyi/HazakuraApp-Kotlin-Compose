package com.ru.hazakura.ui.viewModelScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.DataAnime
import com.ru.hazakura.domain.repository.AnimeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkScreenViewModel@Inject constructor(
    private val animeDb: AnimeRepository
): ViewModel() {
    val _animeHistoryList = MutableStateFlow<List<Anime>>(emptyList())
    val animeHistoryList = _animeHistoryList.asStateFlow()

    val _animeWatchList = MutableStateFlow<List<Anime>>(emptyList())
    val WatchList = _animeWatchList.asStateFlow()

    val _animeViewedList = MutableStateFlow<List<Anime>>(emptyList())
    val ViewedList = _animeViewedList.asStateFlow()

    val _animePlansList = MutableStateFlow<List<Anime>>(emptyList())
    val PlansList = _animePlansList.asStateFlow()

    val _animeAbadonedList = MutableStateFlow<List<Anime>>(emptyList())
    val AbadonedList = _animeAbadonedList.asStateFlow()

    val _animePostedList = MutableStateFlow<List<Anime>>(emptyList())
    val PostedList = _animePostedList.asStateFlow()


    private var _emptyListAnime = MutableStateFlow(false)
    val emptyListAnime = _emptyListAnime.asStateFlow()

    init{
        fetchHistory()
        fetchWatch()
        fetchViewed()
        fetchPlans()
        fetchPosted()
        fetchAbadoned()
    }

    fun deleteAnime(id: String){
        viewModelScope.launch {
            animeDb.deleteAnime(id)
        }
    }

    private fun fetchHistory(){
        viewModelScope.launch {
            val animeData = animeDb.getAnimeList()
            if (!animeData.isEmpty()){
                _animeHistoryList.update { animeData }
            }else{
                _emptyListAnime.update { true }
            }
        }
    }
    private fun fetchWatch(){
        viewModelScope.launch {
            val animeData = animeDb.getAnimeWatch()
            if (!animeData.isEmpty()){
                _animeWatchList.update { animeData }
            }

        }
    }
    private fun fetchPlans(){
        viewModelScope.launch {
            val animeData = animeDb.getAnimePlans()
            if (!animeData.isEmpty()){
                _animePlansList.update { animeData }
            }

        }
    }
    private fun fetchAbadoned(){
        viewModelScope.launch {
            val animeData = animeDb.getAnimeAbandoned()
            if (!animeData.isEmpty()){
                _animeAbadonedList.update { animeData }
            }

        }
    }
    private fun fetchViewed(){
        viewModelScope.launch {
            val animeData = animeDb.getAnimeViewed()
            if (!animeData.isEmpty()){
                _animeViewedList.update { animeData }
            }

        }
    }
    private fun fetchPosted(){
        viewModelScope.launch {
            val animeData = animeDb.getAnimePostponed()
            if (!animeData.isEmpty()){
                _animePostedList.update { animeData }
            }

        }
    }
}