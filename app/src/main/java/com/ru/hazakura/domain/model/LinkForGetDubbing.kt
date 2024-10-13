package com.ru.hazakura.domain.model

data class LinkForGetDubbing(
    val link: String?
)

data class Translations (
    var translationTitle : String?,
    var dataMediaId      : String?,
    var dataMediaHash    : String?,
    var dataEpisodeCount : String?
)