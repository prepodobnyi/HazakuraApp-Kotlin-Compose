package com.ru.hazakura.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class AccessAndRefreshToken(
    var accessToken  : String? = null,
    var tokenType    : String? = null,
    var refreshToken : String? = null,
    var createdAt    : Int?    = null
)

