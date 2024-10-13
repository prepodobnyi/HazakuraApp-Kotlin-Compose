package com.ru.hazakura.ui.presentatin.appscreens.homescreentab

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.ru.hazakura.ui.presentatin.composableItem.AnimeCard
import com.ru.hazakura.ui.viewModelScreen.DetailScreenViewModel
import com.ru.hazakura.ui.viewModelScreen.ListAnimeTypeViewModel
import com.ru.hazakura.util.Screen
import java.net.URLDecoder

@Composable
fun ListAnimeScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavController,
    viewModel: ListAnimeTypeViewModel = hiltViewModel()
) {
    val animeType = navBackStackEntry.arguments?.getString("animeType")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val dubbingTitle = navBackStackEntry.arguments?.getString("dubbingTitle")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val animeTypeList by viewModel.animeTypeList.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val needMore by viewModel.needMore.collectAsStateWithLifecycle()
    val emptyListAnime by viewModel.emptyListAnime.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()
    LaunchedEffect(animeType) {
        if (!animeTypeList.isNotEmpty()) {
            animeType?.let {
                viewModel.fetchListAnimeType(it, 1)
            }
        }

    }
    when(animeType){
        "ongoing" ->{
            MyTitleText("Онгоинги")
        }
        "popular" ->{
            MyTitleText("Популярное")
        }
        "season_anime" ->{
            MyTitleText("Аниме сезона")
        }
        "random"  ->{
            MyTitleText("Случайные")
        }
        else ->{
            MyTitleText("$dubbingTitle")
        }
    }
    if (animeTypeList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 32.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(animeTypeList.size) { anime ->
                AnimeCard(animeTypeList[anime], onClick = {
                    navController.navigate(
                        Screen.DetailAnime.createRoute(
                            animeTypeList[anime].id,
                            animeTypeList[anime].poster.originalUrl,
                            animeTypeList[anime].russian)
                    )
                })
            }

            // Показать индикатор загрузки внизу списка
            item {
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }
        LaunchedEffect(listState) {
            snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
                .collect { lastVisibleItemIndex ->
                    if (lastVisibleItemIndex != null) {
                        if (lastVisibleItemIndex > animeTypeList.size -9 && needMore) {
                            animeType?.let { viewModel.fetchListAnimeType(it, viewModel.currentPage.value) }
                        }
                    }
                }
        }
    }else if(emptyListAnime){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "по запросу нет результата")
        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }

}

@Composable
fun MyTitleText(text: String){
    Spacer(modifier = Modifier.padding(8.dp))
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground,
        style = MaterialTheme.typography.headlineSmall,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 16.dp)
    )
    Spacer(modifier = Modifier.padding(8.dp))
}