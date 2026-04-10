package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.repository.IAppDetailsRepository
import javax.inject.Inject

class UpdateWishListStatusUseCase @Inject constructor(
    private val appDetailRepository: IAppDetailsRepository
) {
    suspend operator fun invoke(id: String, isInWishList: Boolean){
        appDetailRepository.toggleWishlist(id, isInWishList)
    }
}