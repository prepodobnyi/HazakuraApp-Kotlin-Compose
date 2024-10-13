package com.ru.hazakura.data.network.dto

import com.google.gson.annotations.SerializedName
import com.ru.hazakura.domain.model.AccessAndRefreshToken

data class AccessAndRefreshTokenDto(
    @SerializedName("access_token")  var accessToken  : String? = null,
    @SerializedName("token_type")    var tokenType    : String? = null,
    @SerializedName("refresh_token") var refreshToken : String? = null,
    @SerializedName("created_at")    var createdAt    : Int?    = null
){
    fun toModel(): AccessAndRefreshToken = AccessAndRefreshToken(accessToken = "Bearer $accessToken", tokenType, refreshToken, createdAt)
}
