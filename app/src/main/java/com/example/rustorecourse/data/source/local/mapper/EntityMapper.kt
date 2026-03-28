package com.example.rustorecourse.data.source.local.mapper

import com.example.rustorecourse.data.source.local.entity.AppDetailsItemEntity
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.data.source.local.entity.AppEntity
import com.example.rustorecourse.domain.model.AppDetailsItem

fun AppEntity.toDomain(): App = App(
    id = id,
    name = name,
    developer = developer,
    category = category,
    ageRating = ageRating,
    size = size,
    screenshotUrlList = screenshotUrlList,
    iconUrl = iconUrl,
    description = description
)

fun AppDetailsItemEntity.toDomain(): AppDetailsItem = AppDetailsItem(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl
)

fun App.toEntity(): AppEntity = AppEntity(
    id = id,
    name = name,
    developer = developer,
    category = category,
    ageRating = ageRating,
    size = size,
    screenshotUrlList = screenshotUrlList,
    iconUrl = iconUrl,
    description = description
)

fun AppDetailsItem.toEntity(): AppDetailsItemEntity = AppDetailsItemEntity(
    id = id,
    name = name,
    description = description,
    category = category,
    iconUrl = iconUrl
)

