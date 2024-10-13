package com.ru.hazakura.data.network.repository

import com.ru.hazakura.data.database.anime.AnimeDataBase
import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.data.database.anime.WatchedEpisodeEntity
import com.ru.hazakura.data.mapper.toAnime
import com.ru.hazakura.data.mapper.toWatchedEpisode
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.WatchedEpisode
import com.ru.hazakura.domain.repository.AnimeRepository
import javax.inject.Inject

class AnimeRepoisitoryImpl @Inject constructor(
    private val animeDataBase: AnimeDataBase
): AnimeRepository {

    override suspend fun upsertAnime(animeEntity: AnimeEntity) {
        animeDataBase.animeDao.upsertAnime(animeEntity)
    }

    override suspend fun getAnimeById(animeId: String): AnimeEntity? {
        return animeDataBase.animeDao.getAnimeById(animeId)
    }

    override suspend fun getAnimeWatch(): List<Anime> {
        return animeDataBase.animeDao.getAnimeWatch().map { it.toAnime() }
    }

    override suspend fun getAnimeViewed(): List<Anime> {
        return animeDataBase.animeDao.getAnimeViewed().map { it.toAnime() }
    }

    override suspend fun getAnimePostponed(): List<Anime> {
        return animeDataBase.animeDao.getAnimePostponed().map { it.toAnime() }
    }

    override suspend fun getAnimeAbandoned(): List<Anime> {
        return animeDataBase.animeDao.getAnimeAbandoned().map { it.toAnime() }
    }

    override suspend fun getAnimePlans(): List<Anime> {
        return animeDataBase.animeDao.getAnimePlans().map { it.toAnime() }
    }

    override suspend fun getAnimeByName(title: String): List<AnimeEntity> {
        return animeDataBase.animeDao.getAnimeByName(title)
    }

    override suspend fun getAnimeList(): List<Anime> {
        return animeDataBase.animeDao.getAnimeList().map { it.toAnime() }
    }

    override suspend fun getWatchedEpisodesByAnimeId(animeId: String): List<WatchedEpisode> {
        return animeDataBase.animeDao.getWatchedEpisodesByAnimeId(animeId).map { it.toWatchedEpisode() }
    }

    override suspend fun addWatchedEpisode(watchedEpisode: WatchedEpisodeEntity) {
        animeDataBase.animeDao.addWatchedEpisode(watchedEpisode)
    }

    override suspend fun deleteAnime(id: String) {
        animeDataBase.animeDao.deleteAnime(id)
    }
}