package com.ru.hazakura.ui.presentatin.appscreens

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.TextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ru.hazakura.data.database.anime.AnimeDataBase
import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.domain.model.Translations
import com.ru.hazakura.ui.viewModelScreen.DubbingAndSeriaViewModel
import com.ru.hazakura.util.Screen
import com.ru.hazakura.util.rememberImeState
import java.net.URLDecoder

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DubbingAndSeriaScreen(
    navBackStackEntry: NavBackStackEntry,
    viewModel: DubbingAndSeriaViewModel = hiltViewModel(),
    navController: NavController
) {
    val id = navBackStackEntry.arguments?.getString("id")
    val coverUrl = navBackStackEntry.arguments?.getString("coverUrl")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val animeTitle = navBackStackEntry.arguments?.getString("animeTitle")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val episodes = navBackStackEntry.arguments?.getString("episodes")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val link = navBackStackEntry.arguments?.getString("link")?.let {
        URLDecoder.decode(it, "UTF-8")
    }

    LaunchedEffect(link) {
        if (link != null) {
            viewModel.fetchDubbing(link)
        }
    }



    LaunchedEffect(id) {
        if (id != null) {
            viewModel.fetchAnimeIdInDB(id)
            viewModel.fetchEpisodes(id)
        }
    }


    val dubbings by viewModel.dubbing.collectAsStateWithLifecycle()
    val animeEntity by viewModel.animeEntity.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val animeLink by viewModel.animeLink.collectAsStateWithLifecycle()
    val episode by viewModel.episode.collectAsStateWithLifecycle()
    val episodeWatched by viewModel.episodeWatched.collectAsStateWithLifecycle()
    val urlParams by viewModel.urlParams.collectAsStateWithLifecycle()

    var selectedIndex by remember { mutableStateOf(0) }
    val options = listOf("Озвучка", "Серии")

    LaunchedEffect(navController) {
        if (id != null){
            viewModel.fetchEpisodes(id)
            if (animeEntity != null) {
                selectedIndex = 1
            }
        }
    }

    var selectedDubbing by remember {
        mutableStateOf<Translations?>(null)
    }
    var filterText by remember { mutableStateOf("") }
    var selectedItem by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(dubbings) {
        if (dubbings.isNotEmpty()) {
            if(animeEntity?.lastDubbbing != null){
                selectedItem = animeEntity!!.lastDubbbing
                selectedDubbing = dubbings.filter { it.translationTitle == animeEntity!!.lastDubbbing }[0]
            }else {
                selectedItem = dubbings[0].translationTitle
                selectedDubbing = dubbings[0]
            }
        }
    }

// Фильтруем список по введенному тексту
    val filteredItems = dubbings.filter { it.translationTitle!!.contains(filterText, ignoreCase = true) }
    val imeState = rememberImeState()
    val scrollState = rememberScrollState()
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()

            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(if (imeState.value) 0.01f else 1.5f)
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = coverUrl,
                contentDescription = "background image",
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(28.dp)),
                contentScale = ContentScale.Crop,
            )
        }
        Text(
            text = animeTitle.toString(),
            color = MaterialTheme.colorScheme.onBackground,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )
        Text(
            text = if(selectedDubbing != null) "${selectedDubbing!!.dataEpisodeCount} из $episodes" else "0 из $episodes",
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        SingleChoiceSegmentedButtonRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            options.forEachIndexed { index, label ->
                SegmentedButton(
                    shape = SegmentedButtonDefaults.itemShape(index = index, count = options.size),
                    onClick = { selectedIndex = index },
                    selected = index == selectedIndex,
                    modifier = Modifier
                        .height(48.dp)
                ) {
                    if ((selectedItem != null) and (label == "Озвучка")) {
                        Text(
                            text = "$selectedItem",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }else{
                        Text(
                            text = label,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .weight(2f)
                .fillMaxWidth()
                .clip(
                    RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp)
                )
                .background(MaterialTheme.colorScheme.surfaceContainerLow),
        )
        {
            if(selectedIndex == 0) {
                stickyHeader {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .padding(bottom = 8.dp)
                            .background(MaterialTheme.colorScheme.surfaceContainerHigh),

                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically)
                    {
                        Icon(
                            modifier = Modifier.padding(start = 16.dp),
                            imageVector = Icons.Rounded.Search,
                            contentDescription = null
                        )
                        TextField(
                            modifier = Modifier
                                .weight(1f)
                                .height(52.dp),
                            textStyle = TextStyle(fontSize = 16.sp),
                            value = filterText,
                            onValueChange = { filterText = it },
                            singleLine = true,
                            placeholder = {
                                Text(
                                    text = "Озвучка...",
                                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                )
                            },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,// Убираем фон
                                focusedIndicatorColor = Color.Transparent, // Убираем индикатор фокуса
                                unfocusedIndicatorColor = Color.Transparent // Убираем индикатор без фокуса
                            ),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done // Устанавливаем действие клавиши "Done" (или "Enter")
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { // Действие при нажатии клавиши "Done"
                                    focusManager.clearFocus()
                                })
                        )
                    }
                }
                items(filteredItems.size) { item ->
                    val isSelected = filteredItems[item].translationTitle == selectedItem
                    Text(
                        text = filteredItems[item].translationTitle!!,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp)
                            .clip(RoundedCornerShape(28))
                            .background(if (isSelected) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
                            .clickable {
                                selectedItem =
                                    if (isSelected) null else filteredItems[item].translationTitle
                                selectedDubbing = filteredItems[item]
                            }
                            .padding(start = 16.dp, bottom = 12.dp, top = 12.dp, end = 16.dp)
                    )

                }
            }else{
                items(selectedDubbing?.dataEpisodeCount?.toInt() ?:0) { seria ->
                    val isWatched = episodeWatched.any { it.episode_number == seria+1 }
                    Text(
                        text = "${seria+1} Серия",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, bottom = 2.dp, end = 16.dp)
                            .clip(RoundedCornerShape(28))
                            .background(if (isWatched) MaterialTheme.colorScheme.secondaryContainer else Color.Transparent)
                            .clickable {
                                if(animeEntity != null) {
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            lastDubbbing = selectedDubbing?.translationTitle!!,
                                            progress = animeEntity?.progress!!
                                    )
                                    )
                                }else{
                                    viewModel.addAnimeInDB(
                                        AnimeEntity(
                                            id = id!!,
                                            russian = animeTitle!!,
                                            poster = coverUrl!!,
                                            lastDubbbing = selectedDubbing?.translationTitle!!,
                                        )
                                    )
                                }

                                viewModel.fetchAnimeLink(
                                    "${selectedDubbing?.dataMediaId}",
                                    "${selectedDubbing?.dataMediaHash}",
                                    "${seria + 1}",
                                    urlParams
                                )
                            }
                            .padding(start = 16.dp, bottom = 12.dp, top = 12.dp, end = 16.dp)
                    )

                }
            }
        }

        LaunchedEffect(key1 = animeLink) {
            if (animeLink != null) {
                navController.navigate(Screen.VideoPlayer.createRoute("$id","$animeLink", "$episode"))
                viewModel.animeLinkNull()
                viewModel.fetchAnimeIdInDB(id!!)
                }
            }
    }
    if (loading){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.6f))
                .clickable { }
                .wrapContentSize(Alignment.Center)
        ) {
            CircularProgressIndicator()
        }
    }
}