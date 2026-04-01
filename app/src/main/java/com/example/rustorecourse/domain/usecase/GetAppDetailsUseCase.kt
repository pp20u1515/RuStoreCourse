package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.model.App
import com.example.rustorecourse.domain.repository.IAppRepository
import javax.inject.Inject

<<<<<<<< HEAD:app/src/main/java/com/example/rustorecourse/domain/usecase/GetAppDetailsUseCase.kt
class GetAppDetailsUseCase @Inject constructor(
========
class GetAppUseCase @Inject constructor(
>>>>>>>> main:app/src/main/java/com/example/rustorecourse/domain/usecase/GetAppUseCase.kt
    private val appRepository: IAppRepository
) {
    suspend operator fun invoke(id: String): Result<App> {
        return appRepository.getLocalApp(id)
    }
}