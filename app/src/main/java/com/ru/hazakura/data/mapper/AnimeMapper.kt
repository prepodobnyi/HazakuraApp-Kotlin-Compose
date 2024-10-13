package com.ru.hazakura.data.mapper

import com.ru.hazakura.data.database.anime.AnimeEntity
import com.ru.hazakura.data.database.anime.WatchedEpisodeEntity
import com.ru.hazakura.data.network.dto.AiredOnDto
import com.ru.hazakura.data.network.dto.AnimeChronologyDto
import com.ru.hazakura.data.network.dto.AnimeDetailDto
import com.ru.hazakura.data.network.dto.AnimeDto
import com.ru.hazakura.data.network.dto.AnimeSimilarDto
import com.ru.hazakura.data.network.dto.CalendarAnimeItemDto
import com.ru.hazakura.data.network.dto.CommentedUserDto
import com.ru.hazakura.data.network.dto.CommentsDto
import com.ru.hazakura.data.network.dto.DataDto
import com.ru.hazakura.data.network.dto.DataListDto
import com.ru.hazakura.data.network.dto.GenreDto
import com.ru.hazakura.data.network.dto.PosterDto
import com.ru.hazakura.data.network.dto.ScreenshotsDto
import com.ru.hazakura.data.network.dto.SearchResponceDto
import com.ru.hazakura.data.network.dto.SimilarPosterDto
import com.ru.hazakura.data.network.dto.StudioDto
import com.ru.hazakura.data.network.dto.TopicDto
import com.ru.hazakura.domain.model.AiredOn
import com.ru.hazakura.domain.model.Anime
import com.ru.hazakura.domain.model.AnimeChronology
import com.ru.hazakura.domain.model.AnimeDetail
import com.ru.hazakura.domain.model.CalendarAnimeItem
import com.ru.hazakura.domain.model.CommentedUser
import com.ru.hazakura.domain.model.Comments
import com.ru.hazakura.domain.model.DataAnime
import com.ru.hazakura.domain.model.DataListAnime
import com.ru.hazakura.domain.model.Genre

import com.ru.hazakura.domain.model.MiniAnime
import com.ru.hazakura.domain.model.Poster
import com.ru.hazakura.domain.model.Screenshots
import com.ru.hazakura.domain.model.SearchResponce
import com.ru.hazakura.domain.model.Studio
import com.ru.hazakura.domain.model.Topic
import com.ru.hazakura.domain.model.WatchedEpisode
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

val types = mapOf(
    "tv" to "ТВ Сериал",
    "movie" to "Фильм",
    "ova" to "OVA",
    "ona" to "ONA",
    "special" to "Спецвыпуск",
    "tv_special" to "ТВ Спецвыпуск",
    "music" to "Музыкальное видео",
    "pv" to "Промо-видео",
    "cm" to "Рекламный ролик"
)

val statuses = mapOf(
    "anons" to "Анонс",
    "ongoing" to "Онгоинг",
    "released" to "Завершено"
)

val ageRatings = mapOf(
    "none" to "?",
    "g" to "0+",
    "pg" to "6+",
    "pg_13" to "13+",
    "r" to "16+",
    "r_plus" to "18+",
    "rx" to "21+"
)

