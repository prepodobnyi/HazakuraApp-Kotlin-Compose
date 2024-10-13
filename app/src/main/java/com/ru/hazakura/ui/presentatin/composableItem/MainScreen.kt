package com.ru.hazakura.ui.presentatin.composableItem

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ru.hazakura.ui.presentatin.appscreens.bookmarkscreentab.BookmarkScreen
import com.ru.hazakura.ui.presentatin.appscreens.PersonScreen
import com.ru.hazakura.ui.presentatin.appscreens.bookmarkscreentab.BookmarkScreenTab
import com.ru.hazakura.ui.presentatin.appscreens.homescreentab.HomeScreenTab
import com.ru.hazakura.ui.presentatin.appscreens.searchscreentab.SearchScreenTab
import com.ru.hazakura.ui.presentatin.composable.BottomNavigationBar
import com.ru.hazakura.ui.viewModelScreen.BottomNavBarViewModel
import com.ru.hazakura.util.Screen


@Composable
fun MainScreen(
    mainNavController: NavController,
    viewModel: BottomNavBarViewModel = hiltViewModel()
) {
    val isShowBottomBar by viewModel.isShowBottomBar.collectAsStateWithLifecycle()
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStackEntry?.destination
    Scaffold(
        modifier = Modifier,
        bottomBar = {
            if(isShowBottomBar) {
                BottomNavigationBar(navController, currentDestination)
            }
        }
    ){ innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.HomeTab.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.HomeTab.route){ HomeScreenTab(navController,viewModel) }
            composable(Screen.SearchTab.route){ SearchScreenTab(viewModel) }
            composable(Screen.BookmarkTab.route){ BookmarkScreenTab(viewModel) }
            composable(Screen.Person.route){ PersonScreen() }
        }

    }
}

