package com.ru.hazakura.domain.repository

import com.ru.hazakura.domain.model.Translations
import com.ru.hazakura.util.Result
import org.json.JSONObject
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Url

interface KodikStringRepository {
    suspend fun getTranslate(dubbingLink: String): Result<Pair<JSONObject, List<Translations>>>

    suspend fun getAnimeLink(mediaId: String,mediaHash: String,episode: String, urlParams: JSONObject): Result<String?>

}