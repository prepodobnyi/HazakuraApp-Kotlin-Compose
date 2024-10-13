package com.ru.hazakura.ui.viewModelScreen

import android.content.Intent
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.Checker
import com.ru.hazakura.util.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthScreenViewModel @Inject constructor(
    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository
): ViewModel() {

    private val _isChecked = MutableStateFlow<Checker>(Checker.Not)
    val isChecked: StateFlow<Checker> = _isChecked

    fun handleIntent(authCode: String?) {
        viewModelScope.launch {

            if (authCode != null) {
                _isChecked.value = Checker.Checking
                Log.i("Auth", "получен код:$authCode")

                when(val result = repository.getAccessAndRefreashToken(authCode)) {

                    is Result.Success -> {
                        val tokens = result.data
                        storeManager.saveTokens(tokens)
                        Log.i("TryGetTokens","получили токен!!!")
                        _isChecked.value = Checker.Ok
                    }

                    is Result.Error -> {
                        Log.e("TryGetTokenError", result.message)
                        _isChecked.value = Checker.Not
                        }
                    }
                }
            }
        }
    }
