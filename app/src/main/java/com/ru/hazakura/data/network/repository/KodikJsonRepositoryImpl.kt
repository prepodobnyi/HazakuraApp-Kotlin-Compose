package com.ru.hazakura.data.network.repository


import com.ru.hazakura.data.network.KodikJsonApi
import com.ru.hazakura.domain.repository.KodikJsonRepository
import retrofit2.Response

import javax.inject.Inject

class KodikJsonRepositoryImpl@Inject constructor (
    val api: KodikJsonApi
): KodikJsonRepository {
    override suspend fun postData(url: String, params: Map<String, String>): Response<Map<String, Any>> {
        return api.postData(url,params)

    }
}