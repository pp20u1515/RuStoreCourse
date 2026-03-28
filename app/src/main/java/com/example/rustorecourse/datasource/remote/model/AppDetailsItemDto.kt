package com.example.rustorecourse.datasource.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class AppDetailsItemDto(
    val id: String,
    val name: String?,
    val description: String,
    val category: String,
    val iconUrl: String?
)
