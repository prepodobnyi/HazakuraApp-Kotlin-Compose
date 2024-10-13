package com.ru.hazakura.data.database.anime

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity
data class AnimeEntity(
    @PrimaryKey
    val id: String,
    val russian: String,
    val poster: String,
    val lastDubbbing: String? = null,
    val addedAt: Long = System.currentTimeMillis(),
    val progress: String = "watch",
)

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = AnimeEntity::class,
            parentColumns = ["id"],
            childColumns = ["anime_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WatchedEpisodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val anime_id: String,
    val episode_number: String,
)