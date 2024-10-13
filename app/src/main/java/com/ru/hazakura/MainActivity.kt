package com.ru.hazakura

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.ru.hazakura.ui.presentatin.composableItem.AppNavigation
import com.ru.hazakura.ui.presentatin.composableItem.SetBarColor
import com.ru.hazakura.ui.theme.HazakuraAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)

        enableEdgeToEdge()
        setContent {
            HazakuraAppTheme {
                SetBarColor(androidx.compose.ui.graphics.Color.Transparent, MaterialTheme.colorScheme.background)
                Surface(
                    modifier = Modifier
                        .fillMaxSize().statusBarsPadding(),
                    color = MaterialTheme.colorScheme.background
                ) {

                AppNavigation()

                }
            }
        }
    }
}

