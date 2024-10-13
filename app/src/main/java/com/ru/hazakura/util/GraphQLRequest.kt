package com.ru.hazakura.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate


data class GraphQLRequest(
    val query: String
)

fun homeScreenGraphQuery(): GraphQLRequest {
    val year = LocalDate.now().year
    val season = getCurrentSeason()

    return GraphQLRequest(
        query = """
            { 
              ongoings: animes(page: 1, limit: 12, order: ranked, season: "${year-1}_${year}", status: "ongoing", score: 6) { 
                		id 
                        russian 
                        poster { originalUrl } 
                		
                }
              seasonAnime: animes(page: 1, limit: 12, order: popularity, season: "${season}_${year}", status: "!anons") { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
              populars: animes(page: 1, limit: 12, order: popularity, season: "${year-1}_${year}", status: "!anons", score: 6) { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
              random: animes(page: 1, limit: 12, order: random, status: "!anons", score: 6) { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }

            }
        """.trimIndent())
}
fun studioGraphQuery(page: Int = 1, studio: String): GraphQLRequest {
    return GraphQLRequest(
        query = """
            { 
              animes(page: $page, limit: 50, studio: "$studio") { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
            }
        """.trimIndent())
}

fun homeOngoingGraphQuery(page: Int = 1): GraphQLRequest {
    val year = LocalDate.now().year
    return GraphQLRequest(
        query = """
            { 
              animes(page: $page, limit: 50, order: ranked, season: "${year-1}_${year}", status: "ongoing", score: 6) { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
            }
        """.trimIndent())
}

fun animeDetailGraphQuery(id: String): GraphQLRequest {
    return GraphQLRequest(
        query = """
            { 
              animes(ids: "$id") { 
                		id 
                        russian 
                        poster { originalUrl: originalUrl } 
                        score
                        status
                        kind
                        airedOn{date}
                        genres{name, kind}
                        studios{id, name}
                        rating
                        episodesAired
                        episodes
                        nextEpisodeAt
                        description
                        topic{id}
                        screenshots{x332Url}
                        chronology{id,russian,poster{originalUrl: mainUrl}, kind}
                }
            }
        """.trimIndent())
}

fun homePopularGraphQuery(page: Int = 1): GraphQLRequest {
    val year = LocalDate.now().year
    return GraphQLRequest(
        query = """
            { 
              animes(page: $page, limit: 50, order: popularity, season: "${year-1}_${year}", status: "!anons", score: 6) { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
            }
        """.trimIndent())
}
fun homeSeasonAnimeGraphQuery(page: Int = 1): GraphQLRequest {
    val year = LocalDate.now().year
    val season = getCurrentSeason()
    return GraphQLRequest(
        query = """
            { 
              animes(page: $page, limit: 50, order: popularity, season: "${season}_${year}", status: "!anons") { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
            }
        """.trimIndent())
}
fun homeRandomGraphQuery(page: Int = 1): GraphQLRequest {
    return GraphQLRequest(
        query = """
            { 
              animes(page: $page, limit: 50, order: random, status: "!anons", score: 6) { 
                		id 
                        russian 
                        poster { originalUrl: mainUrl } 
                		
                }
            }
        """.trimIndent())
}

fun SearchAnimeGraphQuery(ids: String): GraphQLRequest {
    return GraphQLRequest(
        query = """
            {
              animes(limit: 50, ids: "$ids") {
                id
                russian
                poster { originalUrl: mainUrl } 
              }
            }
                    """.trimIndent())
}

fun FilterGraphQuery(
    page: Int = 1,
    kind: String = "",
    status: String = "",
    genre: String = "",
    season: String = "",
    ratingAge: String = "",
    score: String = "1",
    order: String = "popularity"
): GraphQLRequest {
    return GraphQLRequest(
        query = """
            {
                animes(page : $page, limit: 50, kind: "!pv,!cm,!music,$kind", status:"!anons,$status", genre:"$genre", season:"$season", rating:"$ratingAge", score:$score, order: $order) {
                    id
                    russian
                    poster{originalUrl: mainUrl}
                }
            }
                    """.trimIndent())
}

fun getCurrentSeason(): String {
    val month = LocalDate.now().monthValue
    return when (month) {
        in 1..3 -> "winter"
        in 4..6 -> "spring"
        in 7..9 -> "summer"
        in 10..12 -> "fall"
        else -> ""
    }
}


