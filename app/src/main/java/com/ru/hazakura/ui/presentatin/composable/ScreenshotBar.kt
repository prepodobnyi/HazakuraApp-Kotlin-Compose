package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ru.hazakura.domain.model.Screenshots

@Composable
fun ScreenshotBar(
    screens: List<Screenshots>,
    selectedScreenshotValue: MutableState<String?>
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        items(screens) { screen ->
            AsyncImage(
                model = screen.x332Url,
                contentDescription = "screenshot",
                modifier = Modifier
                    .padding(start = 4.dp, end = 4.dp)
                    .size(302.dp, 178.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .clickable { selectedScreenshotValue.value = screen.x332Url },
                contentScale = ContentScale.Crop,

            )

        }
    }
}