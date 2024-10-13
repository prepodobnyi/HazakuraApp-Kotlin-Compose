package com.ru.hazakura.ui.presentatin.composableItem

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun SetBarColor(color: Color, colorStatusBar: Color) {
    val systemUiController = rememberSystemUiController()
    LaunchedEffect(key1 = color) {
        systemUiController.setSystemBarsColor(color)
        systemUiController.setStatusBarColor(colorStatusBar)
    }
}
