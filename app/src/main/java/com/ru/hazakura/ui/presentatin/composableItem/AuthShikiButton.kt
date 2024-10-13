package com.ru.hazakura.ui.presentatin.composableItem

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.SettingsInputComponent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ru.hazakura.util.Screen

@Composable
fun AuthShikiButton(navController: NavController) {
    Button(onClick = {
            val authIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://shikimori.one/oauth/authorize?client_id=VSZHMtEs2tm0-zYahEH4k0o-pwOlIMD3OIcu1XxzAFQ&redirect_uri=hazakura%3A%2F%2Fauth&response_type=code&scope=")
                )
            navController.context.startActivity(authIntent) }
        )
        {
        Text("Authorize")
    }
}

@Composable
fun SearchAnimeBar(navController: NavController){
    Button(
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(start = 16.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurface),
        onClick = {navController.navigate(Screen.SearchAnime.route)}
    ) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically)
    {
        Icon(imageVector = Icons.Rounded.Menu, contentDescription = null)
        Text(
            text = "Что ищем семпай?",
            modifier = Modifier.weight(1f).padding(start = 8.dp),
            style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
        )
        Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
    }
    }
}

@Composable
fun FilterBar(navController: NavController){
    Button(
        modifier = Modifier.fillMaxWidth().height(56.dp).padding(start = 16.dp, end = 16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainerHighest,
            contentColor = MaterialTheme.colorScheme.onSurface),
        onClick = {navController.navigate(Screen.Filter.route)}
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            Icon(imageVector = Icons.Rounded.SettingsInputComponent, contentDescription = null)
            Text(
                text = "Поиск по Фильтрам",
                modifier = Modifier.weight(1f).padding(start = 8.dp),
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f))
            )
            Icon(imageVector = Icons.Rounded.Search, contentDescription = null)
        }
    }
}