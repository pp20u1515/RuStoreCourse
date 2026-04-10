package com.example.rustorecourse.data.repository

import com.example.rustorecourse.data.source.local.database.AppDetailsDao
import com.example.rustorecourse.data.source.local.mapper.toDomain
import com.example.rustorecourse.data.source.local.service.AppDaoService
import com.example.rustorecourse.domain.model.AppDetailsItem
import com.example.rustorecourse.domain.repository.IAppDetailsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class AppDetailsRepository @Inject constructor(
    val localDaoService: AppDaoService
): IAppDetailsRepository {
    override suspend fun toggleWishlist(id: String, isInWishList: Boolean) {
        localDaoService.toggleWishlist(id, isInWishList)
    }

    override suspend fun observeAppDetails(id: String): Boolean {
        return localDaoService.getWishListStatus(id)
    }
}