package com.example.rustorecourse.data.datasource.local.mapper

import com.example.rustorecourse.data.datasource.local.entity.AppDetailsItemEntity
import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.data.datasource.local.entity.AppEntity
import com.example.rustorecourse.domain.model.AppDetailsItem

fun AppEntity.toDomain(): App = App(
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
    appName = appName,
    description = description,
    category = category,
    icon = icon
)

fun App.toEntity(): AppEntity = AppEntity(
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
    appName = appName,
    description = description,
    category = category,
    icon = icon
)