val genreNames = mapOf(
    "Avant Garde" to "Авангард",
    "Gourmet" to "Гурман",
    "Drama" to "Драма",
    "Comedy" to "Комедия",
    "Slice of Life" to "Повседневность",
    "Adventure" to "Приключения",
    "Romance" to "Романтика",
    "Supernatural" to "Сверхестественное",
    "Sports" to "Спорт",
    "Mystery" to "Мистика",
    "Suspense" to "Триллер",
    "Horror" to "Ужасы",
    "Sci-Fi" to "Фантастика",
    "Fantasy" to "Фэнтези",
    "Action" to "Экшен",
    "Ecchi" to "Этти",
    "Erotica" to "Эротика",
    "Hentai" to "Хентай",
    "CGDCT" to "CGDCT", // не знаю, как перевести
    "Anthropomorphic" to "Антропоморфизм",
    "Martial Arts" to "Боевые искусства",
    "Vampire" to "Вампир",
    "Adult Cast" to "Взрослые персонажи",
    "Video Game" to "Видеоигры",
    "Military" to "Военное",
    "Survival" to "Выживание",
    "Harem" to "Гарем",
    "Racing" to "Гонки",
    "Gag Humor" to "Гэг-юмор",
    "Detective" to "Детектив",
    "Gore" to "Жестокость",
    "Childcare" to "Забота о детях",
    "High Stakes Game" to "Игра с высокими ставками",
    "Idols (Female)" to "Идолы (жен.)",
    "Idols (Male)" to "Идолы (муж.)",
    "Visual Arts" to "Изобразительное искусство",
    "Performing Arts" to "Исполнительское искусство",
    "Historical" to "Исторический",
    "Isekai" to "Исэкай",
    "Iyashikei" to "Иясикей",
    "Team Sports" to "Командный спорт",
    "Space" to "Космос",
    "Crossdressing" to "Кроссдрессинг",
    "Otaku Culture" to "Культура отаку",
    "Love Polygon" to "Любовный многоугольник",
    "Magical Sex Shift" to "Магическая смена пола",
    "Mahou Shoujo" to "Махо-сёдзё",
    "Medical" to "Медицинский",
    "Mecha" to "Меха",
    "Mythology" to "Мифология",
    "Music" to "Музыка",
    "Educational" to "Образовательное",
    "Organized Crime" to "Организованная преступность",
    "Parody" to "Пародия",
    "Pets" to "Питомцы",
    "Psychological" to "Психологическое",
    "Time Travel" to "Путешествие во времени",
    "Workplace" to "Работа",
    "Reverse Harem" to "Реверсивный гарем",
    "Reincarnation" to "Реинкарнация",
    "Romantic Subtext" to "Романтический подтекст",
    "Samurai" to "Самурай",
    "Combat Sports" to "Спортивный бой",
    "Strategy Game" to "Стратегические игры",
    "Super Power" to "Суперсила",
    "Award Winning" to "Удостоено наград",
    "Delinquents" to "Хулиганы",
    "School" to "Школа",
    "Showbiz" to "Шоу-бизнес",
)

fun AnimeEntity.toAnime(
): Anime {
    return Anime(id, russian, poster = Poster(poster))
}

fun Anime.toAnimeEntity(
    lastDubbing: String
): AnimeEntity{
    return AnimeEntity(
        id = id,
        lastDubbbing = lastDubbing,
        russian = russian,
        poster = poster.originalUrl,
    )
}

fun AnimeDto.toAnime(
): Anime{
    return Anime(
        id = id,
        russian = russian?: "нет названия",
        poster = poster.toPoster(),
    )
}

fun AnimeChronologyDto.toAnime(
): AnimeChronology{
    return AnimeChronology(
        id = id,
        russian = russian?: "нет названия",
        poster = poster.toPoster(),
        kind = kind?: "cm"
    )
}

fun AnimeSimilarDto.toAnime(
): Anime{
    return Anime(
        id = id,
        russian = russian?: "нет названия",
        poster = image.toPoster(),
    )
}

fun AnimeDetailDto.toAnimeDetail(
): AnimeDetail {

    return AnimeDetail(
        id = id,
        russian = russian?: "нет названия",
        poster = poster.toPoster(),
        score = score?: 0.0f,
        status = statuses[status?: "ongoing"]?: "неизвестно",
        kind = types[kind?: "ona"]?: "ona",
        airedOn = airedOn?.toAiredOn()?.date ?: AiredOn("0000").date,
        genres = getGenreString(genres.map { it.toGenre() }),
        theme = getThemeString(genres.map { it.toGenre() }),
        studios = studioMapper(studios),
        rating = ageRatings[rating?: "none"]?: "none",
        episodesAired = episodesAired?: 0,
        episodes = episodes?: 0,
        nextEpisodeAt = formatDateTime(nextEpisodeAt?: "0"),
        description = description?.replace(Regex("\\[.*?]"), "") ?: "Без описания",
        topic = topic?.id ?:"0",
        screenshots = screenshots.map {it.toScreenshots()},
        chronology = getAnimeChrono(chronology.map { it.toAnime() })
    )
}

