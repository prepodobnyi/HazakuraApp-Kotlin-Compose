package com.ru.hazakura.domain.repository

import retrofit2.Response

interface KodikJsonRepository {
    suspend fun postData (url: String, params: Map<String, String>): Response<Map<String, Any>>
}