package com.ru.hazakura.ui.presentatin.appscreens.homescreentab

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.ru.hazakura.ui.presentatin.appscreens.DetailScreen
import com.ru.hazakura.ui.presentatin.appscreens.DubbingAndSeriaScreen
import com.ru.hazakura.ui.presentatin.appscreens.VideoPlayerScreen
import com.ru.hazakura.ui.viewModelScreen.BottomNavBarViewModel
import com.ru.hazakura.util.Screen

@Composable
fun HomeScreenTab(navController: NavController, viewModel: BottomNavBarViewModel) {
    val homeNavController = rememberNavController()
    NavHost(
        navController = homeNavController,
        startDestination = Screen.Home.route,
    ) {

        composable(Screen.Home.route) { HomeScreen(homeNavController) }
        composable(Screen.DetailAnime.route) { navBackStackEntry ->
            DetailScreen(navBackStackEntry, navController = homeNavController)
        }
        composable(Screen.SearchAnime.route){ SearchAnimeScreen(homeNavController) }
        composable(Screen.ListAnimeScreen.route) { navBackStackEntry ->
            ListAnimeScreen(navBackStackEntry, navController = homeNavController)
        }
        composable(Screen.DubbingAndSeria.route) { navBackStackEntry ->
            DubbingAndSeriaScreen(navBackStackEntry, navController = homeNavController)
        }
        composable(Screen.VideoPlayer.route) { navBackStackEntry ->
            VideoPlayerScreen(navBackStackEntry, navController = homeNavController,viewModel)
        }
    }



}