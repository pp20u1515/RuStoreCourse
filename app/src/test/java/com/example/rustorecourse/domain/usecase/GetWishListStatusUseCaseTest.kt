package com.example.rustorecourse.domain.usecase

import com.example.rustorecourse.domain.repository.IAppDetailsRepository
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class GetWishListStatusUseCaseTest {

    private val mockAppDetailsRepository: IAppDetailsRepository = mockk()
    private lateinit var getWishListStatusUseCase: GetWishListStatusUseCase

    // Тестовые данные
    private val testAppId = "1"
    private val anotherAppId = "2"
    private val nonExistentAppId = "non-existent-id"

    @Before
    fun setUp() {
        getWishListStatusUseCase = GetWishListStatusUseCase(mockAppDetailsRepository)
    }

    @After
    fun tearDown() {
        clearMocks(mockAppDetailsRepository)
    }

    @Test
    fun `invoke should return true when app is in wishlist`() = runTest {
        coEvery { mockAppDetailsRepository.observeAppDetails(testAppId) } returns true

        val result = getWishListStatusUseCase.invoke(testAppId)

        assertTrue(result)
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(testAppId) }
    }

    @Test
    fun `invoke should return false when app is not in wishlist`() = runTest {
        coEvery { mockAppDetailsRepository.observeAppDetails(testAppId) } returns false

        val result = getWishListStatusUseCase.invoke(testAppId)

        assertFalse(result)
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(testAppId) }
    }

    @Test
    fun `invoke should return correct status for different app ids`() = runTest {
        coEvery { mockAppDetailsRepository.observeAppDetails(testAppId) } returns true
        coEvery { mockAppDetailsRepository.observeAppDetails(anotherAppId) } returns false

        val result1 = getWishListStatusUseCase.invoke(testAppId)
        val result2 = getWishListStatusUseCase.invoke(anotherAppId)

        assertTrue(result1)
        assertFalse(result2)

        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(testAppId) }
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(anotherAppId) }
    }

    @Test
    fun `invoke should handle multiple apps with mixed statuses`() = runTest {
        val appIds = listOf("app-1", "app-2", "app-3", "app-4", "app-5")
        val statuses = listOf(true, false, true, false, true)

        appIds.forEachIndexed { index, id ->
            coEvery { mockAppDetailsRepository.observeAppDetails(id) } returns statuses[index]
        }

        val results = appIds.map { getWishListStatusUseCase.invoke(it) }

        assertEquals(statuses, results)

        appIds.forEach { id ->
            coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(id) }
        }
    }

    @Test
    fun `invoke should handle app that was added to wishlist`() = runTest {
        coEvery { mockAppDetailsRepository.observeAppDetails(testAppId) } returns true

        val result = getWishListStatusUseCase.invoke(testAppId)

        assertTrue(result)
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(testAppId) }
    }

    @Test
    fun `invoke should handle app that was removed from wishlist`() = runTest {
        coEvery { mockAppDetailsRepository.observeAppDetails(testAppId) } returns false

        val result = getWishListStatusUseCase.invoke(testAppId)

        assertFalse(result)
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(testAppId) }
    }

    @Test
    fun `invoke should handle empty string as app id`() = runTest {
        val emptyId = ""
        coEvery { mockAppDetailsRepository.observeAppDetails(emptyId) } returns false

        val result = getWishListStatusUseCase.invoke(emptyId)

        assertFalse(result)
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(emptyId) }
    }

    @Test
    fun `invoke should handle non-existent app id`() = runTest {
        coEvery { mockAppDetailsRepository.observeAppDetails(nonExistentAppId) } returns false

        val result = getWishListStatusUseCase.invoke(nonExistentAppId)

        assertFalse(result)
        coVerify(exactly = 1) { mockAppDetailsRepository.observeAppDetails(nonExistentAppId) }
    }
}