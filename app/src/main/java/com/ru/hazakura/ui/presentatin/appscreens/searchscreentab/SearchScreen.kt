package com.ru.hazakura.ui.presentatin.appscreens.searchscreentab

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ru.hazakura.ui.presentatin.composableItem.CalendarItem
import com.ru.hazakura.ui.presentatin.composableItem.FilterBar
import com.ru.hazakura.ui.presentatin.composableItem.SearchAnimeBar
import com.ru.hazakura.ui.viewModelScreen.SearchScreenViewModel
import com.ru.hazakura.util.Screen

@Composable
fun SearchScreen(
    navController: NavController,
    viewModel: SearchScreenViewModel = hiltViewModel()
) {
    val calendarData by viewModel.calendarData.collectAsState()
    val listState = rememberLazyListState()

    Column(modifier = Modifier.fillMaxSize()) {
        SearchAnimeBar(navController)
        FilterBar(navController)
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = "График выхода серий",
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.padding(8.dp))
        LazyColumn(
            state = listState,
            modifier = Modifier
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
            contentPadding = PaddingValues(top = 16.dp)
        ) {
            if(calendarData.isEmpty()){
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize(Alignment.Center)
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
            else{
                items(calendarData.size){ item ->
                    CalendarItem(
                        calendar = calendarData[item],
                        onClick = {
                        navController.navigate(
                            Screen.DetailAnime.createRoute(
                                calendarData[item].anime.id,
                                calendarData[item].anime.poster.originalUrl,
                                calendarData[item].anime.russian)
                        )
                    })
                }
            }
        }
    }

}
