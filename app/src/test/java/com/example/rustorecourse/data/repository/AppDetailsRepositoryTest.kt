package com.example.rustorecourse.data.repository

import com.example.rustorecourse.data.source.local.service.AppDaoService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class AppDetailsRepositoryTest {
    private val mockLocalDaoService: AppDaoService = mockk()

    private lateinit var repository: AppDetailsRepository

    @Before
    fun setUp() {
        repository = AppDetailsRepository(mockLocalDaoService)
    }

    @Test
    fun `toggleWishlist should call localDaoService toggleWishlist with correct parameters`() = runTest {
        val testId = "fa2e31b8-1234-4cf7-9914-108a170a1b01"
        val testWishlistStatus = true

        coEvery { mockLocalDaoService.toggleWishlist(testId, testWishlistStatus) } returns Unit

        repository.toggleWishlist(testId, testWishlistStatus)

        coVerify(exactly = 1) {
            mockLocalDaoService.toggleWishlist(testId, testWishlistStatus)
        }
    }

    @Test
    fun `toggleWishlist should call localDaoService toggleWishlist with false status`() = runTest {
        val testId = "1"
        val testWishlistStatus = false

        coEvery { mockLocalDaoService.toggleWishlist(testId, testWishlistStatus) } returns Unit

        repository.toggleWishlist(testId, testWishlistStatus)

        coVerify(exactly = 1) {
            mockLocalDaoService.toggleWishlist(testId, testWishlistStatus)
        }
    }

    @Test
    fun `toggleWishlist should work correctly when called multiple times`() = runTest {
        val testId = "2"

        coEvery { mockLocalDaoService.toggleWishlist(any(), any()) } returns Unit

        repository.toggleWishlist(testId, true)
        repository.toggleWishlist(testId, false)
        repository.toggleWishlist(testId, true)

        coVerify(exactly = 3) {
            mockLocalDaoService.toggleWishlist(any(), any())
        }
    }

    @Test
    fun `observeAppDetails should return true when app is in wishlist`() = runTest {
        val testId = "3"
        val expectedStatus = true

        coEvery { mockLocalDaoService.getWishListStatus(testId) } returns expectedStatus

        val result = repository.observeAppDetails(testId)

        assert(result == expectedStatus)
        coVerify(exactly = 1) {
            mockLocalDaoService.getWishListStatus(testId)
        }
    }

    @Test
    fun `observeAppDetails should return false when app is not in wishlist`() = runTest {
        val testId = "4"
        val expectedStatus = false

        coEvery { mockLocalDaoService.getWishListStatus(testId) } returns expectedStatus

        val result = repository.observeAppDetails(testId)

        assert(result == expectedStatus)
        coVerify(exactly = 1) {
            mockLocalDaoService.getWishListStatus(testId)
        }
    }

    @Test
    fun `observeAppDetails should return correct status for different app ids`() = runTest {
        val appId1 = "app-1"
        val appId2 = "app-2"

        coEvery { mockLocalDaoService.getWishListStatus(appId1) } returns true
        coEvery { mockLocalDaoService.getWishListStatus(appId2) } returns false

        val result1 = repository.observeAppDetails(appId1)
        val result2 = repository.observeAppDetails(appId2)

        assert(result1 == true)
        assert(result2 == false)

        coVerify(exactly = 1) { mockLocalDaoService.getWishListStatus(appId1) }
        coVerify(exactly = 1) { mockLocalDaoService.getWishListStatus(appId2) }
    }
}