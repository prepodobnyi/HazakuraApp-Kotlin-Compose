package com.ru.hazakura.data.network

import com.ru.hazakura.data.network.dto.AccessAndRefreshTokenDto
import com.ru.hazakura.data.network.dto.AnimeDetailDataDto
import com.ru.hazakura.data.network.dto.AnimeSimilarDto
import com.ru.hazakura.data.network.dto.CalendarAnimeItemDto
import com.ru.hazakura.data.network.dto.CommentsDto
import com.ru.hazakura.data.network.dto.MainAnimeListDto
import com.ru.hazakura.data.network.dto.MainScreenAnimeListDto
import com.ru.hazakura.data.network.dto.UserDto
import com.ru.hazakura.util.GraphQLRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ShikiApi {

    //Получение токенов доступа
    @Headers("User-Agent: Hazakura")
    @FormUrlEncoded
    @POST("/oauth/token")
    suspend fun getTokens(
        @Field("grant_type") grantType: String = "authorization_code",
        @Field("client_id") clientId: String = "VSZHMtEs2tm0-zYahEH4k0o-pwOlIMD3OIcu1XxzAFQ",
        @Field("client_secret") clientSecret: String = "NNVK0RDT946YXUfaInoZuA9_o7Nm-fiqj5DfiZBo2BE",
        @Field("code") code: String,
        @Field("redirect_uri") redirectUri: String = "hazakura://auth"
    ): Response<AccessAndRefreshTokenDto>

    //Обновить токен доступа
    @Headers("User-Agent: Hazakura")
    @FormUrlEncoded
    @POST("oauth/token")
    suspend fun getRefreshToken(
        @Field("grant_type") grantType: String = "refresh_token",
        @Field("client_id") clientId: String = "VSZHMtEs2tm0-zYahEH4k0o-pwOlIMD3OIcu1XxzAFQ",
        @Field("client_secret") clientSecret: String = "NNVK0RDT946YXUfaInoZuA9_o7Nm-fiqj5DfiZBo2BE",
        @Field("refresh_token") refreshToken: String
    ): Response<AccessAndRefreshTokenDto>


    //Получение данных пользователя
    @Headers("User-Agent: Hazakura")
    @GET("api/users/whoami")
    suspend fun getCurrentUser(
        @Header("Authorization") authorization: String
    ): Response<UserDto>

    @Headers(
        "User-Agent: Hazakura",
        "Content-Type: application/json",
        "Accept-Encoding: gzip, deflate, br",
        "Accept: application/json; charset=UTF-8",
        "Connection: keep-alive",
        "DNT: 1"
    )
    @POST("api/graphql")
    suspend fun getHomeListAnimes(
        @Header("Authorization") authorization: String,
        @Body requestBody: GraphQLRequest
    ): Response<MainScreenAnimeListDto>

    @Headers(
        "User-Agent: Hazakura",
        "Content-Type: application/json",
        "Accept-Encoding: gzip, deflate, br",
        "Accept: application/json; charset=UTF-8",
        "Connection: keep-alive",
        "DNT: 1"
    )
    @POST("api/graphql")
    suspend fun getListTypeAnimes(
        @Header("Authorization") authorization: String,
        @Body requestBody: GraphQLRequest
    ): Response<MainAnimeListDto>


    @Headers(
        "User-Agent: Hazakura",
        "Content-Type: application/json",
        "Accept-Encoding: gzip, deflate, br",
        "Accept: application/json; charset=UTF-8",
        "Connection: keep-alive",
        "DNT: 1"
    )
    @POST("api/graphql")
    suspend fun getAnimeDetail(
        @Header("Authorization") authorization: String,
        @Body requestBody: GraphQLRequest
    ):Response<AnimeDetailDataDto>

    @Headers(
        "User-Agent: Hazakura",
        "Content-Type: application/json",
        "Accept-Encoding: gzip, deflate, br",
        "Accept: application/json; charset=UTF-8",
        "Connection: keep-alive",
        "DNT: 1"
    )

    @GET("https://shikimori.one/api/animes/{id}/similar")
    suspend fun getSimilarAnime(
        @Header("Authorization") authorization: String,
        @Path("id") id: String
    ): Response<List<AnimeSimilarDto>>

    @Headers(
        "User-Agent: Hazakura",
        "Content-Type: application/json",
        "Accept-Encoding: gzip, deflate, br",
        "Accept: application/json; charset=UTF-8",
        "Connection: keep-alive",
        "DNT: 1"
    )
    @GET("/api/comments")
    suspend fun getComments(
        @Header("Authorization") authorization: String,
        @Query("limit") limit: String = "30",
        @Query("commentable_type") commentableType: String = "Topic",
        @Query("commentable_id") commentableId: String
    ): Response<List<CommentsDto>>

    @POST("/api/graphql")
    suspend fun getSearchAnimes(
        @Header("Authorization") authorization: String,
        @Body requestBody: GraphQLRequest
    ):Response<MainAnimeListDto>


    @GET("/api/calendar")
    suspend fun getCalendar(
        @Header("Authorization") authorization: String,
    ): Response<List<CalendarAnimeItemDto>>

    companion object{
        const val baseUrl = "https://shikimori.one/"
    }
}