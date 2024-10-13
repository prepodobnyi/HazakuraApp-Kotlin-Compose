package com.ru.hazakura.ui.presentatin.appscreens

import android.util.Log
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ru.hazakura.ui.presentatin.composableItem.AuthShikiButton
import com.ru.hazakura.ui.viewModelScreen.AuthScreenViewModel
import com.ru.hazakura.util.Checker
import com.ru.hazakura.util.Screen

@Composable
fun AuthScreen(
    navController: NavController
) {
    val viewModel: AuthScreenViewModel = hiltViewModel()
    val isChecked = viewModel.isChecked.collectAsState()

    if(isChecked.value != Checker.Checking)
        LaunchedEffect(isChecked.value) {
            when (isChecked.value) {
                Checker.Checking -> {

                }
                Checker.Not -> {

                }
                Checker.Ok -> {
                    navController.navigate(Screen.Loading.route){
                        popUpTo(Screen.Auth.route) { inclusive = true }
                    }
                }

                else -> {}
            }
        }
    Surface{
        if (isChecked.value == Checker.Not) {
            AuthShikiButton(navController = navController)
        }else{
            Text(text = "Идет авторизация...")
        }
    }
}