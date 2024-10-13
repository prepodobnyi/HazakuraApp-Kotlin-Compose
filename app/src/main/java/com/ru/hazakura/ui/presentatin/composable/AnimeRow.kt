package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.ui.presentatin.composableItem.AnimeCard
import com.ru.hazakura.util.Screen

@Composable
fun AnimeRow(
    type: String,
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
        item{
            Box(
                modifier = Modifier
                    .size(158.dp,287.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Button(
                    onClick = { navController.navigate(Screen.ListAnimeScreen.createRoute(animeType = type))},
                    modifier = Modifier
                        .fillMaxSize(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground
                    )
                ) {
                    Text(
                        text = "MORE",
                        fontSize = 24.sp
                    )
                }
            }
        }
    }
}