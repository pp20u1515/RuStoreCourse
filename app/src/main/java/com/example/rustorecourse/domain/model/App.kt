package com.example.rustorecourse.domain.model

import com.example.rustorecourse.domain.model.Category

data class App(
    val name: String,
    val developer: String,
    val category: Category,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
)