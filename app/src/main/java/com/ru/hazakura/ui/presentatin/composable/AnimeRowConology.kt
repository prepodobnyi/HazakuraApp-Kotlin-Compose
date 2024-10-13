package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.AnimeChronology
import com.ru.hazakura.ui.presentatin.composableItem.AnimeCard
import com.ru.hazakura.util.Screen

@Composable
fun AnimeRowChronology(
    animeItems: List<AnimeChronology>,
    navController: NavController
) {
    val listState = rememberLazyListState()
    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        items(animeItems.reversed()){anime ->
            AnimeCard(
                anime = Anime(anime.id, anime.russian, anime.poster),
                onClick = {
                    navController.navigate(
                        Screen.DetailAnime.createRoute(
                            anime.id,
                            anime.poster.originalUrl,
                            anime.russian)
                    )
                }
            )
        }
    }
}