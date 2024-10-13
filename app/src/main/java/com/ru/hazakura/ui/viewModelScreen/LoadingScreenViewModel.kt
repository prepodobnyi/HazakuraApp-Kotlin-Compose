package com.ru.hazakura.ui.viewModelScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.AccessCodeState
import com.ru.hazakura.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoadingScreenViewModel @Inject constructor(

    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository

): ViewModel() {
    private val _hasAccessCode = MutableStateFlow<AccessCodeState>(AccessCodeState.Checking)
    val hasAccessCode: StateFlow<AccessCodeState> = _hasAccessCode

    init {
        checkAccessCode()
    }

    private fun checkAccessCode() {
        viewModelScope.launch {
            storeManager.getTokens().collect { token ->
                when {
                    token.accessToken == null -> {
                        Log.i("TokenNull","$token")
                        _hasAccessCode.value = AccessCodeState.Empty
                    }
                    else -> {
                        Log.i("TokenAccess","$token")
                        _hasAccessCode.value = AccessCodeState.Access
                    }
                }
            }
        }
    }


}