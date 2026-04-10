package com.example.rustorecourse.domain.repository

interface IAppDetailsRepository {
    suspend fun toggleWishlist(id: String, isInWishList: Boolean)
    suspend fun observeAppDetails(id: String): Boolean
}