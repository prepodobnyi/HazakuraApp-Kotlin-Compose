package com.ru.hazakura.ui.presentatin.appscreens.homescreentab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.ru.hazakura.ui.presentatin.composableItem.AnimeCard
import com.ru.hazakura.ui.viewModelScreen.SearchAnimeViewModel
import com.ru.hazakura.util.Screen


@Composable
fun SearchAnimeScreen(
    navController: NavController,
    viewModel: SearchAnimeViewModel = hiltViewModel()
    ){
    val animeList by viewModel.animeList.collectAsStateWithLifecycle()
    val emptyListAnime by viewModel.emptyListAnime.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    var text by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(start = 16.dp, end = 16.dp)
            .clip(RoundedCornerShape(50.dp))
            .background(MaterialTheme.colorScheme.surfaceContainerHighest)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Icon(
                modifier = Modifier.padding(start = 20.dp),
                imageVector = Icons.Rounded.Menu,
                contentDescription = null
            )
            TextField(
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .focusRequester(focusRequester),
                textStyle = TextStyle(fontSize = 16.sp),
                value = text,
                singleLine = true,
                onValueChange = { text = it },
                placeholder = {
                    Text(
                        text = "Поиск Аниме",
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    )
                },
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,// Убираем фон
                    focusedIndicatorColor = Color.Transparent, // Убираем индикатор фокуса
                    unfocusedIndicatorColor = Color.Transparent // Убираем индикатор без фокуса
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done // Устанавливаем действие клавиши "Done" (или "Enter")
                ),
                keyboardActions = KeyboardActions(
                    onDone = { // Действие при нажатии клавиши "Done"
                        viewModel.searchAnime(text)
                        focusManager.clearFocus()
                    })
            )
            Icon(modifier = Modifier.padding(end = 20.dp),imageVector = Icons.Rounded.Search, contentDescription = null)
        }
    }
    if (animeList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.padding(top = 60.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(animeList.size) { anime ->
                AnimeCard(animeList[anime], onClick = {
                    navController.navigate(
                        Screen.DetailAnime.createRoute(
                            animeList[anime].id,
                            animeList[anime].poster.originalUrl,
                            animeList[anime].russian)
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

    }else if(emptyListAnime){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "по запросу нет результата")
        }
    }
    // добавить функцию поиска на kodik
}