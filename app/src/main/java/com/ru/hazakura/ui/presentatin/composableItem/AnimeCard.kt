package com.ru.hazakura.ui.presentatin.composableItem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.materialIcon
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.ru.hazakura.domain.model.Anime

@Composable
fun AnimeCard(
    anime: Anime,
    onClick: () -> Unit,

) {
    val cardAspectRatio = 158f / 287f
    Box(
        modifier = Modifier
            .padding(start = 4.dp, end = 4.dp)
            .aspectRatio(cardAspectRatio)
            .size(158.dp,287.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
        ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ){
            AsyncImage(
                model = anime.poster.originalUrl,
                contentDescription = null,
                modifier = Modifier
                    .aspectRatio(158f / 242f)
                    .clip(RoundedCornerShape(
                        bottomStart = 16.dp,
                        bottomEnd = 16.dp,
                        topStart = 16.dp,
                        topEnd = 16.dp
                    )),
                contentScale = ContentScale.Crop,
            )
            Text(
                text = anime.russian,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = 12.sp,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                textAlign = TextAlign.Center,
            )
        }
    }
}


