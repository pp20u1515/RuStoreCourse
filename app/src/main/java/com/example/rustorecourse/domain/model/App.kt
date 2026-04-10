package com.example.rustorecourse.domain.model

data class App(
    val id: String,
    val name: String,
    val developer: String,
    val category: Category?,
    val ageRating: Int,
    val size: Float,
    val iconUrl: String,
    val screenshotUrlList: List<String>,
    val description: String,
    val lastUpdated: Long = System.currentTimeMillis(),
    val isInWishList: Boolean = false
)