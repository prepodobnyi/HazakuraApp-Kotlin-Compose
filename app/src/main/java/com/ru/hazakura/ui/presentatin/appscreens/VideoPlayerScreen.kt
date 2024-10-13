package com.ru.hazakura.ui.presentatin.appscreens

import android.app.Activity
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER
import android.content.pm.ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE
import android.os.Build
import android.view.MotionEvent
import android.view.View
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.datasource.DefaultHttpDataSource
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.exoplayer.source.MediaSource
import androidx.media3.exoplayer.source.ProgressiveMediaSource
import androidx.media3.ui.PlayerView
import androidx.media3.ui.PlayerView.SHOW_BUFFERING_WHEN_PLAYING
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import com.ru.hazakura.data.database.anime.WatchedEpisodeEntity
import com.ru.hazakura.ui.viewModelScreen.BottomNavBarViewModel
import com.ru.hazakura.ui.viewModelScreen.VideoPlayerViewModel
import java.net.URLDecoder

@Composable
fun VideoPlayerScreen(
    navBackStackEntry: NavBackStackEntry,
    navController: NavController,
    viewModel: BottomNavBarViewModel,
    videoPlayerViewModel: VideoPlayerViewModel = hiltViewModel()
    ) {
    val animeLink = navBackStackEntry.arguments?.getString("animeLink")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val episode = navBackStackEntry.arguments?.getString("episode")?.let {
        URLDecoder.decode(it, "UTF-8")
    }
    val id = navBackStackEntry.arguments?.getString("id")


    LaunchedEffect(animeLink) {
        videoPlayerViewModel.addVideoUri(animeLink ?: "")
        videoPlayerViewModel.playVideo(animeLink?: "")
    }
    Box(modifier = Modifier.fillMaxSize().background(Color.Black)) {
        FullScreenContent(viewModel) {
            VideoPlayer(id,episode,videoPlayerViewModel, navController)
        }
    }
}

@Composable
fun FullScreenContent(viewModel: BottomNavBarViewModel,content: @Composable () -> Unit) {

    val view = LocalView.current

    DisposableEffect(view) {
        view.keepScreenOn = true
        viewModel.changeStateVisabilityBottomBar()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            view.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }

        onDispose {

            view.keepScreenOn = false
            viewModel.changeStateVisabilityBottomBar()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                view.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
            }
        }
    }

    content()
}

@OptIn(UnstableApi::class)
@Composable
fun VideoPlayer(id: String?, episode: String?,viewModel: VideoPlayerViewModel,navController: NavController) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val activity = LocalContext.current as Activity
    var isFullScreen by rememberSaveable { mutableStateOf(viewModel.isFullScreen.value) }

    // Логика для переключения полноэкранного режима
    val enterFullscreen = {
        activity.requestedOrientation = SCREEN_ORIENTATION_USER_LANDSCAPE
        isFullScreen = true
        viewModel.isFullScreen.value= true
    }

    val exitFullscreen = {
        activity.requestedOrientation = SCREEN_ORIENTATION_USER
        isFullScreen = false
        viewModel.isFullScreen.value = false
    }
    BackHandler {
        exitFullscreen()
        val needSaveEpisode = viewModel.needSave.value
        val duration = viewModel.player.duration
        val currentPosition = viewModel.player.currentPosition
        // Проверка: больше ли 70% времени прошло
        if (!needSaveEpisode) {
            if (duration > 0 && currentPosition.toDouble() / duration >= 0.7) {
                viewModel.addEpisodeToWatched(WatchedEpisodeEntity(anime_id = id!!, episode_number = episode!!))
                viewModel.setFlagTrue(true)
            }
        }
        navController.popBackStack()
    }
    var lifecycle by remember {
        mutableStateOf(Lifecycle.Event.ON_CREATE)
    }
    DisposableEffect(key1 = lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            lifecycle = event
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            PlayerView(context).also {
                val player = viewModel.player
                it.player = player

                player.addListener(object : Player.Listener {
                    override fun onPlaybackStateChanged(state: Int) {
                        if (state == Player.STATE_READY) {
                            // Если плеер готов к воспроизведению, запускаем видео
                            player.playWhenReady = true
                        }
                    }
                })

                it.setShowBuffering(SHOW_BUFFERING_WHEN_PLAYING)
                it.setFullscreenButtonClickListener() {
                    if (isFullScreen) exitFullscreen() else enterFullscreen()
                }
            }
        },
        update = {
            when (lifecycle) {
                Lifecycle.Event.ON_RESUME -> {
                    it.onResume()
                }
                Lifecycle.Event.ON_PAUSE -> {
                    it.onPause()
                    it.player?.pause()
                }
                else -> Unit
            }
        }
    )
}

