package com.ru.hazakura.ui.presentatin.composableItem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navDeepLink
import com.ru.hazakura.ui.presentatin.appscreens.AuthScreen
import com.ru.hazakura.ui.presentatin.appscreens.LoadingScreen
import com.ru.hazakura.ui.viewModelScreen.AuthScreenViewModel
import com.ru.hazakura.util.Screen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()
    val uri = "hazakura://auth?code="

    NavHost(
        navController = navController,
        startDestination = Screen.Loading.route
    ) {

        composable(Screen.Loading.route){
            LoadingScreen(navController = navController)
        }
        composable(Screen.Auth.route, deepLinks = listOf(
            navDeepLink { uriPattern = "$uri{code}" }
        )){ backStackEntry ->
            val code = backStackEntry.arguments?.getString("code")
            if (!code.isNullOrEmpty()) {
                val viewModel: AuthScreenViewModel = hiltViewModel()
                LaunchedEffect(code) {
                    viewModel.handleIntent(code)
                }
            }
            AuthScreen(navController = navController)
        }
        composable(Screen.Main.route){
            MainScreen(mainNavController = navController)
        }
    }
}