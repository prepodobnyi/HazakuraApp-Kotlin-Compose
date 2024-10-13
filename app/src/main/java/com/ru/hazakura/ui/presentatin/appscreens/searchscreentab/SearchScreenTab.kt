package com.ru.hazakura.ui.presentatin.appscreens.searchscreentab

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ru.hazakura.ui.presentatin.appscreens.DetailScreen
import com.ru.hazakura.ui.presentatin.appscreens.DubbingAndSeriaScreen
import com.ru.hazakura.ui.presentatin.appscreens.VideoPlayerScreen
import com.ru.hazakura.ui.presentatin.appscreens.homescreentab.HomeScreen
import com.ru.hazakura.ui.presentatin.appscreens.homescreentab.ListAnimeScreen
import com.ru.hazakura.ui.presentatin.appscreens.homescreentab.SearchAnimeScreen
import com.ru.hazakura.ui.viewModelScreen.BottomNavBarViewModel
import com.ru.hazakura.util.Screen

@Composable
fun SearchScreenTab(viewModel: BottomNavBarViewModel) {
    val SearchNavController = rememberNavController()
    NavHost(
        navController = SearchNavController,
        startDestination = Screen.Search.route,
    ) {

        composable(Screen.Search.route) { SearchScreen(SearchNavController) }
        composable(Screen.DetailAnime.route) { navBackStackEntry ->
            DetailScreen(navBackStackEntry, navController = SearchNavController)
        }
        composable(Screen.SearchAnime.route){ SearchAnimeScreen(SearchNavController) }
        composable(Screen.DubbingAndSeria.route) { navBackStackEntry ->
            DubbingAndSeriaScreen(navBackStackEntry, navController = SearchNavController)
        }
        composable(Screen.VideoPlayer.route) { navBackStackEntry ->
            VideoPlayerScreen(navBackStackEntry, navController = SearchNavController,viewModel)
        }
        composable(Screen.Filter.route){
            FilterScreen(navController = SearchNavController)
        }
    }
}