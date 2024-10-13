package com.ru.hazakura.data.network.dto

import com.ru.hazakura.domain.model.User

data class UserDto(
    val id: Int?,
    val nickname: String?,
    val avatar: String?
) {
   fun toModel(): User = User(id, nickname, avatar)
}