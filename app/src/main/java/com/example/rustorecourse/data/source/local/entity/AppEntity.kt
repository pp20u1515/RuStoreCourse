package com.example.rustorecourse.data.source.local.entity

import com.example.rustorecourse.domain.model.Category

data class AppEntity(
    val id: String,
    val name: String,
    val developer: String,
    val category: Category?,
    val ageRating: Int,
    val size: Float,
    val screenshotUrlList: List<String>,
    val iconUrl: String,
    val description: String
)
