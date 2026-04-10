package com.example.rustorecourse.data.source.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.rustorecourse.data.source.local.database.CategoryConverter
import com.example.rustorecourse.domain.model.Category

@Entity (tableName = "app_details")
@TypeConverters(CategoryConverter::class)
data class AppEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val developer: String,
    val category: Category?,
    val ageRating: Int,
    val size: Float,
    val screenshotUrlList: List<String>,
    val iconUrl: String,
    val description: String,
    val lastUpdated: Long = System.currentTimeMillis(),
    val isInWishList: Boolean = false
)
