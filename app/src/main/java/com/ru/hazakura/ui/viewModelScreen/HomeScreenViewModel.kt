package com.ru.hazakura.ui.viewModelScreen


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.model.DataAnime
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.Result
import com.ru.hazakura.util.homeScreenGraphQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel@Inject constructor(

    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository
) : ViewModel() {

    private var _animeData = MutableStateFlow<DataAnime?>(null)
    val animeData = _animeData.asStateFlow()

    init{
        fetchAnime()
    }

    private fun fetchAnime() {
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                val query = homeScreenGraphQuery()
                println(accessToken)
                when(val result = repository.getHomeAnimeList(accessToken.toString(),query)) {
                    is Result.Success -> {
                        val data = result.data
                        _animeData.update { data }
                    }
                    is Result.Error -> {
                    val data = result.message
                        if(data == "401"){
                            if (refreshToken != null) {
                                refreshToken(refreshToken)
                                fetchAnime()
                            }
                        }
                    }

                }
            }
        }
    }
    private fun getUser() {
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                val accessToken = token.accessToken
                val refreshToken = token.refreshToken
                when (val result = repository.getCurrentUser(accessToken.toString())) {
                    is Result.Success -> {
                        val data = result.data
                        println("id пользователя: ${data.id}")
                    }

                    is Result.Error -> {
                        Log.i("RefreshToken", result.message)
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