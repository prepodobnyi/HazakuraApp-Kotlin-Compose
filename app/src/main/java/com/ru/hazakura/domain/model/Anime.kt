package com.ru.hazakura.domain.model

import com.ru.hazakura.data.network.dto.AnimeSimilarDto

data class AnimeDetail(
    val id: String,
    val russian: String,
    val poster: Poster,
    val score: Float,
    val status: String,
    val kind: String,
    val airedOn: String,
    val genres: String,
    val theme: String,
    val studios: List<Studio>,
    val rating: String,
    val episodesAired: Int,
    val episodes: Int,
    val nextEpisodeAt: String,
    val description: String,
    var topic: String,
    var screenshots : List<Screenshots> = emptyList(),
    var chronology  : List<AnimeChronology>  = emptyList()
)
data class Topic (
    var id : String? = null
)
data class Studio (
    var id : String,
    var name: String
)

data class Screenshots (
    var x332Url : String? = null
)
data class Anime(
    val id: String,
    var russian: String,
    val poster: Poster,
)

data class AnimeChronology(
    val id: String,
    val russian: String,
    val poster: Poster,
    val kind: String
)

data class Poster(
    val originalUrl: String
)

data class AiredOn(
    val date: String
)

data class Genre(
    val name: String,
    val kind: String
)

data class MiniAnime(
    val id: String,
    val russian: String,
    val poster: String,
    val episodesAired: Int,
    val episodes: Int,
    val description: String
)

data class DataListAnime(
    val animes: List<Anime>
)

data class DataAnime(
    val ongoings: List<Anime>,
    val seasonAnime: List<Anime>,
    val populars: List<Anime>,
    val random: List<Anime>,
)


data class Comments(
    var id              : Int,
    var user_id          : Int,
    var body            : String,
    var html_body        : String,
    var images           : List<String>,
    var created_at       : String,
    var user            : CommentedUser
)

data class CommentedUser(
    var id           : Int,
    var nickname     : String,
    var avatar       : String
)

data class CalendarAnimeItem(
    val nextEpisode: Int,
    val nextEpisodeAt: String,
    val anime: Anime
)