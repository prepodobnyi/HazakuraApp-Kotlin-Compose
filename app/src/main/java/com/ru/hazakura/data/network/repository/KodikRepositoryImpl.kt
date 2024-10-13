package com.ru.hazakura.data.network.repository

import com.ru.hazakura.data.mapper.ToLinkForGetDubbing
import com.ru.hazakura.data.mapper.toSearchResponce
import com.ru.hazakura.data.network.KodikApi
import com.ru.hazakura.domain.model.LinkForGetDubbing
import com.ru.hazakura.domain.model.SearchResponce
import com.ru.hazakura.domain.repository.KodikRepository
import com.ru.hazakura.util.Result
import javax.inject.Inject

class KodikRepositoryImpl @Inject constructor (
    val api: KodikApi
): KodikRepository {

    override suspend fun getLinkDubbingInfo(id: String): Result<LinkForGetDubbing> {
        val url = "https://kodikdb.com/find-player?shikimoriID=$id"
        return try {
            val response =
                api.getLinkDubbingInfo(
                    url = url,
                    token = "447d179e875efe44217f20d1ee2146be",
                    shikimoriID = id
                )
            if (response.isSuccessful) {
                val dubbingLink = response.body()
                if (dubbingLink != null) {
                    Result.Success(dubbingLink.ToLinkForGetDubbing())
                } else {
                    Result.Error("Link not found")
                }
            } else {
                Result.Error("${response.code()}")
            }
        } catch (e: Exception) {
            Result.Error("Exception: ${e.message}")
        }

    }

    override suspend fun getSearch(title: String): Result<SearchResponce> {
        return try {
            val response = api.getSearch(title = title)
            if (response.isSuccessful) {
                val nonNullUniqueResults = response.body()
                if(nonNullUniqueResults != null){
                    Result.Success(nonNullUniqueResults.toSearchResponce())
                }else{
                    Result.Error("нет не нулевых элементов")
                }
            } else {
                Result.Error("Ошибка запроса: ${response.code()}")
                    }

        } catch (e: Exception) {
            // Отлавливаем все исключения и возвращаем их как ошибку
            Result.Error(e.toString())
        }
    }
}