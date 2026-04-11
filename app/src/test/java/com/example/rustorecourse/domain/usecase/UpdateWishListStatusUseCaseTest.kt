package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.repository.IAppDetailsRepository
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UpdateWishListStatusUseCaseTest {
    private val mockAppDetailRepository: IAppDetailsRepository = mockk()

    private lateinit var updateWishListStatusUseCase: UpdateWishListStatusUseCase

    // Тестовые данные
    private val testAppId = "1"
    private val anotherAppId = "2"
    private val nonExistentAppId = "non-existent-id"

    @Before
    fun setUp() {
        updateWishListStatusUseCase = UpdateWishListStatusUseCase(mockAppDetailRepository)
    }

    @After
    fun tearDown() {
        clearMocks(mockAppDetailRepository)
    }

    @Test
    fun `invoke should call repository toggleWishlist with correct parameters when adding to wishlist`() = runTest {
        coEvery { mockAppDetailRepository.toggleWishlist(testAppId, true) } returns Unit

        updateWishListStatusUseCase.invoke(testAppId, true)

        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(testAppId, true) }
    }

    @Test
    fun `invoke should call repository toggleWishlist with correct parameters when removing from wishlist`() = runTest {
        coEvery { mockAppDetailRepository.toggleWishlist(testAppId, false) } returns Unit

        updateWishListStatusUseCase.invoke(testAppId, false)

        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(testAppId, false) }
    }

    @Test
    fun `invoke should work with different app ids`() = runTest {
        coEvery { mockAppDetailRepository.toggleWishlist(any(), any()) } returns Unit

        updateWishListStatusUseCase.invoke(testAppId, true)
        updateWishListStatusUseCase.invoke(anotherAppId, false)
        updateWishListStatusUseCase.invoke("third-app-id", true)

        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(testAppId, true) }
        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(anotherAppId, false) }
        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist("third-app-id", true) }
    }

    @Test
    fun `invoke should handle adding multiple apps to wishlist`() = runTest {
        val appIds = listOf("app-1", "app-2", "app-3", "app-4", "app-5")
        coEvery { mockAppDetailRepository.toggleWishlist(any(), any()) } returns Unit

        appIds.forEach { appId ->
            updateWishListStatusUseCase.invoke(appId, true)
        }

        appIds.forEach { appId ->
            coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(appId, true) }
        }
    }

    @Test
    fun `invoke should handle empty string as app id`() = runTest {
        val emptyId = ""
        coEvery { mockAppDetailRepository.toggleWishlist(emptyId, true) } returns Unit

        updateWishListStatusUseCase.invoke(emptyId, true)

        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(emptyId, true) }
    }

    @Test
    fun `invoke should handle non-existent app id`() = runTest {
        coEvery { mockAppDetailRepository.toggleWishlist(nonExistentAppId, true) } returns Unit

        updateWishListStatusUseCase.invoke(nonExistentAppId, true)

        coVerify(exactly = 1) { mockAppDetailRepository.toggleWishlist(nonExistentAppId, true) }
    }
}