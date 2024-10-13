package com.ru.hazakura.data.database.anime

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface AnimeDao {
    @Upsert
    suspend fun upsertAnime(animeEntity: AnimeEntity)

    @Query("SELECT * FROM AnimeEntity WHERE id = :animeId ")
    suspend fun getAnimeById(animeId: String): AnimeEntity

    @Query("SELECT * FROM AnimeEntity WHERE LOWER(russian) LIKE LOWER(:title)")
    suspend fun getAnimeByName(title: String): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity WHERE progress = 'watch' ")
    suspend fun getAnimeWatch(): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity WHERE progress = 'Viewed' ")
    suspend fun getAnimeViewed(): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity WHERE progress = 'Postponed' ")
    suspend fun getAnimePostponed(): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity WHERE progress = 'Abandoned' ")
    suspend fun getAnimeAbandoned(): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity WHERE progress = 'Plans' ")
    suspend fun getAnimePlans(): List<AnimeEntity>

    @Query("SELECT * FROM AnimeEntity ORDER BY addedAt DESC")
    suspend fun getAnimeList(): List<AnimeEntity>

    @Query("SELECT * FROM WatchedEpisodeEntity WHERE anime_id = :animeId")
    suspend fun getWatchedEpisodesByAnimeId(animeId: String): List<WatchedEpisodeEntity>

    @Upsert
    suspend fun addWatchedEpisode(watchedEpisode: WatchedEpisodeEntity)

    @Query("DELETE FROM AnimeEntity WHERE id = :id")
    fun deleteAnime(id: String)
}