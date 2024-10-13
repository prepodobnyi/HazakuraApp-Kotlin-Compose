package com.ru.hazakura.ui.presentatin.composableItem

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun FullScreenImage(image: String?, onDismiss: () -> Unit){
    Box(modifier = Modifier.fillMaxSize().background(Color.Black.copy(alpha = 0.9f)).padding(16.dp),contentAlignment = Alignment.Center) {
        Box(modifier = Modifier
            .fillMaxSize()
            .clickable { onDismiss() })
        AsyncImage(model = image, contentDescription = "", modifier = Modifier
            .fillMaxSize(), contentScale = ContentScale.Fit )
    }
}