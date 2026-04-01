package com.example.rustorecourse.domain.model

data class AppDetailsItem(
    val id: String,
    val name: String?,
    val description: String,
    val category: String,
    val iconUrl: String?
)