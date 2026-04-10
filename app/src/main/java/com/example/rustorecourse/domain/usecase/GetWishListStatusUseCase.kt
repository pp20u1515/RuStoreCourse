package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.repository.IAppDetailsRepository
import javax.inject.Inject

class GetWishListStatusUseCase @Inject constructor(
    private val appDetailsRepository: IAppDetailsRepository
) {
    suspend operator fun invoke(id: String): Boolean{
        return appDetailsRepository.observeAppDetails(id)
    }
}