fun CalendarAnimeItemDto.toCalendarAnimeItem(): CalendarAnimeItem{
    val animes = anime.toAnime()
    if(animes.russian == "")
    {
        animes.russian = "Нет названия"
    }
    return CalendarAnimeItem(
        nextEpisode = next_episode?: 0,
        nextEpisodeAt = formatDateCalendarTime(next_episode_at?: "0"),
        anime = animes
    )
}
fun ScreenshotsDto.toScreenshots(): Screenshots{
    return Screenshots(x332Url)
}

fun SimilarPosterDto.toPoster(): Poster {
    return Poster("https://shikimori.one$preview")
}

fun PosterDto.toPoster(): Poster {
    return Poster(originalUrl)
}

fun AiredOnDto.toAiredOn(): AiredOn {
    return AiredOn(date?: "0000")
}

fun GenreDto.toGenre(): Genre {
    val russianName = genreNames[name]?: name?: ""
    return Genre(name = russianName, kind?: "")
}
fun getGenreString(genres: List<Genre>): String {
    return genres.filter { it.kind == "genre" }
        .joinToString(", ") { it.name } // Объединяем в строку через запятую
}

fun getAnimeChrono(genres: List<AnimeChronology>): List<AnimeChronology> {
    return genres.filter { it.kind !in setOf("cm", "pv", "music") }

}

fun getThemeString(genres: List<Genre>): String {
    return genres.filter { it.kind == "theme" }
        .joinToString(", ") {it.name} // Объединяем в строку через запятую
}

fun StudioDto.toStudio(): Studio {
    return Studio(id = id?: "0", name = name?:"неизвестно")
}

fun studioMapper(studio: List<StudioDto>): List<Studio> {
    if(studio.isEmpty()){
        return emptyList()
    }else{
        return studio.map { it.toStudio() }
    }
}

fun WatchedEpisodeEntity.toWatchedEpisode(): WatchedEpisode {
    return WatchedEpisode(
        anime_id = anime_id,
        episode_number = episode_number.toInt(),
    )
}

fun WatchedEpisode.toWatchedEpisodeEntity(): WatchedEpisodeEntity {
    return WatchedEpisodeEntity(
        id = 0,
        anime_id = anime_id,
        episode_number = episode_number.toString(),
    )
}

fun DataDto.toDataAnime(): DataAnime{
    return DataAnime(
        ongoings = ongoings.map { it.toAnime()},
        seasonAnime = seasonAnime.map { it.toAnime()},
        populars = populars.map { it.toAnime()},
        random = random.map { it.toAnime()},
        )
}

fun DataListDto.toDataListAnime(): DataListAnime {
    return  DataListAnime(
        animes = animes.map { it.toAnime()}
    )
}

fun CommentsDto.toComments(): Comments{
    return Comments(
        id = id?: 0,
        user_id = user_id?: 0,
        body = body?: "0",
        html_body = extractText(html_body?: "0"),
        images = extractImage(html_body?: "0"),
        created_at = formatDateTimeComment(created_at?: "0"),
        user = user.toCommentedUser()
    )
}

fun CommentedUserDto.toCommentedUser(): CommentedUser{
    return CommentedUser(
        id = id?: 0,
        nickname = nickname?: "кто-то",
        avatar = "$avatar"
    )
}

fun formatDateTime(dateTime: String): String {
    if(dateTime == "0"){return "нет данных"}
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault())
    val outputFormatSameYear = SimpleDateFormat("dd MMMM", Locale("ru"))
    val outputFormatDifferentYear = SimpleDateFormat("yyyy dd MMM, HH:mm", Locale("ru"))
    val outputFormatToday = SimpleDateFormat("'Сегодня в' HH:mm", Locale("ru"))
    val outputFormatTomorrow = SimpleDateFormat("'Завтра в' HH:mm", Locale("ru"))

    val date = inputFormat.parse(dateTime)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val now = Calendar.getInstance()

    return when {
        now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) -> {
            when {
                now.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR) -> {
                    outputFormatToday.format(date)
                }
                calendar.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == 1 -> {
                    outputFormatTomorrow.format(date)
                }
                else -> {
                    outputFormatSameYear.format(date)
                }
            }
        }
        else -> {
            outputFormatDifferentYear.format(date)
        }
    }
}

