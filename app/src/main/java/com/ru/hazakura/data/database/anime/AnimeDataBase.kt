package com.ru.hazakura.data.database.anime

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [AnimeEntity::class, WatchedEpisodeEntity::class],
    version = 3
)
abstract class AnimeDataBase: RoomDatabase() {
    abstract val animeDao: AnimeDao
}