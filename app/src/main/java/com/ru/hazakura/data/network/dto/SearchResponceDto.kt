package com.ru.hazakura.data.network.dto

data class SearchData(
    val data: DataSearch
)
data class DataSearch(
    val animes: List<AnimeDto>
)

data class SearchResponceDto(
    val results: List<ResultDto>
)

data class ResultDto(
    val shikimori_id: String? = null
)