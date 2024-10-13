package com.ru.hazakura.ui.presentatin.appscreens.bookmarkscreentab

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ru.hazakura.ui.presentatin.appscreens.DetailScreen
import com.ru.hazakura.ui.presentatin.appscreens.DubbingAndSeriaScreen
import com.ru.hazakura.ui.presentatin.appscreens.VideoPlayerScreen
import com.ru.hazakura.ui.viewModelScreen.BottomNavBarViewModel
import com.ru.hazakura.util.Screen

@Composable
fun BookmarkScreenTab(viewModel: BottomNavBarViewModel) {
    val BookmarkNavController = rememberNavController()
    NavHost(
        navController = BookmarkNavController,
        startDestination = Screen.Bookmark.route,
    ) {

        composable(Screen.Bookmark.route) { BookmarkScreen(BookmarkNavController) }
        composable(Screen.DetailAnime.route) { navBackStackEntry ->
            DetailScreen(navBackStackEntry, navController = BookmarkNavController)
        }
        composable(Screen.DubbingAndSeria.route) { navBackStackEntry ->
            DubbingAndSeriaScreen(navBackStackEntry, navController = BookmarkNavController)
        }
        composable(Screen.VideoPlayer.route) { navBackStackEntry ->
            VideoPlayerScreen(navBackStackEntry, navController = BookmarkNavController,viewModel)
        }
    }
}