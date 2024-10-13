package com.ru.hazakura.domain.repository

import com.ru.hazakura.data.network.dto.MainScreenAnimeListDto
import com.ru.hazakura.domain.model.AccessAndRefreshToken
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.AnimeDetail
import com.ru.hazakura.domain.model.CalendarAnimeItem
import com.ru.hazakura.domain.model.Comments
import com.ru.hazakura.domain.model.DataAnime
import com.ru.hazakura.domain.model.DataListAnime
import com.ru.hazakura.domain.model.User
import com.ru.hazakura.util.GraphQLRequest
import com.ru.hazakura.util.Result as MyResult

interface ShikiRepository {
    suspend fun getAccessAndRefreashToken(code: String) :MyResult<AccessAndRefreshToken>

    suspend fun getRefreshToken(refreshToken: String) : MyResult<AccessAndRefreshToken>

    suspend fun getCurrentUser(authorization: String): MyResult<User>

    suspend fun getHomeAnimeList(authorization: String, graphQLRequest: GraphQLRequest): MyResult<DataAnime>

    suspend fun getAnimeTypeList(authorization: String, graphQLRequest: GraphQLRequest): MyResult<DataListAnime>

    suspend fun getAnimeDetail(authorization: String, graphQLRequest: GraphQLRequest): MyResult<AnimeDetail>

    suspend fun getSimilarAnime(authorization: String, id: String): MyResult<List<Anime>>

    suspend fun getComments(authorization: String, commentableId: String): MyResult<List<Comments>>

    suspend fun getSearchAnimeList(authorization: String, graphQLRequest: GraphQLRequest): MyResult<DataListAnime>

    suspend fun getCalendarList(authorization: String): MyResult<List<CalendarAnimeItem>>
}