package com.ru.hazakura.data.network

import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface KodikStringApi {

    @GET
    suspend fun getTranslate(
        @Url url: String
    ): Response<String>

    @GET(value = "serial/{media_id}/{media_hash}/ 720p")
    suspend fun getSerial(
        @Path(value = "media_id") mediaId: String,
        @Path(value = "media_hash") mediaHash: String,
        @Query(value = "min_age") minAge: Int = 21,
        @Query(value = "first_url") firstUrl: Boolean = false,
        @Query(value = "season") season: Int = 1,
        @Query(value = "episode") episode: String
    ): Response<String>

    @GET("video/{media_id}/{media_hash}/720p")
    suspend fun getVideo(
        @Path("media_id") mediaId: String,
        @Path("media_hash") mediaHash: String,
        @Query("min_age") minAge: Int = 21,
        @Query("first_url") firstUrl: Boolean = false,
        @Query("season") season: Int = 1,
        @Query("episode") episode: String
    ): Response<String>

    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST
    suspend fun getDownloadLink(@Url url: String, @Body params: JSONObject): Response<String>

    @GET
    suspend fun getScriptContent(@Url url: String): Response<String>

    companion object{
        const val baseUrl = "https://kodik.info"
    }
}