package com.ru.hazakura.data.network.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ru.hazakura.data.network.KodikStringApi
import com.ru.hazakura.domain.model.Translations
import com.ru.hazakura.domain.repository.KodikJsonRepository
import com.ru.hazakura.domain.repository.KodikStringRepository
import com.ru.hazakura.util.Result
import com.ru.hazakura.util.convert
import com.ru.hazakura.util.decodeBase64
import org.json.JSONObject
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import java.util.Base64
import javax.inject.Inject

class KodikStringRepositoryImpl@Inject constructor (
    val api: KodikStringApi,
    private val kodikJsonRepository: KodikJsonRepository
): KodikStringRepository {
    override suspend fun getTranslate(dubbingLink: String): Result<Pair<JSONObject, List<Translations>>> {
        val response = api.getTranslate("https:$dubbingLink")
        return try {
            if (response.isSuccessful) {
                val responseData = response.body()
                if (responseData != null) {
                    // Находим подстроку 'urlParams' и извлекаем JSON данные
                    val startIndex = responseData.indexOf("urlParams") + 13
                    val endIndex = responseData.indexOf(";", startIndex) - 1
                    val urlParamsJson = responseData.substring(startIndex, endIndex)

                    // Преобразуем JSON данные в объект
                    val urlParams = JSONObject(urlParamsJson)

                    // Используем Jsoup для парсинга HTML
                    val soup = Jsoup.parse(responseData)
                    // Обработка данных переводов
                    val translations = mutableListOf<Translations>()

                    try {

                        val translationNumSeria = soup.select(".serial-translations-box option")
                        println(translationNumSeria)
                        if (translationNumSeria.isEmpty()) {
                            throw Exception("Элемент .serial-translations-box option не найден")
                        }
                        for (translation in translationNumSeria) {
                            translations.add(
                                Translations(
                                    translationTitle = translation.attr("data-title"),
                                    dataMediaId = translation.attr("data-media-id"),
                                    dataMediaHash = translation.attr("data-media-hash"),
                                    dataEpisodeCount = extractNumber(translation.attr("data-episode-count"))
                                )
                            )
                        }
                    } catch (e: Exception) {
                        try {

                            val translation = soup.selectFirst(".serial-seasons-box option")
                            if (translation == null) {
                                throw Exception("Элемент .serial-seasons-box option не найден")
                            }
                            val script = soup.selectFirst("script")
                            val start = script?.data()?.indexOf("translationTitle = \"")
                                ?.plus("translationTitle = \"".length) ?: 0
                            val end = script?.data()?.indexOf("\";", start) ?: 0
                            val translationTitle = script?.data()?.substring(start, end) ?: ""
                            val seraiNum = soup.select(".serial-series-box option").last()
                            translations.add(
                                Translations(
                                    translationTitle = translationTitle,
                                    dataMediaId = translation.attr("data-serial-id") ?: "",
                                    dataMediaHash = translation.attr("data-serial-hash") ?: "",
                                    dataEpisodeCount = extractNumber(seraiNum?.attr("value") ?: "")
                                )
                            )
                        } catch (e: Exception) {
                            val translationNumSeria = soup.select(".movie-translations-box option")
                            if (translationNumSeria.isEmpty()) {
                                throw Exception("Элемент .movie-translations-box  option не найден")
                            }
                            for (translation in translationNumSeria) {
                                translations.add(
                                    Translations(
                                        translationTitle = translation.attr("data-title"),
                                        dataMediaId = translation.attr("data-media-id"),
                                        dataMediaHash = translation.attr("data-media-hash"),
                                        dataEpisodeCount = "1"
                                    )
                                )
                            }
                        }
                    }
                    Result.Success(urlParams to translations)
                } else {
                    Result.Error("${response.code()}")
                }
            } else {
                Result.Error("Exception: ${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.message}")
        }
    }

    override suspend fun getAnimeLink(
        mediaId: String,
        mediaHash: String,
        episode: String,
        urlParams: JSONObject
    ): Result<String?> {
        return try {
            val responseSerial =
                api.getSerial(mediaId = mediaId, mediaHash = mediaHash, episode = episode)
            if (responseSerial.isSuccessful) {
                // Если запрос успешен, преобразуем ответ в список объектов Anime
                val data = responseSerial.body()
                if (data != null) {
                    Result.Success(processHtml(data, urlParams))
                } else {
                    Result.Error("пустой ответ от сервера на получение ссылки")
                }


            } else {
                val responseVideo =
                    api.getVideo(mediaId = mediaId, mediaHash = mediaHash, episode = episode)
                if (responseVideo.isSuccessful) {
                    val data = responseVideo.body()
                    if (data != null) {
                        Result.Success(processHtml(data, urlParams))
                    } else {
                        Result.Error("пустой ответ от сервера на получение ссылки")
                    }

                } else {
                    Result.Error("ошибка в запросе на получение ссылки аниме")
                }
            }
        } catch (e: Exception) {
            // Отлавливаем все исключения и возвращаем их как ошибку
            Result.Error("${e.message}")
        }
    }


    suspend fun processHtml(html: String, urlParams: JSONObject): String? {
        val document: Document = Jsoup.parse(html)
        val scriptUrl = document.select("script")[1].attr("src")
        val hashContainer = document.select("script")[4].html()

        val videoType = hashContainer.substringAfter(".type = '").substringBefore("'")
        val videoHash = hashContainer.substringAfter(".hash = '").substringBefore("'")
        val videoId = hashContainer.substringAfter(".id = '").substringBefore("'")
        val downloadUrl =
            getDownloadLinkWithData(videoType, videoHash, videoId, urlParams, scriptUrl)
        if (downloadUrl != null) {
            if (downloadUrl.isNotEmpty()) {
                val processedUrl =
                    downloadUrl.replace("https:", "").substring(2, downloadUrl.length - 18)
                return "https://$processedUrl"
            } else {
                return null
            }
        } else {
            return null
        }

    }


    suspend fun getDownloadLinkWithData(
        videoType: String,
        videoHash: String,
        videoId: String,
        urlParams: JSONObject,
        scriptUrl: String
    ): String? {
        val params = mapOf(
            "hash" to videoHash,
            "id" to videoId,
            "type" to videoType,
            "d" to urlParams.getString("d"),
            "d_sign" to urlParams.getString("d_sign"),
            "pd" to urlParams.getString("pd"),
            "pd_sign" to urlParams.getString("pd_sign"),
            "ref" to "",
            "ref_sign" to urlParams.getString("ref_sign"),
            "bad_user" to "true",
            "cdn_is_working" to "true"
        )
        val postLink = getPostLink(scriptUrl)
        val response = kodikJsonRepository.postData(postLink, params)
        if (response.isSuccessful) {
            val responseBody = response.body()

            val gson = Gson()
            val type = object : TypeToken<Map<String, Any>>() {}.type
            val data: Map<String, Any> = gson.fromJson(gson.toJson(responseBody), type)
            val links = data["links"] as Map<String, Any>
            val quality720 = links["720"] as List<Map<String, Any>>
            val src = quality720[0]["src"] as String
            return decodeBase64(convert(src))
        } else {
            return null
        }
    }

    suspend fun getPostLink(scriptUrl: String): String {

        val response = api.getScriptContent(scriptUrl)
        if (response.isSuccessful) {
            val data = response.body() ?: return ""

            val startIndex = data.indexOf("$.ajax") + 30
            val endIndex = data.indexOf("cache:!1") - 3
            val url = data.substring(startIndex, endIndex)
            return String(Base64.getDecoder().decode(url))
        } else {
            return ""
        }
    }
}

fun extractNumber(str: String): String {
    val regex = "-?(\\d+)$".toRegex()
    val matchResult = regex.find(str)

    val res = matchResult?.groupValues?.get(1)
    if(res != null){
        return res
    }else{return "0"}
}