fun formatDateCalendarTime(dateTime: String): String {
    if(dateTime == "0"){return "нет данных"}
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val outputFormatSameYear = SimpleDateFormat("dd MMMM", Locale("ru"))
    val outputFormatDifferentYear = SimpleDateFormat("yyyy dd MMM, HH:mm", Locale("ru"))
    val outputFormatToday = SimpleDateFormat("'Сегодня в' HH:mm", Locale("ru"))
    val outputFormatTomorrow = SimpleDateFormat("'Завтра в' HH:mm", Locale("ru"))

    val date = inputFormat.parse(dateTime)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val now = Calendar.getInstance()

    return when {
        now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) -> {
            when {
                now.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR) -> {
                    outputFormatToday.format(date)
                }
                calendar.get(Calendar.DAY_OF_YEAR) - now.get(Calendar.DAY_OF_YEAR) == 1 -> {
                    outputFormatTomorrow.format(date)
                }
                else -> {
                    outputFormatSameYear.format(date)
                }
            }
        }
        else -> {
            outputFormatDifferentYear.format(date)
        }
    }
}

fun formatDateTimeComment(dateTime: String): String {
    if(dateTime == "0"){return "нет данных"}
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault())
    val outputFormatSameYear = SimpleDateFormat("dd MMM HH:mm", Locale("ru"))
    val outputFormatDifferentYear = SimpleDateFormat("yyyy dd MMM, HH:mm", Locale("ru"))
    val outputFormatToday = SimpleDateFormat("'Сегодня в' HH:mm", Locale("ru"))
    val outputFormatYesterday = SimpleDateFormat("'Вчера в' HH:mm", Locale("ru"))

    val date = inputFormat.parse(dateTime)
    val calendar = Calendar.getInstance()
    calendar.time = date

    val now = Calendar.getInstance()

    return when {
        now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR) -> {
            when {
                now.get(Calendar.DAY_OF_YEAR) == calendar.get(Calendar.DAY_OF_YEAR) -> {
                    outputFormatToday.format(date)
                }
                now.get(Calendar.DAY_OF_YEAR) - calendar.get(Calendar.DAY_OF_YEAR) == 1 -> {
                    outputFormatYesterday.format(date)
                }
                else -> {
                    outputFormatSameYear.format(date)
                }
            }
        }
        else -> {
            outputFormatDifferentYear.format(date)
        }
    }
}

fun extractText(input: String): String {
    val doc: Document = Jsoup.parse(input)
    doc.body().select("span[class=marker-text]").remove()
    doc.body().select("div[class=b-replies translated-before single]").remove()
    doc.body().select("div[class=b-replies translated-before]").remove()

    val text = doc.text()

    val sb = StringBuilder()
    sb.append(text?: "")
    return sb.toString()

}

fun extractImage(input: String): List<String> {
    val doc: Document = Jsoup.parse(input)
    doc.body().select("span[class=marker-text]").remove()
    doc.body().select("div[class=b-replies translated-before single]").remove()
    doc.body().select("div[class=b-replies translated-before]").remove()
    doc.body().select("div[class=b-quote]").remove()
    doc.body().select("div[class=ban]").remove()
    doc.body().select("img.smiley").remove()
    doc.body().select("a.video-link").remove()

    val elements: Elements = doc.body().select("img")
    val sb = mutableListOf<String>()

    for (element in elements) {
        val src = element.attr("src")
        sb.add(src)
    }
    return sb.toList()

}

fun SearchResponceDto.toSearchResponce(): SearchResponce{
    return SearchResponce(
        shikimori_ids = results.filter { it.shikimori_id != null }
            ?.map { it.shikimori_id!! }
            ?.distinct()?: emptyList()
    )
}