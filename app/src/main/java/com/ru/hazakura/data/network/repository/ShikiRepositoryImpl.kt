package com.ru.hazakura.data.network.repository

import com.ru.hazakura.data.mapper.toAnime
import com.ru.hazakura.data.mapper.toAnimeDetail
import com.ru.hazakura.data.mapper.toCalendarAnimeItem
import com.ru.hazakura.data.mapper.toComments
import com.ru.hazakura.data.mapper.toDataAnime
import com.ru.hazakura.data.mapper.toDataListAnime
import com.ru.hazakura.data.network.ShikiApi
import com.ru.hazakura.domain.model.AccessAndRefreshToken
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.AnimeDetail
import com.ru.hazakura.domain.model.CalendarAnimeItem
import com.ru.hazakura.domain.model.Comments
import com.ru.hazakura.domain.model.DataAnime
import com.ru.hazakura.domain.model.DataListAnime
import com.ru.hazakura.domain.model.User
import com.ru.hazakura.domain.repository.ShikiRepository
import com.ru.hazakura.util.GraphQLRequest
import com.ru.hazakura.util.Result
import javax.inject.Inject
import com.ru.hazakura.util.Result as MyResult

class ShikiRepositoryImpl @Inject constructor (
    val api: ShikiApi
): ShikiRepository {

    override suspend fun getAccessAndRefreashToken(code: String): MyResult<AccessAndRefreshToken> {
        return try {
            val response = api.getTokens(code = code)
            if (response.isSuccessful) {
                val tokens = response.body()
                if (tokens != null) {
                    MyResult.Success(tokens.toModel())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getRefreshToken(refreshToken: String): MyResult<AccessAndRefreshToken> {
        return try {
            val response = api.getRefreshToken(refreshToken = refreshToken)
            if (response.isSuccessful) {
                val tokens = response.body()
                if (tokens != null) {
                    MyResult.Success(tokens.toModel())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getCurrentUser(authorization: String): MyResult<User> {
        return try {
            val response = api.getCurrentUser(authorization)
            if (response.isSuccessful) {
                val user = response.body()
                if (user != null) {
                    MyResult.Success(user.toModel())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getHomeAnimeList(
        authorization: String,
        graphQLRequest: GraphQLRequest
    ): Result<DataAnime> {
        return try {
            val response =
                api.getHomeListAnimes(authorization = authorization, requestBody = graphQLRequest)
            if (response.isSuccessful) {
                val dataAnime = response.body()?.data
                if (dataAnime != null) {
                    MyResult.Success(dataAnime.toDataAnime())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getAnimeTypeList(
        authorization: String,
        graphQLRequest: GraphQLRequest
    ): Result<DataListAnime> {
        return try {
            val response =
                api.getListTypeAnimes(authorization = authorization, requestBody = graphQLRequest)
            if (response.isSuccessful) {
                val dataAnime = response.body()?.data
                if (dataAnime != null) {
                    MyResult.Success(dataAnime.toDataListAnime())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getAnimeDetail(
        authorization: String,
        graphQLRequest: GraphQLRequest
    ): Result<AnimeDetail> {
        return try {
            val response =
                api.getAnimeDetail(authorization = authorization, requestBody = graphQLRequest)

            if (response.isSuccessful) {
                val dataAnime = response.body()?.data?.animes
                if (dataAnime != null) {
                    MyResult.Success(dataAnime[0].toAnimeDetail())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getSimilarAnime(authorization: String, id: String): Result<List<Anime>> {
        return try {
            val response =
                api.getSimilarAnime(authorization = authorization, id = id)
            if (response.isSuccessful) {
                val dataAnime = response.body()
                if (dataAnime != null) {
                    MyResult.Success(dataAnime.map{it.toAnime()})
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getComments(authorization: String, commentableId: String): Result<List<Comments>> {
        return try {
            val response =
                api.getComments(authorization = authorization, commentableId = commentableId)
            if (response.isSuccessful) {
                val comments = response.body()
                if (comments?.isNotEmpty() == true) {
                    MyResult.Success(comments.map{it.toComments()})
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getSearchAnimeList(
        authorization: String,
        graphQLRequest: GraphQLRequest
    ): Result<DataListAnime> {
        return try {
            val response =
                api.getListTypeAnimes(authorization = authorization, requestBody = graphQLRequest)
            if (response.isSuccessful) {
                val dataAnime = response.body()?.data
                if (dataAnime != null) {
                    MyResult.Success(dataAnime.toDataListAnime())
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getCalendarList(
        authorization: String
    ): Result<List<CalendarAnimeItem>> {
        return try {
            val response =
                api.getCalendar(authorization = authorization)

            if (response.isSuccessful) {
                val dataAnime = response.body()
                if (dataAnime != null) {
                    MyResult.Success(dataAnime.map { it.toCalendarAnimeItem() })
                } else {
                    MyResult.Error("Empty response body")
                }
            } else {
                MyResult.Error("${response.code()}")
            }
        } catch (e: Exception) {
            MyResult.Error("Exception: ${e.message}")
        }
    }
}