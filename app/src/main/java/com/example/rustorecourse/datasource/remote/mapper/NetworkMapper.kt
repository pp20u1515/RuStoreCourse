package com.example.rustorecourse.datasource.remote.mapper

import com.example.rustorecourse.datasource.remote.model.AppDetailsItemDto
import com.example.rustorecourse.datasource.remote.model.AppDto
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.model.AppDetailsItem

fun List<AppDetailsItemDto>.toDomainList(): List<AppDetailsItem> {
    return this.map { it.toDomain() }
}

fun AppDetailsItemDto.toDomain(): AppDetailsItem = AppDetailsItem(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl
)

fun AppDto.toDomain(): App = App(
    id = id,
    name = name,
    developer = developer,
    category = category,
    ageRating = ageRating,
    size = size,
    iconUrl = iconUrl,
    screenshotUrlList = screenshotUrlList,
    description = description
)