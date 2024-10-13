package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.ui.presentatin.composableItem.AnimeCard
import com.ru.hazakura.util.Screen

@Composable
fun AnimeRowSimilar(
    animeItems: List<Anime>,
    navController: NavController
) {
    val listState = rememberLazyListState()

    LazyRow(
        state = listState,
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        items(animeItems){anime ->
            AnimeCard(
                anime = anime,
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