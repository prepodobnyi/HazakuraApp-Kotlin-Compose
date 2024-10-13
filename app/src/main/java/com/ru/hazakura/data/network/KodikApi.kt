package com.ru.hazakura.data.network

import com.ru.hazakura.data.network.dto.LinkForGetDubbingDto
import com.ru.hazakura.data.network.dto.SearchResponceDto
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface KodikApi {

    @GET("get-player")
    suspend fun getLinkDubbingInfo(
        @Query("title") title: String = "Player",
        @Query("hasPlayer") hasPlayer: Boolean = false,
        @Query("url") url: String,
        @Query("token") token: String,
        @Query("shikimoriID") shikimoriID: String
    ): Response<LinkForGetDubbingDto>

    @FormUrlEncoded
    @POST("search")
    suspend fun getSearch(
        @Field("token") token: String = "447d179e875efe44217f20d1ee2146be",
        @Field("title") title: String) : Response<SearchResponceDto>


    companion object{
        const val baseUrl = "https://kodikapi.com/"
    }
}