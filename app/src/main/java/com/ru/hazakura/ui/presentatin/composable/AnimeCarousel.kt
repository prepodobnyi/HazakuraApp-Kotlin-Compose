package com.ru.hazakura.ui.presentatin.composable

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.snapping.SnapFlingBehavior
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerScope
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.ru.hazakura.R
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.util.Screen
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AnimeCarousel(
    items: List<Anime>,
    navController: NavController
) {
    val newItems = items + listOf(Unit)
    val pageState = rememberPagerState(pageCount = { newItems.size })
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val contentPadding = calculateHorizontalPadding(screenWidth)

    val fling = PagerDefaults.flingBehavior(
        state = pageState,
        pagerSnapDistance = PagerSnapDistance.atMost(3)
    )
    HorizontalPager(
        state = pageState,
        contentPadding = PaddingValues(horizontal = contentPadding),
        pageSize = PageSize.Fixed(258.dp),
        beyondBoundsPageCount = 1,
        flingBehavior = fling,
        modifier = Modifier.padding(start = 4.dp, end = 4.dp)

    ) { page ->
        val pageOffset = ((pageState.currentPage - page) + pageState.currentPageOffsetFraction)
        val absPageOffset = pageOffset.absoluteValue.coerceIn(0f, 4f)
        // Установите корректное смещение для элементов

        val scale = when {
            pageOffset > 1f -> (1f + absPageOffset - 1f - (absPageOffset * 0.5f))
            pageOffset > 0f -> 0.5f + (1f - absPageOffset) * 0.5f
            pageOffset < -1f -> (1f + absPageOffset - 1f - (absPageOffset * 0.5f))
            else -> 0.5f + (1f - absPageOffset) * 0.5f
        }

        val horizontalOffsetDp = when {
            pageOffset > 1f -> 125.dp
            pageOffset > 0f -> 125.dp * pageOffset.absoluteValue.coerceIn(0f, 2f)
            pageOffset < -2f -> (-125).dp
            pageOffset < -1f -> (-125).dp * (pageOffset.absoluteValue.coerceIn(0f, 3f) - 1f)
            else -> 0.dp
        }

        val alpha = when {
            pageOffset > 1f || pageOffset < -1f -> 0.5f
            else -> 1 - absPageOffset * 0.5f
        }

        val horizontalOffsetPx = with(LocalDensity.current) { horizontalOffsetDp.toPx() }
        if (page < items.size) {
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = horizontalOffsetPx
                        this.alpha = alpha
                    }
                    .size(250.dp * scale, 400.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .clickable {
                        navController.navigate(
                            Screen.DetailAnime.createRoute(
                                items[page].id,
                                items[page].poster.originalUrl,
                                items[page].russian
                            )
                        )
                    }
            ) {
                // Основное изображение
                AsyncImage(
                    model = items[page].poster.originalUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop

                )

                // Градиентный фон и текст
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .clipToBounds()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black),
                                startY = 500f,
                                // Начинаем градиент чуть выше нижней части изображения
                            )
                        )
                ) {
                    Text(
                        text = items[page].russian,
                        color = Color.White,
                        fontSize = 16.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .wrapContentSize(unbounded = true) // Ширина текста не зависит от внешнего контейнера
                            .width(250.dp)
                            .align(Alignment.BottomCenter)
                            .padding(8.dp) // Отступы для текста
                    )
                }
            }
        }else{
            Box(
                modifier = Modifier
                    .graphicsLayer {
                        translationX = horizontalOffsetPx
                        this.alpha = alpha
                    }
                    .size(250.dp * scale, 400.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(MaterialTheme.colorScheme.background)
            ) {Button(
                onClick = { navController.navigate(Screen.ListAnimeScreen.createRoute(
                    animeType = "ongoing"
                )) },
                modifier = Modifier
                    .fillMaxSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                )
            ) {
                Text(
                    text = "MORE",
                    fontSize = 24.sp
                )
            }
            }
        }
    }
}
@Composable
fun calculateHorizontalPadding(screenWidth: Dp): Dp {
    // отнимем ширину элемента и разделим на 2, чтобы получить отступ по бокам
    val contentPadding = (screenWidth - 250.dp) / 2
    return contentPadding
}


