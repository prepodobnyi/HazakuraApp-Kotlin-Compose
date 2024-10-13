package com.ru.hazakura.ui.presentatin.appscreens.homescreentab


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ru.hazakura.ui.presentatin.composable.AnimeCarousel
import com.ru.hazakura.ui.presentatin.composable.AnimeRow
import com.ru.hazakura.ui.presentatin.composableItem.SearchAnimeBar
import com.ru.hazakura.ui.viewModelScreen.HomeScreenViewModel


@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel()
) {
    val animeData by viewModel.animeData.collectAsStateWithLifecycle()
    if(animeData == null){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MaterialTheme.colorScheme.surface)

        ) {


            AnimeCarousel(animeData!!.ongoings, navController)
            Spacer(modifier = Modifier.padding(8.dp))
            SearchAnimeBar(navController)
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Аниме сезона",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AnimeRow(animeItems = animeData!!.seasonAnime, navController = navController, type = "season_anime")

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Популярное",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AnimeRow("popular",animeData!!.populars, navController)

            Spacer(modifier = Modifier.padding(8.dp))

            Text(
                text = "Случайные",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            Spacer(modifier = Modifier.padding(8.dp))

            AnimeRow("random",animeData!!.random, navController)

            //CollectionAnime()
            Spacer(modifier = Modifier.padding(16.dp))
        }
    }
}