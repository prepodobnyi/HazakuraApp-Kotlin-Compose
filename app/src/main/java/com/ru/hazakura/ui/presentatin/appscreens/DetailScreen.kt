package com.ru.hazakura.ui.presentatin.appscreens


import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.domain.repository.AnimeRepository
import com.ru.hazakura.ui.presentatin.composable.AnimeRowChronology
import com.ru.hazakura.ui.presentatin.composable.AnimeRowSimilar
import com.ru.hazakura.ui.presentatin.composable.CommentsColumn
import com.ru.hazakura.ui.presentatin.composable.ScreenshotBar
import com.ru.hazakura.ui.presentatin.composableItem.FullScreenImage
import com.ru.hazakura.ui.viewModelScreen.DetailScreenViewModel
import com.ru.hazakura.util.Screen
import java.net.URLDecoder

@Composable
fun DetailScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: DetailScreenViewModel = hiltViewModel(),
    navController: NavController,
) {

    val animeDetail by viewModel.animeDetail.collectAsStateWithLifecycle()
    val animeSimilar by viewModel.animeSimilar.collectAsStateWithLifecycle()
    val comments by viewModel.comments.collectAsStateWithLifecycle()
    val dubbingLink by viewModel.dubbingLink.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()
    val animeinDb by viewModel.animeinDb.collectAsStateWithLifecycle()

    var expanded by remember{mutableStateOf(false)}
    var expandedText by remember{mutableStateOf(false)}
    var shouldShowExpandutton by remember{mutableStateOf(false)}
    var selectedScreenshotValue = rememberSaveable {
        mutableStateOf<String?>(null)
    }
    var selectedItem by remember { mutableStateOf("null") }
    LaunchedEffect(animeinDb) {
        if (animeinDb != null) {
            selectedItem = animeinDb?.progress!!
        }
    }
    val animatedHeight by animateDpAsState(targetValue = if (expanded) 432.dp else 134.dp,animationSpec= tween(durationMillis = 300))
    val id = navBackStackEntry.arguments?.getString("id")
    val isFetched = rememberSaveable { mutableStateOf(false) }
    LaunchedEffect(id) {
        if (id != null && !isFetched.value) {
            viewModel.fetchAnime(id)
            isFetched.value = true
        }
    }
    val coverUrl = navBackStackEntry.arguments?.getString("coverUrl")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val animeTitle = navBackStackEntry.arguments?.getString("animeTitle")?.let {
        URLDecoder.decode(it, "UTF-8")
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(372.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = coverUrl,
                contentDescription = "background image",
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer { alpha = 0.99f }
                    .drawWithContent {
                        val colors = listOf(
                            Color.Transparent,
                            Color.Black
                        )
                        val colors2 = listOf(
                            Color.Black,
                            Color.Transparent
                        )
                        drawContent()
                        drawRect(
                            size = Size(size.width, size.height / 4),
                            brush = Brush.verticalGradient(colors, startY = 0.0f, size.height / 4),
                            blendMode = BlendMode.DstIn
                        )
                        drawRect(
                            size = Size(size.width, size.height / 2),
                            brush = Brush.verticalGradient(
                                colors2,
                                startY = size.height / 2,
                                size.height - 10f
                            ),
                            blendMode = BlendMode.DstIn,
                            topLeft = Offset(0f, size.height / 2)
                        )
                    }
                    .blur(
                        radiusX = 10.dp,
                        radiusY = 10.dp,
                        edgeTreatment = BlurredEdgeTreatment(RoundedCornerShape(8.dp))
                    ),

                contentScale = ContentScale.Crop,
            )
            if (animeDetail == null) {
                AsyncImage(
                    model = coverUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .size(258.dp, 372.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(258.dp, 372.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    AsyncImage(
                        model = animeDetail?.poster?.originalUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = "${animeDetail?.score ?: 0.0}",
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(8.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow.copy(alpha = 0.65f))
                            .padding(4.dp)
                    )
                }
            }
        }
        Text(
            text = animeTitle.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )




        if (animeDetail == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(modifier = Modifier.fillMaxWidth()) {

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp, top = 8.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    FilterChip(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            if (selectedItem != "watch") {
                                if(animeinDb?.lastDubbbing != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            lastDubbbing = animeinDb!!.lastDubbbing,
                                            progress = "watch"
                                        )
                                    )
                                }else if(animeinDb != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            progress = "watch"
                                        )
                                    )
                                }else{
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            progress = "watch"
                                        )
                                    )
                                }
                                selectedItem = "watch"
                            }
                        },
                        label = {
                            Text("Смотрю")
                        },
                        selected = selectedItem == "watch",

                        )
                    FilterChip(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            if (selectedItem != "Viewed") {
                                if(animeinDb?.lastDubbbing != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            lastDubbbing = animeinDb!!.lastDubbbing,
                                            progress = "Viewed"
                                        )
                                    )
                                }else if(animeinDb != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            progress = "Viewed"
                                        )
                                    )
                                }else{
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            progress = "Viewed"
                                        )
                                    )
                                }
                                selectedItem = "Viewed"
                            }
                        },
                        label = {
                            Text("Просмотрено")
                        },
                        selected = selectedItem == "Viewed",

                        )
                    FilterChip(
                        onClick = {
                            if (selectedItem != "Postponed") {
                                if(animeinDb?.lastDubbbing != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            lastDubbbing = animeinDb!!.lastDubbbing,
                                            progress = "Postponed"
                                        )
                                    )
                                }else if(animeinDb != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            progress = "Postponed"
                                        )
                                    )
                                }else{
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            progress = "Postponed"
                                        )
                                    )
                                }
                                selectedItem = "Postponed"
                            }
                        },
                        label = {
                            Text("Отложено")
                        },
                        selected = selectedItem == "Postponed",
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 8.dp),
                    horizontalArrangement = Arrangement.Center
                )
                {
                    FilterChip(
                        modifier = Modifier.padding(end = 8.dp),
                        onClick = {
                            if (selectedItem != "Abandoned") {
                                if(animeinDb?.lastDubbbing != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            lastDubbbing = animeinDb!!.lastDubbbing,
                                            progress = "Abandoned"
                                        )
                                    )
                                }else if(animeinDb != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            progress = "Abandoned"
                                        )
                                    )
                                }else{
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            progress = "Abandoned"
                                        )
                                    )
                                }
                                selectedItem = "Abandoned"
                            }
                        },
                        label = {
                            Text("Брошено")
                        },
                        selected = selectedItem == "Abandoned",
                    )
                    FilterChip(
                        onClick = {
                            if (selectedItem != "Plans") {
                                if(animeinDb?.lastDubbbing != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            lastDubbbing = animeinDb!!.lastDubbbing,
                                            progress = "Plans"
                                        )
                                    )
                                }else if(animeinDb != null){
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = animeinDb!!.id,
                                            russian = animeinDb!!.russian,
                                            poster = animeinDb!!.poster,
                                            progress = "Plans"
                                        )
                                    )
                                }else{
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            progress = "Plans"
                                        )
                                    )
                                }
                                selectedItem = "Plans"
                            }
                        },
                        label = {
                            Text("В планах")
                        },
                        selected = selectedItem == "Plans",

                        )
                }
            }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 0.dp, start = 16.dp, end = 16.dp)
                        .clip(RoundedCornerShape(16.dp))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = animatedHeight)
                            .padding(bottom = 48.dp, top = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "Статус:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${animeDetail?.status}")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Год выхода:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(text = "${animeDetail?.airedOn?.substring(0, 4)}")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "Студия:", fontWeight = FontWeight.Bold, fontSize = 18.sp, modifier = Modifier.align(
                                Alignment.CenterVertically))
                            LazyRow {
                                if (animeDetail?.studios?.size != 0) {
                                    items(animeDetail?.studios!!.size) { item ->
                                        TextButton(
                                            onClick = {
                                                navController.navigate(
                                                    Screen.ListAnimeScreen.createRoute(
                                                        "${animeDetail?.studios!![item].id}",
                                                        "${animeDetail?.studios!![item].name}"


                                                    )
                                                )
                                            },
                                            contentPadding = PaddingValues(0.dp),
                                            modifier = Modifier
                                                .height(28.dp)
                                                .padding(end = 4.dp),
                                        )
                                        {
                                            if(animeDetail?.studios?.size!! > 1) {
                                                if (item != animeDetail?.studios?.size!! - 1) {
                                                    Text(
                                                        text = "${animeDetail?.studios!![item].name},",
                                                        fontSize = 16.sp
                                                    )
                                                }else{
                                                    Text(
                                                        text = "${animeDetail?.studios!![item].name}",
                                                        fontSize = 16.sp
                                                    )
                                                }
                                            }else{
                                                Text(
                                                    text = "${animeDetail?.studios!![item].name}",
                                                    fontSize = 16.sp
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "Тип:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${animeDetail?.kind}")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "Рейтинг:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${animeDetail?.rating}")
                        }



                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "Жанры:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${animeDetail?.genres}")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(text = "Тема:", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            Text(text = "${animeDetail?.theme}")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Вышло серий:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(text = "${animeDetail?.episodesAired} из ${animeDetail?.episodes}")
                        }

                        Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(
                                text = "Следующий эпизод:",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp
                            )
                            Text(text = "${animeDetail?.nextEpisodeAt}")
                        }
                    }
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.BottomStart)
                    ) {
                        TextButton(onClick = { expanded = !expanded }) {
                            Text(text = if (expanded) "Свернуть" else "Развернуть")
                        }
                    }
                }
            if(!isLoading) {
                if (dubbingLink != null) {
                    Button(
                        onClick = {
                            navController.navigate(
                                Screen.DubbingAndSeria.createRoute(
                                    "$id",
                                    "${animeDetail?.poster?.originalUrl}",
                                    "$animeTitle",
                                    "${animeDetail?.episodes}",
                                    "$dubbingLink"
                                )
                            )
                        },
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        )
                    ) {
                        Text(text = "СМОТРЕТЬ")
                    }
                } else {
                    Text(
                        text = "Не нашли серии семпай",
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(8.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow),
                        color = MaterialTheme.colorScheme.onSurface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }
            }else{
                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        CircularProgressIndicator()
                    }
            }

                Column(modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                    .animateContentSize(tween(durationMillis = 300)))
                {

                    Text(
                        text = "${animeDetail?.description}",
                        onTextLayout = {textLayoutResult ->

                            shouldShowExpandutton = textLayoutResult.lineCount > 4
                        },
                        modifier = Modifier.fillMaxWidth(),

                        maxLines = (if (expandedText) Int.MAX_VALUE else 5),
                        overflow = TextOverflow.Ellipsis,

                    )
                    if(shouldShowExpandutton) {
                        TextButton(onClick = { expandedText = !expandedText },
                            modifier = Modifier.align(Alignment.CenterHorizontally)) {
                            Text(text = if (expandedText) "Свернуть" else "Развернуть")
                        }
                    }
                }

            if(animeDetail?.screenshots!!.isNotEmpty()) {
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Кадры",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                ScreenshotBar(screens = animeDetail?.screenshots!!, selectedScreenshotValue)
            }
            if(animeDetail?.chronology!!.isNotEmpty()){
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Порядок просмотра",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
                Spacer(modifier = Modifier.padding(8.dp))
            AnimeRowChronology(animeDetail?.chronology!!,navController)
            }
            if (animeSimilar.isNotEmpty()){
                Spacer(modifier = Modifier.padding(8.dp))
                Text(
                    text = "Похожие",
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 16.dp)
                )
                Spacer(modifier = Modifier.padding(8.dp))
                AnimeRowSimilar(animeSimilar,navController)
            }
            Spacer(modifier = Modifier.padding(8.dp))
            Text(
                text = "Комментарии",
                color = MaterialTheme.colorScheme.onBackground,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 16.dp)
            )
            if (comments.isNotEmpty()){
                Spacer(modifier = Modifier.padding(8.dp))
                CommentsColumn(comments = comments, selectedScreenshotValue)
            }
        }

    }
    if (selectedScreenshotValue.value != null){
        FullScreenImage(selectedScreenshotValue.value){
            selectedScreenshotValue.value = null
        }
    }
}

