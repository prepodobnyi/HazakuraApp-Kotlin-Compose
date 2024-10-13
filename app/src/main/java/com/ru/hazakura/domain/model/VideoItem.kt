package com.ru.hazakura.domain.model

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItem(
    val contentUri: String,
    val mediaItem: MediaItem,
    val name: String
)
