package com.ru.hazakura.data.network

import retrofit2.Response
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface KodikJsonApi {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST
    suspend fun postData(@Url url: String, @FieldMap params: Map<String, String>): Response<Map<String, Any>>

    companion object{
        const val baseUrl = "https://kodik.info"
    }
}