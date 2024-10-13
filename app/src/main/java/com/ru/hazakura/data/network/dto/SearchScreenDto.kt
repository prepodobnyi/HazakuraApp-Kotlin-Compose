package com.ru.hazakura.data.network.dto

data class CalendarAnimeItemDto(
    val next_episode: Int?= 0,
    val next_episode_at: String?= null,
    val anime: AnimeSimilarDto
)

