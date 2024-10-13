package com.ru.hazakura.domain.repository
import com.ru.hazakura.domain.model.LinkForGetDubbing
import com.ru.hazakura.domain.model.SearchResponce
import com.ru.hazakura.util.Result

interface KodikRepository {
    suspend fun getLinkDubbingInfo(id: String) : Result<LinkForGetDubbing>
    suspend fun getSearch(title: String): Result<SearchResponce>
}