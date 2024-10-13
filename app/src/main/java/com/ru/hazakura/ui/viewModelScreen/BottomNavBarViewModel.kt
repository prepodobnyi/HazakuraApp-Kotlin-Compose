package com.ru.hazakura.ui.viewModelScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BottomNavBarViewModel @Inject constructor():ViewModel() {
    private val _isShowBottomBar = MutableStateFlow(true)
    val isShowBottomBar = _isShowBottomBar.asStateFlow()

    fun changeStateVisabilityBottomBar(){
        _isShowBottomBar.value = !_isShowBottomBar.value
    }
}