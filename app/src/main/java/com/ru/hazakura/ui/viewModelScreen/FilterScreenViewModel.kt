package com.ru.hazakura.ui.viewModelScreen

import androidx.lifecycle.ViewModel
import com.ru.hazakura.data.datastore.ProtoDataStoreManager
import com.ru.hazakura.domain.repository.ShikiRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FilterScreenViewModel @Inject constructor(
    private val storeManager: ProtoDataStoreManager,
    private val repository: ShikiRepository
): ViewModel() {

}