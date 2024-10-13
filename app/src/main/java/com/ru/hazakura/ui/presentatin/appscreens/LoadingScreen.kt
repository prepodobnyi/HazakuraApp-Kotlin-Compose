package com.ru.hazakura.ui.presentatin.appscreens

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ru.hazakura.ui.viewModelScreen.LoadingScreenViewModel
import com.ru.hazakura.util.AccessCodeState
import com.ru.hazakura.util.Screen


@Composable
fun LoadingScreen(
    navController: NavController
) {
    val viewModel: LoadingScreenViewModel = hiltViewModel<LoadingScreenViewModel>()
    val accessCodeState = viewModel.hasAccessCode.collectAsState()

    if (accessCodeState.value != AccessCodeState.Checking) {
        LaunchedEffect(accessCodeState.value) {
            when (accessCodeState.value) {
                AccessCodeState.Empty -> {
                    navController.navigate(Screen.Auth.route) {
                        Log.i("AuthScreen", "перехожу в authScreen")
                        popUpTo(Screen.Loading.route) { inclusive = true }
                    }
                }

                AccessCodeState.Access -> {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Loading.route) { inclusive = true }
                    }
                }

                AccessCodeState.Checking -> {
                    // Ничего не делаем, так как это состояние указывает на проверку
                    // Возможно, можно добавить лог или просто оставить пустым
                }
            }
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 65.dp)

    ) {
        CircularProgressIndicator()
    }

}