package com.example.rustorecourse.datasource.remote.model

import com.example.rustorecourse.domain.model.Category
import kotlinx.serialization.Serializable

@Serializable
data class AppDto (
    val id: String,
    val name: String,
    val developer: String,
    val category: Category?,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
)