package com.ru.hazakura.ui.presentatin.appscreens.bookmarkscreentab

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.TabRow
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.ru.hazakura.ui.presentatin.composableItem.AnimeCard
import com.ru.hazakura.ui.viewModelScreen.BookmarkScreenViewModel
import com.ru.hazakura.util.Screen
import com.ru.hazakura.util.TabItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BookmarkScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel = hiltViewModel()
) {
    val tabs = listOf(TabItem.History, TabItem.Watch, TabItem.Viewed, TabItem.Plans, TabItem.Postponed, TabItem.Abandoned)
    val pagerState = rememberPagerState(pageCount = {
        tabs.size
    })

    Column() {
        Tabs(tabs = tabs, pagerState = pagerState)
        TabsContent(BookmarkNavController, pagerState = pagerState, viewModel = viewModel)
    }

}


@Composable
fun HistoryScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel
){
    val animeList by viewModel.animeHistoryList.collectAsStateWithLifecycle()
    val emptyListAnime by viewModel.emptyListAnime.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()


    if (animeList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(animeList.size) { anime ->
                AnimeCard(animeList[anime], onClick = {
                    BookmarkNavController.navigate(
                        Screen.DetailAnime.createRoute(
                            animeList[anime].id,
                            animeList[anime].poster.originalUrl,
                            animeList[anime].russian)
                    )
                })
            }

        }
    }else if(emptyListAnime){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "История чиста, прямо как ты семпай")
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
fun PostponedScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel
){
    val postedList by viewModel.PostedList.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()


    if (postedList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(postedList.size) { anime ->
                AnimeCard(postedList[anime], onClick = {
                    BookmarkNavController.navigate(
                        Screen.DetailAnime.createRoute(
                            postedList[anime].id,
                            postedList[anime].poster.originalUrl,
                            postedList[anime].russian)
                    )
                })
            }

        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Пусто")
        }
    }
}

@Composable
fun ViewedScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel
){
    val viewedList by viewModel.ViewedList.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    if (viewedList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewedList.size) { anime ->
                AnimeCard(viewedList[anime], onClick = {
                    BookmarkNavController.navigate(
                        Screen.DetailAnime.createRoute(
                            viewedList[anime].id,
                            viewedList[anime].poster.originalUrl,
                            viewedList[anime].russian)
                    )
                })
            }

        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Пусто")
        }
    }

}

@Composable
fun PlansScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel
){
    val viewedList by viewModel.PlansList.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()


    if (viewedList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewedList.size) { anime ->
                AnimeCard(viewedList[anime], onClick = {
                    BookmarkNavController.navigate(
                        Screen.DetailAnime.createRoute(
                            viewedList[anime].id,
                            viewedList[anime].poster.originalUrl,
                            viewedList[anime].russian)
                    )
                })
            }

        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Пусто")
        }
    }
}

@Composable
fun AbandonedScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel
){
    val viewedList by viewModel.AbadonedList.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    if (viewedList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewedList.size) { anime ->
                AnimeCard(viewedList[anime], onClick = {
                    BookmarkNavController.navigate(
                        Screen.DetailAnime.createRoute(
                            viewedList[anime].id,
                            viewedList[anime].poster.originalUrl,
                            viewedList[anime].russian)
                    )
                })
            }

        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Пусто")
        }
    }
}

@Composable
fun WatchScreen(
    BookmarkNavController: NavHostController,
    viewModel: BookmarkScreenViewModel
){
    val viewedList by viewModel.WatchList.collectAsStateWithLifecycle()
    val listState = rememberLazyGridState()

    if (viewedList.isNotEmpty()){
        LazyVerticalGrid(
            modifier = Modifier.fillMaxSize().padding(top = 8.dp),
            columns = GridCells.Fixed(3),
            state = listState,
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(viewedList.size) { anime ->
                AnimeCard(viewedList[anime], onClick = {
                    BookmarkNavController.navigate(
                        Screen.DetailAnime.createRoute(
                            viewedList[anime].id,
                            viewedList[anime].poster.originalUrl,
                            viewedList[anime].russian)
                    )
                })
            }

        }
    }else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        ) {
            Text(text = "Пусто")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Tabs(tabs: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()

    ScrollableTabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground) {
        tabs.forEachIndexed { index, tab ->
            // OR Tab()
            LeadingIconTab(
                icon = { },
                text = { Text(tab.title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TabsContent(BookmarkNavController: NavHostController, pagerState: PagerState, viewModel: BookmarkScreenViewModel ) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> HistoryScreen(BookmarkNavController,viewModel)

            1 -> WatchScreen(BookmarkNavController,viewModel)

            2 ->ViewedScreen(BookmarkNavController,viewModel)

            3->PlansScreen(BookmarkNavController,viewModel)

            4->PostponedScreen(BookmarkNavController,viewModel)

            5->AbandonedScreen(BookmarkNavController,viewModel)
        }
    }
}