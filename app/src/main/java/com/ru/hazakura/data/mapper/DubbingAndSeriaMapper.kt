package com.ru.hazakura.data.mapper

import com.ru.hazakura.data.network.dto.LinkForGetDubbingDto
import com.ru.hazakura.domain.model.LinkForGetDubbing

fun LinkForGetDubbingDto.ToLinkForGetDubbing(): LinkForGetDubbing{
    return LinkForGetDubbing(
        link = link
    )
}