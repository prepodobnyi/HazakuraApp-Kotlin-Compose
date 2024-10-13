package com.ru.hazakura.util

import java.net.URLEncoder
import kotlin.math.round

sealed class Screen(val route: String) {

    object Loading: Screen(route = "loading_screen")

    object Auth: Screen(route = "auth_screen")

    object Main: Screen(route = "main_screen")

    object Home: Screen(route = "home_screen")

    object HomeTab: Screen(route = "home_tab_screen")

    object Search: Screen(route = "search_screen")

    object SearchTab: Screen(route = "Search_tab_screen")

    object Filter: Screen(route = "filter_Screen")

    object ListAnimeScreen: Screen(route = "ListAnime_screen/{animeType}/{dubbingTitle}"){
        fun createRoute(animeType: String, dubbingTitle: String = "pass"): String{
            val encodedAnimeType = URLEncoder.encode(animeType, "UTF-8")
            val encodedDubbingTitle= URLEncoder.encode(dubbingTitle, "UTF-8")
            return "ListAnime_screen/$encodedAnimeType/$encodedDubbingTitle"
        }
    }

    object SearchAnime: Screen(route = "search_anime")

    object Bookmark: Screen(route = "bookmark_screen")

    object BookmarkTab: Screen(route = "bookmark_tab_screen")

    object Person: Screen(route = "person_screen")

    object DetailAnime : Screen(route = "detail_screen/{id}/{coverUrl}/{animeTitle}") {
        fun createRoute(id: String, coverUrl: String, animeTitle: String): String {
            // Кодирование URL и названия аниме для корректной передачи в маршруте
            val encodedUrl = URLEncoder.encode(coverUrl, "UTF-8")
            val encodedTitle = URLEncoder.encode(animeTitle, "UTF-8")
            return "detail_screen/$id/$encodedUrl/$encodedTitle"
        }
    }

    object DubbingAndSeria: Screen(route = "dubbing/{id}/{coverUrl}/{animeTitle}/{episodes}/{link}"){
        fun createRoute(id: String, coverUrl: String, animeTitle: String, episodes: String, link: String): String{
            val encodedUrl = URLEncoder.encode(coverUrl, "UTF-8")
            val encodedlink = URLEncoder.encode(link, "UTF-8")
            val encodedTitle = URLEncoder.encode(animeTitle, "UTF-8")
            val encodedEpisodes = URLEncoder.encode(episodes, "UTF-8")
            return "dubbing/$id/$encodedUrl/$encodedTitle/$encodedEpisodes/$encodedlink"
        }
    }

    object VideoPlayer: Screen(route = "videoScreen/{id}/{animeLink}/{episode}"){
        fun createRoute(id: String,animeLink: String, episode: String): String{
            val encodedAnimeLink = URLEncoder.encode(animeLink, "UTF-8")
            val encodeEpisode = URLEncoder.encode(episode, "UTF-8")
            return "videoScreen/$id/$encodedAnimeLink/$encodeEpisode"
        }
    }
}


sealed class TabItem(var title: String) {

    object Postponed : TabItem("Отложено")
    object Viewed : TabItem( "Просмотренно")
    object Plans : TabItem( "В планах")
    object Abandoned : TabItem( "Брошено")
    object Watch : TabItem( "Смотрю")
    object History : TabItem( "История")
}