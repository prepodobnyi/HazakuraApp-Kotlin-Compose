package com.ru.hazakura.data.network.dto


data class MainAnimeListDto(
    val data: DataListDto
)

data class AnimeDetailDataDto(
    val data: DataDetailDto
)
data class MainScreenAnimeListDto(
    val data: DataDto
)

data class AnimeDto(
    val id: String,
    val russian: String? = null,
    val poster: PosterDto,
)

data class AnimeChronologyDto(
    val id: String,
    val russian: String? = null,
    val poster: PosterDto,
    val kind: String? = "cm"
)

data class AnimeSimilarDto(
    val id: String,
    val russian: String? = null,
    val image: SimilarPosterDto,
)
data class AnimeDetailDto(
    val id: String,
    val russian: String? = null,
    val poster: PosterDto,
    val score: Float? = null,
    val status: String? = null,
    val kind: String?= null,
    val airedOn: AiredOnDto?= null,
    val genres: List<GenreDto>,
    val studios: List<StudioDto>,
    val rating: String?= null,
    val episodesAired: Int?= null,
    val episodes: Int?= null,
    val nextEpisodeAt: String?= null,
    val description: String?= null,
    var topic: TopicDto? = TopicDto("0"),
    var screenshots : List<ScreenshotsDto> = emptyList(),
    var chronology  : List<AnimeChronologyDto>  = emptyList()
)

data class TopicDto (
    var id : String? = null
)

data class ScreenshotsDto (
    var x332Url : String? = null
)

data class PosterDto(
    val originalUrl: String
)

data class SimilarPosterDto(
    val preview: String
)

data class AiredOnDto(
    val date: String?= null
)

data class GenreDto(
    val name: String?= null,
    val kind: String?= null
)

data class StudioDto(
    val id: String?= null,
    val name: String?= null
)

data class DataListDto(
    val animes: List<AnimeDto>,
)

data class DataDetailDto(
    val animes: List<AnimeDetailDto>,
)

data class DataDto(
    val ongoings: List<AnimeDto>,
    val seasonAnime: List<AnimeDto>,
    val populars: List<AnimeDto>,
    val random: List<AnimeDto>,
)

data class CommentsDto(
    var id              : Int? = null,
    var user_id          : Int? = null,
    var body            : String? = null,
    var html_body        : String? = null,
    var created_at       : String? = null,
    var user            : CommentedUserDto
)

data class CommentedUserDto(
    var id           : Int?    = null,
    var nickname     : String? = null,
    var avatar       : String? = null,
)