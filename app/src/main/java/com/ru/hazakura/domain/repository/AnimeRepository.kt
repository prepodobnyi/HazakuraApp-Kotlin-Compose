package com.ru.hazakura.domain.repository

import com.ru.hazakura.data.database.anime.AnimeDao
import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.data.database.anime.WatchedEpisodeEntity
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.WatchedEpisode

interface AnimeRepository {

    suspend fun upsertAnime(animeEntity: AnimeEntity)

    suspend fun getAnimeById(animeId: String): AnimeEntity?

    suspend fun getAnimeWatch(): List<Anime>
    suspend fun getAnimeViewed(): List<Anime>
    suspend fun getAnimePostponed(): List<Anime>
    suspend fun getAnimeAbandoned(): List<Anime>
    suspend fun getAnimePlans(): List<Anime>

    suspend fun getAnimeByName(title: String): List<AnimeEntity>

    suspend fun getAnimeList(): List<Anime>

    suspend fun getWatchedEpisodesByAnimeId(animeId: String): List<WatchedEpisode>

    suspend fun addWatchedEpisode(watchedEpisode: WatchedEpisodeEntity)

    suspend fun deleteAnime(id: String)
}